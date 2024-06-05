package com.frz.inkmason.repository;

import com.frz.inkmason.model.event.Booking;
import com.frz.inkmason.model.person.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public List<Booking> findByStaffIsNull();

    public List<Booking> findByStaffIsNotNull();

    public List<Booking> findByStaff(Staff staff);

    public List<Booking> findByBookerId(Long bookerId);
}