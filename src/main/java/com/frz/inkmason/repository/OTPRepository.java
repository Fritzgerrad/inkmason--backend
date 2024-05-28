package com.frz.inkmason.repository;

import com.frz.inkmason.model.User;
import com.frz.inkmason.model.auth.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    OTP findByUser(User user);
}
