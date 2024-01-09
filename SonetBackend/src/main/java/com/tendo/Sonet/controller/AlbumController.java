package com.tendo.Sonet.controller;

import com.tendo.Sonet.dto.AlbumDTO;
import com.tendo.Sonet.model.*;
import com.tendo.Sonet.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/albums/publish")
    private ResponseEntity<AlbumDTO> publishAlbum(@RequestBody Album album)
    {
        AlbumDTO updatedAlbum = albumService.createOrUpdateAlbum(album);
        updatedAlbum = albumService.publishAlbum(updatedAlbum.getId());

        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
    }

    @GetMapping("/albums/published/{id}")
    public ResponseEntity<AlbumDTO> getPublishedAlbumByID(@PathVariable(value = "id") Long id)
    {
        AlbumDTO album = albumService.getPublishedAlbumByIdWithSongs(id);

        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/albums/unpublished/{id}")
    public ResponseEntity<AlbumDTO> getUnpublishedAlbumByID(@PathVariable(value = "id") Long id)
    {
        AlbumDTO album = albumService.getUnpublishedAlbumWithSongsById(id);

        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/albums/artist")
    public ResponseEntity<List<AlbumDTO>> getAllAlbumsOfArtist(@PathVariable(value = "id", required = false) Long id) {
        List<AlbumDTO> albums = albumService.getAllAlbumsOfArtist(id);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/albums/latest")
    public ResponseEntity<List<AlbumDTO>> getAllLatestAlbums() {
        List<AlbumDTO> albums = albumService.getLatestAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @PostMapping("/albums/toggleLike/{id}")
    public ResponseEntity<Boolean> toggleLikeAlbum(@PathVariable(value = "id") Long albumId) {
        albumService.toggleLikeAlbum(albumId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
