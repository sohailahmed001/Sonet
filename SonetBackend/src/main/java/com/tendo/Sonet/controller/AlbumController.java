package com.tendo.Sonet.controller;

import com.tendo.Sonet.dto.AlbumDTO;
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
    private ResponseEntity<AlbumDTO> createOrUpdateAlbum(@RequestBody Album album)
    {
        AlbumDTO updateAlbum = albumService.createOrUpdateAlbum(album);

        return new ResponseEntity<>(updateAlbum, HttpStatus.CREATED);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumDTO> getAlbumByID(@PathVariable(value = "id") Long id)
    {
        AlbumDTO album = albumService.getAlbumByIDWithSongs(id);

        return new ResponseEntity<>(album, HttpStatus.OK);
    }
}
