package com.tendo.Sonet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tendo.Sonet.dto.RegistrationDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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

    @Column(name = "photo_url")
    private String photoURL;

    @Column(name = "dob", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;

    @JsonIgnoreProperties("sonetUser")
    @OneToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @Transient
    private MultipartFile imageFile; // Transient field to handle file upload

    public SonetUser()
    {
    }

    public SonetUser(RegistrationDTO registrationDTO)
    {
        this.firstName  =   registrationDTO.getFirstName();
        this.middleName =   registrationDTO.getMiddleName();
        this.lastName   =   registrationDTO.getLastName();
        this.photoURL   =   registrationDTO.getPhoto();
        this.dob        =   registrationDTO.getDob();
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhotoURL()
    {
        return photoURL;
    }

    public void setPhotoURL(String photoURL)
    {
        this.photoURL = photoURL;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
