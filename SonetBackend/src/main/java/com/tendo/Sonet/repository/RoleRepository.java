package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.authorities WHERE r.name = :name")
    List<Role> findByNameWithAuthorities(String name);

    List<Role> findRoleByName(String name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.authorities WHERE r.id = :id")
    Optional<Role> findByIdWithAuthorities(Long id);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.authorities")
    Iterable<Role> findAllWithAuthorities();
}
