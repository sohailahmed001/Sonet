package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>
{
    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.songs WHERE a.id = :id")
    Optional<Album> findByIdWithSongs(Long id);
}
