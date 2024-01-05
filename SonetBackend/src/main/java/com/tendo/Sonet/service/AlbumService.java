package com.tendo.Sonet.service;

import com.tendo.Sonet.dto.AlbumDTO;
import com.tendo.Sonet.dto.SongDTO;
import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.Album;
import com.tendo.Sonet.model.Song;
import com.tendo.Sonet.repository.AlbumRepository;
import com.tendo.Sonet.utils.SONETUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService
{
    private static Logger logger = LoggerFactory.getLogger(AlbumService.class);
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistService artistService;

    public AlbumDTO createOrUpdateAlbum(Album album)
    {
        boolean isNewObject =   album.getId() == null;

        if (isNewObject)
        {
            return createAlbum(album);
        }

        return updateAlbum(album);
    }

    private AlbumDTO updateAlbum(Album album)
    {
        if(album.getArtist() == null) {
            Album savedAlbum = this.albumRepository.findById(album.getId()).orElseThrow(() -> new NotFoundException(Album.class));
            album.setArtist(savedAlbum.getArtist());
        }
        Album saved = albumRepository.save(album);
        return getAlbumByID(saved.getId());
    }

    private AlbumDTO createAlbum(Album album)
    {
        if(album.getArtist() == null)
        {
            album.setArtist(this.artistService.getArtistByLoggedInUser());
        }
        Album saved = albumRepository.save(album);
        return getAlbumByID(saved.getId());
    }

    public AlbumDTO getAlbumByID(Long id)
    {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundException(Album.class));

        logger.info("Album imageUrl: {}", album.getCoverImageURL());

        if(StringUtils.hasLength(album.getCoverImageURL())) {
            album.setCoverImageFile(SONETUtils.retrieveFile(album.getCoverImageURL()));
        }
        return new AlbumDTO(album);
    }

    public AlbumDTO getAlbumByIDWithSongs(Long id) {
        Album album = albumRepository.findByIdWithSongs(id).orElseThrow(() -> new NotFoundException(Album.class));

        if(StringUtils.hasLength(album.getCoverImageURL())) {
            album.setCoverImageFile(SONETUtils.retrieveFile(album.getCoverImageURL()));
        }

        if(album.getSongs() != null) {
            for (Song song : album.getSongs()) {
                if(StringUtils.hasLength(song.getPrimaryPhotoUrl())) {
                    song.setPrimaryImageFile(SONETUtils.retrieveFile(song.getPrimaryPhotoUrl()));
                }
                if(StringUtils.hasLength(song.getAudioFileUrl())) {
                    song.setAudioFile(SONETUtils.retrieveFile(song.getAudioFileUrl()));
                }
            }
        }

        List<SongDTO> songsDTO = album.getSongs().stream().map(SongDTO::new).toList();
        AlbumDTO albumDTO = new AlbumDTO(album);
        albumDTO.setSongs(songsDTO);
        return albumDTO;
    }
}
