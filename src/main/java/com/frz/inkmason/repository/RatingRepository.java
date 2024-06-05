package com.frz.inkmason.repository;

import com.frz.inkmason.model.Rating;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    public List<Rating> findByArtist(Artist artist);

    List<Rating> findByCustomer(Customer customer);
}
