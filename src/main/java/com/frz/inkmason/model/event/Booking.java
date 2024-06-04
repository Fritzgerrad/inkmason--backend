package com.frz.inkmason.model.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.enums.BookingMode;
import com.frz.inkmason.model.person.Customer;
import com.frz.inkmason.model.person.Guest;
import com.frz.inkmason.model.person.Staff;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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

    @ManyToOne
    @JsonManagedReference
    private Customer customer;

    @ManyToOne
    @JsonManagedReference
    private Guest guest;

    private BookingMode mode;
    private Date createdDate;
    private Date bookingDate;
    private String contactInformation;
    private String bookingTime;
    private boolean isGuest;

}
