package com.tendo.Sonet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "primary_photo_url")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String primaryPhotoUrl;

    @Column(name = "audio_file_url")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String audioFileUrl;

    @Transient
    private byte[] primaryImageFile;

    @Transient
    private byte[] audioFile;

    public Song()
    {
    }

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

    public byte[] getAudioFile()
    {
        return audioFile;
    }

    public void setAudioFile(byte[] audioFile)
    {
        this.audioFile = audioFile;
    }

    public String getPrimaryPhotoUrl() {
        return primaryPhotoUrl;
    }

    public void setPrimaryPhotoUrl(String primaryPhotoUrl) {
        this.primaryPhotoUrl = primaryPhotoUrl;
    }

    public String getAudioFileUrl() {
        return audioFileUrl;
    }

    public void setAudioFileUrl(String audioFileUrl) {
        this.audioFileUrl = audioFileUrl;
    }

    public byte[] getPrimaryImageFile() {
        return primaryImageFile;
    }

    public void setPrimaryImageFile(byte[] primaryImageFile) {
        this.primaryImageFile = primaryImageFile;
    }
}