package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.repository.SonetUserRepository;
import com.tendo.Sonet.utils.SONETUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import java.io.IOException;

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
            String imageURL = SONETUtils.processImage(sonetUser.getImageFile(), false);
            savedUser.setPhotoURL(imageURL);
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
