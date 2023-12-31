package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>
{
}
