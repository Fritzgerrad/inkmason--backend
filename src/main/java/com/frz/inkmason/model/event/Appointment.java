package com.frz.inkmason.model.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="artist_id", referencedColumnName = "id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name="customer_id", referencedColumnName = "id")
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
