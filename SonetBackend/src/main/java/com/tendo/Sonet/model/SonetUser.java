package com.tendo.Sonet.model;

import com.tendo.Sonet.dto.RegistrationDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Entity
@Table(name = "sonet_user")
public class SonetUser
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Lob
    private byte[] photo;

    @Transient
    private MultipartFile imageFile; // Transient field to handle file upload

    public SonetUser()
    {
    }

    public SonetUser(RegistrationDTO registrationDTO)
    {
        this.firstName  =   registrationDTO.getFirstName();
        this.middleName =   registrationDTO.getMiddleName();
        this.lastName   =   registrationDTO.getMiddleName();
        this.photo      =   registrationDTO.getPhoto();
    }

    public byte[] getPhoto()
    {
        return photo;
    }

    public void setPhoto(byte[] photo)
    {
        this.photo = photo;
    }

    public MultipartFile getImageFile()
    {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile)
    {
        this.imageFile = imageFile;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
