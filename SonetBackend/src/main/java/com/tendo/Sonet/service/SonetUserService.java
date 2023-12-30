package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.repository.SonetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Service
public class SonetUserService
{
    @Autowired
    private SonetUserRepository sonetUserRepository;

    public SonetUser updateSonetUser(SonetUser sonetUser)
    {
        return sonetUserRepository.save(sonetUser);
    }

    public SonetUser getSonetUserByID(Long id)
    {
        return this.sonetUserRepository.findById(id)
                                    .orElseThrow(() -> new NotFoundException(SonetUser.class));
    }
}
