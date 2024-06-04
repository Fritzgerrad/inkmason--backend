package com.frz.inkmason.controller;

import com.frz.inkmason.dto.person.ArtistDto;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {
    public final ArtistService artistService;

    @PostMapping
    public Response create(@RequestBody ArtistDto artistDto){
        return artistService.newArtist(artistDto);
    }

    @GetMapping("/{userId}")
    public Response getArtist(@PathVariable Long userId){
        return artistService.getArtist(userId);
    }

    @GetMapping("/all")
    public Response getAllArtist(){
        return artistService.getAllArtists();
    }

    @DeleteMapping("/admin/{userId}")
    public Response delete(@PathVariable Long userId){
        return artistService.deleteArtist(userId);
    }

    @PutMapping
    public Response update(@RequestBody ArtistDto artistDto){
        return artistService.updateArtist(artistDto);
    }

    @PutMapping("/admin")
    public Response updateByAdmin(@RequestBody ArtistDto artistDto){
        return artistService.adminUpdateArtist(artistDto);
    }
}
