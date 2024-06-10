package com.frz.inkmason.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id", referencedColumnName = "id")
    @JsonManagedReference
    private Customer customer;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="artist_id", referencedColumnName = "id")
    private Artist artist;

    private double score;
    private String comment;
    private Date createdDate;
}
