package com.frz.inkmason.controller;

import com.frz.inkmason.dto.person.ArtistDto;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {
    public final ArtistService artistService;
    public final ResponseMaker responseMaker;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ArtistDto artistDto){
        return responseMaker.getResponse(artistService.newArtist(artistDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response> getArtist(@PathVariable Long userId){
        return  responseMaker.getResponse(artistService.getArtist(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllArtist(){
        return responseMaker.getResponse(artistService.getAllArtists());
    }

    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<Response> delete(@PathVariable Long userId){
        return responseMaker.getResponse(artistService.deleteArtist(userId));
    }

    @PutMapping
    public ResponseEntity<Response> update(@RequestBody ArtistDto artistDto,
                           @RequestHeader("Authorization") String token){
        return responseMaker.getResponse(artistService.updateArtist(artistDto, token));
    }

    @PutMapping("/admin")
    public ResponseEntity<Response> updateByAdmin(@RequestBody ArtistDto artistDto){
        return responseMaker.getResponse(artistService.adminUpdateArtist(artistDto));
    }

    @GetMapping("/ratings")
    public ResponseEntity<Response> getRatings(@RequestHeader("Authorization") String token){
        return responseMaker.getResponse(artistService.getCustomerRatings(token));
    }


}
