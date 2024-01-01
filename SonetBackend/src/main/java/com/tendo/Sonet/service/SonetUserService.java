package com.tendo.Sonet.service;

import com.tendo.Sonet.dto.PasswordDTO;
import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.AppUser;
import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.repository.SonetUserRepository;
import com.tendo.Sonet.utils.SONETUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SonetUserService
{
    @Autowired
    private SonetUserRepository sonetUserRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SonetUser updateSonetUser(SonetUser sonetUser)
    {
        SonetUser   savedUser   = getSonetUser(sonetUser);

        if (sonetUser.getImageFile() != null)
        {
            // delete old Photo if new photo is getting uploaded
            if (savedUser.getPhotoURL() != null)
            {
                SONETUtils.deleteIfFileExist(savedUser.getPhotoURL());
            }

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

    public SonetUser getSonetUserByUsername(String username) {
        List<SonetUser> users = this.sonetUserRepository.findByUsername(username);

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        }
        throw new NotFoundException(SonetUser.class);
    }

    public void updatePassword(PasswordDTO passwordDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = this.userService.getUserByUsername(username).orElseThrow(() -> new NotFoundException(AppUser.class));

        if(this.passwordEncoder.matches(passwordDTO.getCurrentPassword(), appUser.getPassword())) {
            appUser.setPassword(this.passwordEncoder.encode(passwordDTO.getNewPassword()));
            this.userService.saveUser(appUser);
        }
        else {
            throw new RuntimeException("Provided current password does not match with your existing password");
        }
    }
}
