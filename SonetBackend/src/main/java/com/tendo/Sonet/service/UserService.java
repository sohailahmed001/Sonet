package com.tendo.Sonet.service;

import com.tendo.Sonet.dto.RegistrationDTO;
import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.*;
import com.tendo.Sonet.repository.*;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.*;

@Service
public class UserService implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SonetUserRepository sonetUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        try
        {
            Optional<AppUser>   userOptional    = getUserByUsername(username);

            if (userOptional.isPresent())
            {
                return userOptional.map(user -> new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user))).get();
            }
        }
        catch (Exception e)
        {
            throw new UsernameNotFoundException("Unable to fetch user: " + username + " due to " + e.getMessage());
        }

        throw new UsernameNotFoundException("User Details not found for username: " + username);
    }

    public Optional<AppUser> getUserByUsername(String username)
    {
        try
        {
            List<AppUser>   usersList   =   this.userRepository.findByUsername(username);

            if (usersList != null && !usersList.isEmpty())
            {
                return Optional.of(usersList.get(0));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    @Transactional
    public AppUser createNewUser(RegistrationDTO registrationDTO)
    {
        AppUser     user        = saveAppUser(registrationDTO);
        SonetUser   sonetUser   = saveSonetUser(registrationDTO);

        // set sonet user for Main User
        user.setSonetUser(sonetUser);

        return userRepository.save(user) ;
    }

    private SonetUser saveSonetUser(RegistrationDTO registrationDTO)
    {
        SonetUser   sonetUser   =   new SonetUser(registrationDTO);
        return sonetUserRepository.save(sonetUser);
    }

    //@TODO give default Role to user !!
    private AppUser saveAppUser(RegistrationDTO registrationDTO)
    {
        AppUser user    =   new AppUser(registrationDTO.getUsername(), registrationDTO.getPassword());
        return addOrUpdateUser(user);
    }

    public AppUser addOrUpdateUser(AppUser user)
    {
        if (user.getId() != null)
        {
            if (user.getPassword() == null)
            {
                AppUser existingUser = getUserById(user.getId()).orElseThrow(() -> new NotFoundException(AppUser.class));
                user.setPassword(existingUser.getPassword());
            }
            else
            {
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            }
        }
        else
        {
            if (user.getPassword() == null || user.getPassword().isEmpty())
            {
                throw new RuntimeException("Password is required");
            }

            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            user.setCreatedDate(new Date());
        }

        return saveUser(user);
    }

    public AppUser saveUser(AppUser user)
    {
        return this.userRepository.save(user);
    }

    private List<SimpleGrantedAuthority> getGrantedAuthorities(AppUser user)
    {
        List<SimpleGrantedAuthority>    grantedAuthorities  =   new ArrayList<>();

        for(Role role : user.getRoles())
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            grantedAuthorities.addAll(role.getAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getName())).toList());
        }

        return grantedAuthorities;
    }

    public List<Authority> getAllAuthorities() {
        return Streamable.of(this.authorityRepository.findAll()).toList();
    }

    public Authority saveAuthority(Authority newAuthority)
    {
        return this.authorityRepository.save(newAuthority);
    }

    public List<AppUser> getAllUsers()
    {
        return Streamable.of(this.userRepository.findAll()).toList();
    }

    public Optional<AppUser> getUserById(Long userId)
    {
        return this.userRepository.findById(userId);
    }

    public void deleteAuthority(Long authorityId)
    {
        this.authorityRepository.deleteById(authorityId);
    }

    public Optional<AppUser> getUserByIdWithRoles(Long userId)
    {
        return this.userRepository.findByIdWithRoles(userId);
    }
}
