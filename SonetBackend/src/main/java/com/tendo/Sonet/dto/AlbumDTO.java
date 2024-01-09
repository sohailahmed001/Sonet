package com.tendo.Sonet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tendo.Sonet.model.Album;
import com.tendo.Sonet.model.Artist;

import java.util.Date;
import java.util.List;

public class AlbumDTO {
    private Long id;
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private Date createdDate = new Date();
    private boolean isPublished = false;
    private Artist artist;
    private String artistName;
    private List<SongDTO> songs;
    private String coverImageURL;
    private byte[] coverImageFile;
    private boolean isLiked = false;
    private Long likes = 0L;

    public AlbumDTO() {
    }

    public AlbumDTO(Album album) {
        this.id = album.getId();
        this.description = album.getDescription();
        this.isPublished = album.isPublished();
        this.title = album.getTitle();
        this.releaseDate = album.getReleaseDate();
        this.createdDate = album.getCreatedDate();
        this.coverImageFile = album.getCoverImageFile();
        this.coverImageURL = album.getCoverImageURL();
        this.artistName = album.getArtist().getSonetUser().getFirstName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public byte[] getCoverImageFile() {
        return coverImageFile;
    }

    public void setCoverImageFile(byte[] coverImageFile) {
        this.coverImageFile = coverImageFile;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
