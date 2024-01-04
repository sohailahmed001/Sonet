package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>
{
    @Query("SELECT a FROM Artist a LEFT JOIN FETCH a.albums WHERE a.id = :artistId")
    Optional<Artist> findArtistWithAlbums(@Param("artistId") Long artistId);

    @Query("SELECT a FROM Artist a LEFT JOIN FETCH a.sonetUser su LEFT JOIN FETCH su.appUser au LEFT JOIN FETCH au.roles r WHERE au.username = :username AND r.name = 'ROLE_ARTIST'")
    List<Artist> findArtistWithUsername(String username);
}
