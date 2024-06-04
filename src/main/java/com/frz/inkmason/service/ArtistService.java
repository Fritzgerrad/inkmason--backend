package com.frz.inkmason.service;

import com.frz.inkmason.dto.person.ArtistDto;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.model.response.Response;
import com.frz.inkmason.repository.ArtistRepository;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.util.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ArtistService {
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final UserDetailService userService;
    private final AuthenticationService authenticationService;

    public Response newArtist(ArtistDto artistDto){
        return null;

    }


}
