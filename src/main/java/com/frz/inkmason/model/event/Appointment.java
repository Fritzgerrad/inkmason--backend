package com.frz.inkmason.model.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Customer;
import com.frz.inkmason.model.person.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JsonManagedReference
    private Artist artist;

    @ManyToOne
    @JsonManagedReference
    private Customer customer;

    private String description;
    private String title;
    private Date createdDate;
    private Date completedDate;
    private Date startDate;
    private String time;
    private String location;
    private boolean completed;
    private String amount;
    private boolean homeCall;
}
