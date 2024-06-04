package com.frz.inkmason.model.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.model.event.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;

    @OneToOne
    @JoinColumn
    @JsonBackReference
    private Artist artist;

    private Role role;
    private Date onBoardDate;
    private Date confirmedDate;

    @OneToMany(mappedBy = "staff")
    @JsonBackReference
    private List<Booking> bookings;
}
