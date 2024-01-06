package com.tendo.Sonet.service;

import com.tendo.Sonet.model.Song;
import com.tendo.Sonet.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SongService
{
    @Autowired
    SongRepository songRepository;

    public void deleteSongById(Long id) {
        this.songRepository.deleteById(id);
    }

    public Song saveSong(Song song) {
        return this.songRepository.save(song);
    }
}
