package com.frz.inkmason.service;

import com.frz.inkmason.dto.person.ArtistDto;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Staff;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.response.ArtistResponseBody;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.repository.ArtistRepository;
import com.frz.inkmason.repository.StaffRepository;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.util.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ArtistService {
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final UserDetailService userService;
    private final StaffRepository staffRepository;
    private final AuthenticationService authenticationService;

    public Response newArtist(ArtistDto artistDto){
        User user  = userRepository.findUserById(artistDto.getUserId());
        if(user == null){
            return new LocalResponse(StatusCode.unknownError.getCode(),"An Unknown Error Occurred");
        }

        Staff staff = new Staff();
        staff.setRole(Role.artist);
        staff.setOnBoardDate(new Date());
        Staff savedStaff = staffRepository.save(staff);

        Artist artist = Artist.builder()
                .image(artistDto.getImage())
                .gender(artistDto.getGender())
                .location(artistDto.getLocation())
                .nickname(artistDto.getNickname())
                .staff(savedStaff)
                .user(user)
                .build();

        Artist savedArtist = artistRepository.save(artist);
        return new BodyResponse<>(StatusCode.successful.getCode() ,"Artist Successfully Created",
                new ArtistResponseBody(savedArtist.getNickname(), savedArtist.getGender(), savedArtist.getRating(), savedArtist.getImage(), savedArtist.getLocation()));
    }

    public Response updateArtist(ArtistDto artistDto){
        User user = userRepository.findUserById(artistDto.getUserId());
        if(user == null){
            return new LocalResponse(StatusCode.badRequest.getCode(), "User not Found");
        }
        Artist artist = artistRepository.findArtistByUser(user);
        if(artist == null){
            return new LocalResponse(StatusCode.badRequest.getCode(), "Artist not Found");
        }
        artist.setGender(artistDto.getGender());
        artist.setLocation(artistDto.getLocation());
        artist.setNickname(artistDto.getNickname());
        artist.setImage(artistDto.getImage());
        Artist savedArtist =  artistRepository.save(artist);
        return new BodyResponse<>(StatusCode.successful.getCode(), "Artist Successfully Updated",savedArtist);
    }

    public Response deleteArtist(ArtistDto artistDto){
        User user = userRepository.findUserById(artistDto.getUserId());
        Artist artist = artistRepository.findArtistByUser(user);
        artistRepository.delete(artist);
        return new LocalResponse(StatusCode.successful.getCode(),"Delete Successful");
    }

    public Response getArtist(ArtistDto artistDto){
        User user = userRepository.findUserById(artistDto.getUserId());
        if(user == null){
            return new LocalResponse(StatusCode.badRequest.getCode(),"Artist not Found");
        }
        Artist artist = artistRepository.findArtistByUser(user);
        ArtistResponseBody artistResponseBody = ArtistResponseBody.builder()
                        .nickname(artist.getNickname())
                                .gender(artist.getGender())
                                        .location(artist.getLocation())
                                                .rating(artist.getRating())
                .build();
        return  new BodyResponse<>(StatusCode.successful.getCode(), "Successful",artist);
    }

    public Response getAllArtist(){
        List<Artist> artists = artistRepository.findAll();
        return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",artists);
    }







}
