package com.frz.inkmason.util;

import com.frz.inkmason.dto.auth.EmailDetailsDto;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.User;
import com.frz.inkmason.model.auth.OTP;
import com.frz.inkmason.model.response.LocalResponse;
import com.frz.inkmason.model.response.Response;
import com.frz.inkmason.repository.OTPRepository;
import com.frz.inkmason.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
@RequiredArgsConstructor
@Service
public class OTPUtil {
    private final OTPRepository otpRepository;
    private final UserRepository userRepository;


    public String generateOTP(User user) {
        Random random = new Random();
        Integer number = 100000 + random.nextInt(900000);
        OTP otp = OTP.builder()
                .otp(number.toString())
                .expiryTime(new Date(System.currentTimeMillis() + 3000000))
                .user(user)
                .build();
        otp = otpRepository.save(otp);
        return otp.getOtp();
    }

    public Response validateOTP(User user, String otp) {
        OTP temp = otpRepository.findByUser(user);
        Response response = new LocalResponse();
        if (temp != null && temp.getOtp().equals(otp)) {
            if (temp.getExpiryTime().after(new Date())) {
                user.setVerified(true);
                user = userRepository.save(user);
                response.setStatusCode(StatusCode.successful);
                response.setStatusMessage("OTP Successfully Validated");
            }
            else {
                response.setStatusCode(StatusCode.badRequest);
                response.setStatusMessage("OTP has Expired");
            }
        }
        else {
            response.setStatusCode(StatusCode.badRequest);
            response.setStatusMessage("OTP Not a Match");
        }
        otpRepository.delete(temp);
        return response;

    }

    public String regenerateOTP(User user){
        OTP temp = otpRepository.findByUser(user);
        if(temp != null){
            otpRepository.delete(temp);
        }
        String otp = generateOTP(user);
        return otp;
    }
}
