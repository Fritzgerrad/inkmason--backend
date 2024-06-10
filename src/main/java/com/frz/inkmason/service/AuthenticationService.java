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
                        loginUserDto.getIdentifier(),
                        loginUserDto.getPassword()
                )
        );
        User user = userRepository.findUserByEmail(loginUserDto.getIdentifier()).orElseThrow();
        String token = jwtUtil.generateToken(user);

        return new BodyResponse<>( StatusCode.successful.getCode(), "Login Successful",
                new  AuthResponseBody(token, user.getFirstname(), user.getRole(), user.isVerified(),  user.getId()));
    }

    public Response verifyOTP(OTPDto otpDto){
        Response response = new LocalResponse();
        Optional <User> userOptional = userRepository.findUserByEmail(otpDto.getEmail());
        if(userOptional.isEmpty()){
            return new LocalResponse(StatusCode.badRequest.getCode(), "User not Found");
        }
        User user = userOptional.get();
        int otpStatus = otpUtil.validateOTP(user,otpDto.getOtp());
        switch (otpStatus){
            case 0:
                user.setVerified(true);
                userRepository.save(user);
                response.setStatusCode(StatusCode.successful.getCode());
                response.setStatusMessage("OTP Successfully Validated");
                break;
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

    public Response sendOTPToCurrentUser(OTPDto otpDto,String action){
        Optional <User> user = userRepository.findUserByEmail(otpDto.getEmail());
        if(user.isEmpty()){
            return new LocalResponse(StatusCode.badRequest.getCode(), "User does not exist");
        }
        String otp = otpUtil.regenerateOTP(user.get());
        EmailDetailsDto emailDetailsDto;
        if (action.equals("verifyAccount")){
            emailDetailsDto = emailService.generateRegistrationOTPMail(user.get(),otp);
        }
        else{
            emailDetailsDto = emailService.generatePasswordResetOTPMail(user.get(),otp);
        }
        emailService.sendEmail(emailDetailsDto);
        return new LocalResponse(StatusCode.successful.getCode(),"OTP Successfully Resent");
    }

    public Response passwordReset(ResetPasswordDTO resetPasswordDTO){
        Optional<User> userOptional = userRepository.findUserByEmail(resetPasswordDTO.getEmail());
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
