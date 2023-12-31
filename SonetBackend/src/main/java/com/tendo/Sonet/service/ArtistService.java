package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.Artist;
import com.tendo.Sonet.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService
{
    @Autowired
    private ArtistRepository artistRepository;

    public Artist createArtist(Artist artist)
    {
        return artistRepository.save(artist);
    }

    public Artist getArtistByID(Long id)
    {
        return artistRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException(Artist.class));
    }
}
