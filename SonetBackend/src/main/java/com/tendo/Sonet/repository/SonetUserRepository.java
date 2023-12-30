package com.tendo.Sonet.repository;

import com.tendo.Sonet.model.SonetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SonetUserRepository extends JpaRepository<SonetUser, Long>
{
}
