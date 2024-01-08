package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>
{
    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.songs WHERE a.id = :id")
    Optional<Album> findByIdWithSongs(Long id);

    @Query("SELECT DISTINCT a FROM Album a " +
            "LEFT JOIN a.artist ar " +
            "LEFT JOIN FETCH a.songs " +
            "WHERE a.isPublished = :isPublished AND ar.id = :artistId")
    List<Album> findAllByIsPublishedAndByArtistId(boolean isPublished, Long artistId);

    @Query("SELECT DISTINCT a FROM Album a WHERE a.artist.id = :artistId")
    List<Album> findAllByArtistId(Long artistId);
}
