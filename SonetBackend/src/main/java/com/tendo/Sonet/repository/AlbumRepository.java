package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AlbumRepository extends JpaRepository<Album, Long>
{
}
