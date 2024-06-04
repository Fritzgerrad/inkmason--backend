package com.frz.inkmason.model.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.enums.BookingMode;
import com.frz.inkmason.model.person.Customer;
import com.frz.inkmason.model.person.Guest;
import com.frz.inkmason.model.person.Staff;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="staff_id", referencedColumnName = "id", columnDefinition = "BIGINT(20) UNSIGNED")
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
