package com.frz.inkmason.service;

import com.frz.inkmason.dto.auth.CreateUserDto;
import com.frz.inkmason.dto.auth.EmailDetailsDto;
import com.frz.inkmason.dto.auth.LoginUserDto;
import com.frz.inkmason.dto.auth.OTPDto;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.response.AuthResponse;
import com.frz.inkmason.model.response.LocalResponse;
import com.frz.inkmason.model.response.Response;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.model.User;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    @Value("${adminCreationPass}")
    private String ADMINCREATIONPASS;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OTPUtil otpUtil;


    public Response register(CreateUserDto userDto) {

        if (!userRepository.findUserByEmail(userDto.getEmail()).equals(Optional.empty())) {
            return new LocalResponse(StatusCode.badRequest,"User Already Exists");
        }

        if (userDto.getRole().equals(Role.admin) && (userDto.getAdminCreationPass() == null || !userDto.getAdminCreationPass().equals(ADMINCREATIONPASS))) {
            return new LocalResponse(StatusCode.unauthorized,"You don't have Permission to create an admin");
        }

        User user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                //.allowsNewsLetter(userDto.isAllowsNewsLetter())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .createdAt(new Date())
                .updatedAt(new Date())
                .verified(false)
                .build();

        user = userRepository.save(user);
        String otp = otpUtil.generateOTP(user);

        String token = jwtService.generateToken(user);
        EmailDetailsDto emailDetailsDto = emailService.generateRegistrationOTPMail(user,otp);
        emailService.sendEmail(emailDetailsDto);

        return new AuthResponse(token, user.getFirstname(), user.getRole(), false, StatusCode.successful, "Account Created Successfully", user.getId());
    }

    public Response authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getIdentifier(),
                        loginUserDto.getPassword()
                )
        );
        User user = userRepository.findUserByEmail(loginUserDto.getIdentifier()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getFirstname(), user.getRole(), user.isVerified(), StatusCode.successful,"Login Successful", user.getId());
    }

    public Response verifyOTP(OTPDto otpDto){
        System.out.println(otpDto);
        Optional <User> user = userRepository.findUserByEmail(otpDto.getEmail());
        if(user.isEmpty()){
            return new LocalResponse(StatusCode.badRequest, "User not Found");
        }
        return otpUtil.validateOTP(user.get(),otpDto.getOtp());
    }

    public Response resendOTP(OTPDto otpDto){
        Optional <User> user = userRepository.findUserByEmail(otpDto.getEmail());
        if(user.isEmpty()){
            return new LocalResponse(StatusCode.badRequest, "User does not exist");
        }
        String otp = otpUtil.regenerateOTP(user.get());
        EmailDetailsDto emailDetailsDto = emailService.generateRegistrationOTPMail(user.get(),otp);
        emailService.sendEmail(emailDetailsDto);
        return new LocalResponse(StatusCode.successful,"OTP Successfully Resent");
    }

}
