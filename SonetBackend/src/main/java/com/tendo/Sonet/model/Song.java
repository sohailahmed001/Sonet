package com.tendo.Sonet.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Entity
public class Song
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long id;

    @Column(nullable = false)
    private String name;
    private Long views;
    private Long likes;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @Lob
    @Column(name = "primary_photo", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] primaryPhoto;

    @Transient
    private MultipartFile songImage;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getViews()
    {
        return views;
    }

    public void setViews(Long views)
    {
        this.views = views;
    }

    public Long getLikes()
    {
        return likes;
    }

    public void setLikes(Long likes)
    {
        this.likes = likes;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum(Album album)
    {
        this.album = album;
    }

    public byte[] getPrimaryPhoto()
    {
        return primaryPhoto;
    }

    public void setPrimaryPhoto(byte[] primaryPhoto)
    {
        this.primaryPhoto = primaryPhoto;
    }

    public MultipartFile getSongImage()
    {
        return songImage;
    }

    public void setSongImage(MultipartFile songImage)
    {
        this.songImage = songImage;
    }

    public byte[] getAudioFile()
    {
        return audioFile;
    }

    public void setAudioFile(byte[] audioFile)
    {
        this.audioFile = audioFile;
    }

    public MultipartFile getTempAudioFile()
    {
        return tempAudioFile;
    }

    public void setTempAudioFile(MultipartFile tempAudioFile)
    {
        this.tempAudioFile = tempAudioFile;
    }

    @Lob
    @Column(name = "audio_file", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] audioFile;

    @Transient
    private MultipartFile tempAudioFile;

    public Song()
    {
    }
}