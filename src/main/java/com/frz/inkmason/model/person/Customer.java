package com.frz.inkmason.model.person;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.event.Appointment;
import com.frz.inkmason.model.event.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;
    private boolean allowsNewsLetter;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Appointment> appointments;

}
