package com.tendo.Sonet.service;

import com.tendo.Sonet.dto.AlbumDTO;
import com.tendo.Sonet.dto.SongDTO;
import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.Album;
import com.tendo.Sonet.model.Artist;
import com.tendo.Sonet.model.Song;
import com.tendo.Sonet.repository.AlbumRepository;
import com.tendo.Sonet.utils.SONETUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

        setFileFieldsForAlbum(album);
        return new AlbumDTO(album);
    }

    public Album getAlbumByIDWithSongs(Long id) {
        Album album = albumRepository.findByIdWithSongs(id).orElseThrow(() -> new NotFoundException(Album.class));
        setFileFieldsForAlbumAndSongs(album, true);
        return album;
    }

    public AlbumDTO getAlbumDTOByIdWithSongs(Long id) {
        Album album = getAlbumByIDWithSongs(id);
        return convertToDTO(album, album.getSongs());
    }

    public AlbumDTO publishAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundException(Album.class));
        album.setPublished(Boolean.TRUE);
        albumRepository.save(album);
        return getAlbumByID(id);
    }

    public AlbumDTO getUnpublishedAlbumWithSongsById(Long id) {
        AlbumDTO albumDTO = getAlbumDTOByIdWithSongs(id);
        Assert.isTrue(!albumDTO.isPublished(), "Album is already published");
        return albumDTO;
    }

    public AlbumDTO getPublishedAlbumByIdWithSongs(Long id) {
        AlbumDTO albumDTO = getAlbumDTOByIdWithSongs(id);
        Assert.isTrue(albumDTO.isPublished(), "Album not available");
        return albumDTO;
    }

    public List<AlbumDTO> getAllAlbumsOfArtist(Long artistId) {
        if(artistId == null) {
            artistId = artistService.getArtistByLoggedInUser().getId();
        }
        List<Album> albums = this.albumRepository.findAllByArtistId(artistId);

        if(albums == null) {
            albums = new ArrayList<>();
        }

        albums.forEach(this::setFileFieldsForAlbum);
        return albums.stream().map(AlbumDTO::new).toList();
    }

    private void setFileFieldsForAlbumAndSongs(Album album, boolean withAudio) {
        setFileFieldsForAlbum(album);

        if(album.getSongs() != null) {
            for (Song song : album.getSongs()) {
                if(StringUtils.hasLength(song.getPrimaryPhotoUrl())) {
                    song.setPrimaryImageFile(SONETUtils.retrieveFile(song.getPrimaryPhotoUrl()));
                }
                if(withAudio && StringUtils.hasLength(song.getAudioFileUrl())) {
                    song.setAudioFile(SONETUtils.retrieveFile(song.getAudioFileUrl()));
                }
            }
        }
    }

    private void setFileFieldsForAlbum(Album album) {
        if(StringUtils.hasLength(album.getCoverImageURL())) {
            album.setCoverImageFile(SONETUtils.retrieveFile(album.getCoverImageURL()));
        }
    }

    public AlbumDTO convertToDTO(Album album, List<Song> songs) {
        AlbumDTO albumDTO = new AlbumDTO(album);

        if(songs != null) {
            albumDTO.setSongs(album.getSongs().stream().map(SongDTO::new).toList());
        }
        return albumDTO;
    }

    public List<AlbumDTO> getLatestAlbums() {
        List<Album> albums = this.albumRepository.findByPublishedAndReleaseDateDesc();

        if(albums != null) {
            albums.forEach(this::setFileFieldsForAlbum);
            return albums.stream().map(AlbumDTO::new).toList();
        }
        return null;
    }
}
