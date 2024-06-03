package com.frz.inkmason.util;

import com.frz.inkmason.model.User;
import com.frz.inkmason.model.auth.OTP;
import com.frz.inkmason.repository.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
@RequiredArgsConstructor
@Service
public class OtpUtil {
    private final OTPRepository otpRepository;

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

    public int validateOTP(User user, String otp) {
        OTP temp = otpRepository.findByUser(user);
        if (temp != null && temp.getOtp().equals(otp)) {
            if (temp.getExpiryTime().after(new Date())) {
                otpRepository.delete(temp);
                return 0;
            }
            else {
                return 1;
            }
        }
        else {
            return 2;
        }
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
