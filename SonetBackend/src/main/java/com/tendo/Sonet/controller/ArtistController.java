package com.tendo.Sonet.controller;

import com.tendo.Sonet.model.Artist;
import com.tendo.Sonet.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sonet")
public class ArtistController
{
    @Autowired
    private ArtistService artistService;

    @PostMapping("/artists")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist)
    {
        Artist createdArtist = artistService.createArtist(artist);

        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtistByID(@PathVariable Long id)
    {
        Artist artist = artistService.getArtistByID(id);

        return new ResponseEntity<>(artist, HttpStatus.FOUND);
    }
}
