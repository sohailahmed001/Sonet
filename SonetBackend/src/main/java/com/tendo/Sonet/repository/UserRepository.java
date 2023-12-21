package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long>
{
    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    List<AppUser> findByUsername(String username);

    @Query("SELECT DISTINCT u FROM AppUser u " +
            "LEFT JOIN FETCH u.roles r " +
            "LEFT JOIN FETCH r.authorities " +
            "WHERE u.username = :username")
    List<AppUser> findByUsernameWithRolesAndAuthorities(String username);

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<AppUser> findByIdWithRoles(Long id);
}
