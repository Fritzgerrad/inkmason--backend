package com.frz.inkmason.model.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.enums.BookingMode;
import com.frz.inkmason.enums.BookingPlatform;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.model.person.Staff;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="staff_id", referencedColumnName = "id")
    @JsonManagedReference
    private Staff staff;

    @Enumerated(EnumType.STRING)
    private Role bookerRole;
    private Long bookerId;
    private String bookerName;

    //@Enumerated(EnumType.STRING)
//    private BookingMode mode;
    private String mode;
    private Date createdDate;
    private Date bookingDate;
    private String contactInformation;
    private String bookingTime;

    //@Enumerated(EnumType.STRING)
    //private BookingPlatform platform;
    private String platform;
    private boolean completed;


}
