package com.frz.inkmason.repository;

import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Staff;
import com.frz.inkmason.model.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByUser(User user);
    Staff findByArtist(Artist artist);
}
