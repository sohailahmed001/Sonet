package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.Album;
import com.tendo.Sonet.model.Song;
import com.tendo.Sonet.repository.AlbumRepository;
import com.tendo.Sonet.utils.SONETUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumService
{
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistService artistService;

    public Album createOrUpdateAlbum(Album album)
    {
        boolean isNewObject =   album.getId() == null;

        if (isNewObject)
        {
            return createAlbum(album);
        }

        return updateAlbum(album);
    }

    private Album updateAlbum(Album album)
    {
        MultipartFile   coverImageFile  =   album.getCoverImageFile();

        if (coverImageFile != null)
        {
            // delete if already exist !!
            SONETUtils.deleteIfFileExist(album.getCoverImageURL());

            String coverImageURL = SONETUtils.processImage(coverImageFile, false);

            album.setCoverImageURL(coverImageURL);
        }

        return albumRepository.save(album);
    }

    private Album createAlbum(Album album)
    {
        MultipartFile   coverImageFile  =   album.getCoverImageFile();

        if (coverImageFile != null)
        {
            String  coverImageURL   = SONETUtils.processImage(coverImageFile, false);

            album.setCoverImageURL(coverImageURL);
        }

        if(album.getArtist() == null)
        {
            album.setArtist(this.artistService.getArtistByLoggedInUser());
        }

        if(album.getSongs() != null)
        {
            for(Song song: album.getSongs())
            {
                if(song.getPrimaryImageFile() != null)
                {
                    String coverImageURL = SONETUtils.processImage(song.getPrimaryImageFile(), false);
                    song.setPrimaryPhotoUrl(coverImageURL);
                }
            }
        }

        return albumRepository.save(album);
    }

    public Album getAlbumByID(Long id)
    {
        return albumRepository.findById(id).orElseThrow(() -> new NotFoundException(Album.class));
    }
}
