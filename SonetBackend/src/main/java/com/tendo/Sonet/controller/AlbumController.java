package com.tendo.Sonet.controller;

import com.tendo.Sonet.model.*;
import com.tendo.Sonet.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sonet")
public class AlbumController
{

    @Autowired
    private AlbumService albumService;

    @PostMapping("/albums")
    private ResponseEntity<Album> createOrUpdateAlbum(@ModelAttribute Album album)
    {
        Album updateAlbum = albumService.createOrUpdateAlbum(album);

        return new ResponseEntity<>(updateAlbum, HttpStatus.CREATED);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumByID(@PathVariable Long id)
    {
        Album album = albumService.getAlbumByID(id);

        return new ResponseEntity<>(album, HttpStatus.FOUND);
    }
}
