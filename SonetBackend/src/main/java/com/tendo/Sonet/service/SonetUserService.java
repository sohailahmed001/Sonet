package com.tendo.Sonet.service;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.Album;
import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.repository.SonetUserRepository;
import com.tendo.Sonet.utils.SONETUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
        return sonetUserRepository.save(sonetUser);
    }

    public SonetUser getSonetUserByID(Long id)
    {
        return this.sonetUserRepository.findById(id).orElseThrow(() -> new NotFoundException(SonetUser.class));
    }

    public SonetUser getSonetUserByUsername(String username) {
        List<SonetUser> users = this.sonetUserRepository.findByUsername(username);

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        }
        throw new NotFoundException(SonetUser.class);
    }

    public boolean isAlbumLikedByUser(Album album) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SonetUser> sonetUsers = this.sonetUserRepository.findByUsernameWithLikedAlbums(username);
        
        if(!CollectionUtils.isEmpty(sonetUsers)) {
            return sonetUsers.get(0).getLikedAlbums().contains(album);
        }
        throw new RuntimeException("User not found for username: " + username);
    }
}
