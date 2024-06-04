package com.frz.inkmason.model.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.event.Appointment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Artist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;

    @OneToOne
    @JsonManagedReference
    @JoinColumn()
    private Staff staff;

    private String gender;
    private double rating;
    private String image;
    private String location;
    private String nickname;

    @OneToMany(mappedBy = "artist")
    @JsonBackReference
    private List<Appointment> appointments;

}
