package com.frz.inkmason.model.auth;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.person.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String otp;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",unique = true)
    @JsonManagedReference()
    private User user;

    private Date expiryTime;
}

