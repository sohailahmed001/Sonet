package com.tendo.Sonet.dto;

import com.tendo.Sonet.model.Song;

import java.util.Date;

public class SongDTO {
    private Long id;

    private String name;

    private Long views = 0L;

    private Long likes = 0L;

    private Long minDuration;

    private Date createdDate = new Date();

    private AlbumDTO albumDTO;

    private String artistName;

    private String primaryPhotoUrl;

    private String audioFileUrl;

    private byte[] primaryImageFile;

    private byte[] audioFile;

    public SongDTO(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.views = song.getViews();
        this.likes = song.getLikes();
        this.minDuration = song.getMinDuration();
        this.createdDate = song.getCreatedDate();
        this.primaryPhotoUrl = song.getPrimaryPhotoUrl();
        this.audioFileUrl = song.getAudioFileUrl();
        this.primaryImageFile = song.getPrimaryImageFile();
        this.audioFile = song.getAudioFile();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Long minDuration) {
        this.minDuration = minDuration;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public AlbumDTO getAlbumDTO() {
        return albumDTO;
    }

    public void setAlbumDTO(AlbumDTO album) {
        this.albumDTO = album;
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

    public byte[] getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(byte[] audioFile) {
        this.audioFile = audioFile;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
