package com.frz.inkmason.repository;

import com.frz.inkmason.model.person.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository  extends JpaRepository<Guest,Long> {
}
