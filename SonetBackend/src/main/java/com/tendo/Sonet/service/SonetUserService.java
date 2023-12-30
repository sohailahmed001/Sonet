package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.repository.SonetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import java.io.IOException;
import java.util.logging.*;

@Service
public class SonetUserService
{
    @Autowired
    private SonetUserRepository sonetUserRepository;

    public SonetUser updateSonetUser(SonetUser sonetUser)
    {
        SonetUser   savedUser   = getSonetUser(sonetUser);

        if (sonetUser.getImageFile() != null)
        {
            try
            {
                byte[]  bytes   = sonetUser.getImageFile().getBytes();
                savedUser.setPhoto(bytes);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to save Image !!");
            }
        }

        return sonetUserRepository.save(savedUser);
    }

    private SonetUser getSonetUser(SonetUser sonetUser)
    {
        return sonetUserRepository.findById(sonetUser.getId()).orElseThrow(() -> new NotFoundException(SonetUser.class));
    }

    public SonetUser getSonetUserByID(Long id)
    {
        return this.sonetUserRepository.findById(id)
                                    .orElseThrow(() -> new NotFoundException(SonetUser.class));
    }
}
