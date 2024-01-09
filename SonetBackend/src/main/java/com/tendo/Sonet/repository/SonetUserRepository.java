package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.SonetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SonetUserRepository extends JpaRepository<SonetUser, Long>
{
    @Query("SELECT u FROM SonetUser u LEFT JOIN FETCH u.appUser au WHERE au.username = :username")
    List<SonetUser> findByUsername(String username);

    @Query("SELECT u FROM SonetUser u LEFT JOIN FETCH u.likedAlbums LEFT JOIN FETCH u.appUser au WHERE au.username = :username")
    List<SonetUser> findByUsernameWithLikedAlbums(String username);
}
