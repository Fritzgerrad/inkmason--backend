package com.frz.inkmason.service;

import com.frz.inkmason.dto.person.ArtistDto;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.Rating;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Staff;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.RatingRepository;
import com.frz.inkmason.response.ArtistResponseBody;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.repository.ArtistRepository;
import com.frz.inkmason.repository.StaffRepository;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ArtistService {
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final StaffRepository staffRepository;
    private final JwtUtil jwtUtil;
    private final RatingRepository ratingRepository;

    public Response newArtist(ArtistDto artistDto){
        User user  = userRepository.findUserById(artistDto.getUserId());
        if(user == null){
            return new LocalResponse(StatusCode.unknownError.getCode(),"An Unknown Error Occurred");
        }

        Staff staff = new Staff();
        staff.setRole(Role.artist);
        staff.setUser(user);
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
        return new BodyResponse<>(StatusCode.successful.getCode() ,"Artist Successfully Created", savedArtist);
    }

    public Response updateArtist(ArtistDto artistDto, String token){

        BodyResponse<User> response =  jwtUtil.getUserFromToken(token);
        if(response.getStatusCode() == StatusCode.successful.getCode()){
            return edit(artistDto, response.getData());
        }
        else {
            return response;
        }

    }

    public Response adminUpdateArtist(ArtistDto artistDto){
        User user = userRepository.findUserById(artistDto.getUserId());
        if(user == null){
            return new LocalResponse(StatusCode.unknownError.getCode(),"An Unknown Error Occurred");
        }
        return edit(artistDto, user);
    }

    private Response edit(ArtistDto artistDto, User user) {
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

    public Response deleteArtist(Long userId){
        User user = userRepository.findUserById(userId);
        Artist artist = artistRepository.findArtistByUser(user);
        Staff  staff = staffRepository.findByUser(user);
        artistRepository.delete(artist);
        staffRepository.delete(staff);
        userRepository.delete(user);
        return new LocalResponse(StatusCode.successful.getCode(),"Delete Successful");
    }

    public Response getArtist(Long userId){
        User user = userRepository.findUserById(userId);
        if(user == null){
            return new LocalResponse(StatusCode.badRequest.getCode(),"Artist not Found");
        }
        Artist artist = artistRepository.findArtistByUser(user);
        ArtistResponseBody artistResponseBody = ArtistResponseBody.builder()
                        .nickname(artist.getNickname())
                                .gender(artist.getGender())
                                        .location(artist.getLocation())
                                                .rating(artist.getAverageRating())
                .build();
        return  new BodyResponse<>(StatusCode.successful.getCode(), "Successful",artist);
    }

    public Response getAllArtists(){
        List<Artist> artists = artistRepository.findAll();
        return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",artists);
    }

    public Response getCustomerRatings(String token){
        User user  = jwtUtil.getUserFromToken(token).getData();
        Artist artist = artistRepository.findArtistByUser(user);
        List<Rating> customerRatings = ratingRepository.findByArtist(artist);
        return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",customerRatings);
    }







}
