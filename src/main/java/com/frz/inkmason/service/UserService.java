package com.frz.inkmason.service;

import com.frz.inkmason.dto.auth.*;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.response.AuthResponseBody;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.util.JwtUtil;
import com.frz.inkmason.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class UserService {
    @Value("${adminCreationPass}")
    private String ADMINCREATIONPASS;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OtpUtil otpUtil;


    public Response createUser(CreateUserDto userDto) {

        if (!userRepository.findUserByEmail(userDto.getEmail()).equals(Optional.empty())) {
            return new LocalResponse(StatusCode.badRequest.getCode(),"User Already Exists");
        }

        if (userDto.getRole().equals(Role.admin) && (userDto.getAdminCreationPass() == null || !userDto.getAdminCreationPass().equals(ADMINCREATIONPASS))) {
            return new LocalResponse(StatusCode.unauthorized.getCode(),"You don't have Permission to create an admin");
        }

        User user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .createdAt(new Date())
                .updatedAt(new Date())
                .verified(false)
                .build();

        user = userRepository.save(user);
        String otp = otpUtil.generateOTP(user);

        String token = jwtUtil.generateToken(user);
        EmailDetailsDto emailDetailsDto = emailService.generateRegistrationOTPMail(user,otp);
        emailService.sendEmail(emailDetailsDto);

        return new BodyResponse<AuthResponseBody>( StatusCode.successful.getCode(), "Account Created Successfully",
                new  AuthResponseBody(token, user.getFirstname(), user.getRole(), false,  user.getId()));
    }
}


