package com.frz.inkmason.service;

import com.frz.inkmason.dto.UserDto;
import com.frz.inkmason.model.AuthenticationResponse;
import com.frz.inkmason.model.Role;
import com.frz.inkmason.model.User;
import com.frz.inkmason.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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


    public AuthenticationResponse register(UserDto userDto){
        
        if(!userRepository.findUserByEmail(userDto.getEmail()).equals(Optional.empty())){
            return new AuthenticationResponse("" ,91,"User Already Exists");
        }

        if(userDto.getRole().equals(Role.admin) && (userDto.getAdminCreationPass() == null || !userDto.getAdminCreationPass().equals(ADMINCREATIONPASS))){
            return new AuthenticationResponse("",92,"You don't have Permission to create an admin");

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
                .build();

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token,00,"Account Created Successfully");
    }

    public AuthenticationResponse authenticate(UserDto userDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        User user = userRepository.findUserByEmail(userDto.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token,00,"Login Successful");
    }
}
