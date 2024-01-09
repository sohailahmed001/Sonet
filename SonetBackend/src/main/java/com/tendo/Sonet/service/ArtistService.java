package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.Artist;
import com.tendo.Sonet.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Artist getArtistByLoggedInUser()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Artist> artists = this.artistRepository.findArtistWithUsername(username);

        if(artists != null && !artists.isEmpty()) {
            return artists.get(0);
        }
        return null;
    }
}
