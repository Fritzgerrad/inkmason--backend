package com.frz.inkmason.repository;

import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Staff;
import com.frz.inkmason.model.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    public Artist findById(long id);

    public Artist findArtistByUser(User user);

    public Artist deleteById(long id);

}
