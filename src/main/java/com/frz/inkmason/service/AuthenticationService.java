package com.frz.inkmason.service;

import com.frz.inkmason.dto.auth.*;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.response.AuthResponseBody;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.util.JwtUtil;
import com.frz.inkmason.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OtpUtil otpUtil;


    public Response authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );
        User user = userRepository.findUserByEmail(loginUserDto.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user);

        return new BodyResponse<>( StatusCode.successful.getCode(), "Login Successful",
                new  AuthResponseBody(token, user.getFirstname(), user.getRole(), user.isVerified(),  user.getId()));
    }

    public Response verifyOTP(OTPDto otpDto){
        Response response = new LocalResponse();

        Long userId = Long.parseLong(otpDto.getIdentifier());
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            return new LocalResponse(StatusCode.badRequest.getCode(), "User not Found");
        }
        User user = userOptional.get();
        int otpStatus = otpUtil.validateOTP(user,otpDto.getToken());
        switch (otpStatus){
            case 0:
                user.setVerified(true);
                user = userRepository.save(user);
                String token = jwtUtil.generateToken(user);

                return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",
                        new AuthResponseBody(token, user.getFirstname(), user.getRole(), user.isVerified(), user.getId()));
            case 1:
                response.setStatusCode(StatusCode.badRequest.getCode());
                response.setStatusMessage("OTP has Expired");
                break;

            case 2:
                response.setStatusCode(StatusCode.badRequest.getCode());
                response.setStatusMessage("OTP Not a Match");
                break;

            default:
                response.setStatusCode(StatusCode.unknownError.getCode());
                response.setStatusMessage("An Unknown Error Occurred");
        }
        return response;
    }

    public Response resendOTP(String identifier){
        Long userId = Long.parseLong(identifier);
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> sendOTPToCurrentUser("verifyAccount", user.getEmail())).orElse(null);
    }

    public Response sendOTPToCurrentUser(String action,String email){
        Optional <User> userOptional = userRepository.findUserByEmail(email);

        if(userOptional.isEmpty()){
            return new LocalResponse(StatusCode.badRequest.getCode(), "User does not exist");
        }
        User user = userOptional.get();
        String otp = otpUtil.regenerateOTP(user);
        EmailDetailsDto emailDetailsDto;
        if (action.equals("verifyAccount")){
            emailDetailsDto = emailService.generateRegistrationOTPMail(user,otp);
        }
        else{
            emailDetailsDto = emailService.generatePasswordResetOTPMail(user,otp);
        }
        emailService.sendEmail(emailDetailsDto);
        return new BodyResponse<>(StatusCode.successful.getCode(),"OTP Successfully Resent",user.getId());
    }

    public Response passwordReset(ResetPasswordDTO resetPasswordDTO){
        Long userId = Long.parseLong(resetPasswordDTO.getIdentifier());
        Optional<User> userOptional = userRepository.findById(userId);

        if(!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())){
            return new LocalResponse(StatusCode.badRequest.getCode(), "Passwords don't match");
        }

        if(userOptional.isEmpty()){
            return new LocalResponse(StatusCode.badRequest.getCode(), "User does not exist");
        }
        User user = userOptional.get();
        int otpStatus = otpUtil.validateOTP(user, resetPasswordDTO.getToken());

        if (otpStatus == 0){
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
            userRepository.save(user);
            return new LocalResponse(StatusCode.successful.getCode(),"Password Changed Successfully");
        }
        else {
            return new LocalResponse(StatusCode.badRequest.getCode(),"Invalid OTP");
        }
    }


}
