package com.frz.inkmason.repository;

import com.frz.inkmason.model.person.Customer;
import com.frz.inkmason.model.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByUser(User user);

}