package com.frz.inkmason.model.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.Rating;
import com.frz.inkmason.model.event.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;

    private boolean allowsNewsLetter;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Appointment> appointments;


    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Rating> ratings;

}
