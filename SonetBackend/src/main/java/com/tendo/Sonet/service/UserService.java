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
    private ArtistRepository artistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        try
        {
            List<AppUser>   users    = this.userRepository.findByUsernameWithRolesAndAuthorities(username);

            if (users != null && !users.isEmpty())
            {
                AppUser user = users.get(0);
                return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
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
    public SonetUser createNewUser(RegistrationDTO registrationDTO)
    {
        AppUser     user            =   createAppUser(registrationDTO);
        SonetUser   sonetUser       =   createSonetUser(registrationDTO, user);
        List<Role>  listenerRoles   =   this.roleRepository.findRoleByName("ROLE_LISTENER");
        List<Role>  artistRoles     =   this.roleRepository.findRoleByName("ROLE_ARTIST");

        if(registrationDTO.getUserType() == UserType.ARTIST) {
            createNewArtist(registrationDTO, sonetUser);

            if(artistRoles != null && !artistRoles.isEmpty()) {
                user.addRole(artistRoles.get(0));
            }
        }

        if(listenerRoles != null && !listenerRoles.isEmpty()) {
            user.addRole(listenerRoles.get(0));
        }

        this.saveUser(user);
        return this.sonetUserRepository.findById(sonetUser.getId()).orElseThrow(() -> new NotFoundException(SonetUser.class));
    }

    private Artist createNewArtist(RegistrationDTO registrationDTO, SonetUser sonetUser) {
        Artist artist = new Artist();
        artist.setSonetUser(sonetUser);
        return this.artistRepository.save(artist);
    }

    private SonetUser createSonetUser(RegistrationDTO registrationDTO, AppUser user) {
        SonetUser   sonetUser   =   new SonetUser(registrationDTO);
        sonetUser.setAppUser(user);
        user.setSonetUser(sonetUser);
        return saveSonetUser(sonetUser);
    }

    private SonetUser saveSonetUser(SonetUser sonetUser)
    {
        return sonetUserRepository.save(sonetUser);
    }

    //@TODO give default Role to user !!
    private AppUser createAppUser(RegistrationDTO registrationDTO)
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
