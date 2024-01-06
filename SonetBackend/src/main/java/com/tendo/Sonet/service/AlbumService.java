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
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private SongService songService;

    public AlbumDTO createOrUpdateAlbum(Album album)
    {
        logger.info("Inside Create/Updates");

        boolean isNewObject =   album.getId() == null;

        if (isNewObject)
        {
            return createAlbum(album);
        }

        return updateAlbum(album);
    }

    @Transactional
    private AlbumDTO updateAlbum(Album album)
    {
        Album existingAlbum = getAlbumByIDWithSongs(album.getId());
        List<Song> updatedSongs = album.getSongs();
        List<Song> songsToRemove = existingAlbum.getSongs().stream().filter(song -> !updatedSongs.contains(song)).toList();

        logger.info("Updated Songs: {}", updatedSongs);
        logger.info("PersistedSongs: {}", existingAlbum.getSongs());
        logger.info("Songs to Rem: {}", songsToRemove);

        existingAlbum.setAttribsFrom(album);

        logger.info("Attribs set: {}", existingAlbum.getSongs());

        existingAlbum.getSongs().clear();

        for(Song song: updatedSongs) {
            Song savedSong = this.songService.saveSong(song);
            existingAlbum.getSongs().add(savedSong);
        }

        logger.info("Songs set: {}", existingAlbum.getSongs());

        for(Song song: songsToRemove) {
            logger.info("Deleting song: {}", song);
            this.songService.deleteSongById(song.getId());
            logger.info("Deleted: {}", song);
        }

        logger.info("Songs Remainnig: {}", existingAlbum.getSongs());

        Album saved = albumRepository.save(existingAlbum);
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

    public Album getAlbumByIDWithSongs(Long id) {
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
        return album;
    }

    public AlbumDTO getAlbumDTOByIdWithSongs(Long id) {
        Album album = getAlbumByIDWithSongs(id);
        List<SongDTO> songsDTO = album.getSongs().stream().map(SongDTO::new).toList();
        AlbumDTO albumDTO = new AlbumDTO(album);

        albumDTO.setSongs(songsDTO);
        return albumDTO;
    }
}
