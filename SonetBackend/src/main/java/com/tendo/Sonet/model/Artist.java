package com.tendo.Sonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.*;

@Entity()
public class Artist
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long id;

    private String bio;

    @OneToOne
    @JoinColumn(name = "sonet_user_id", nullable = false)
    private SonetUser sonetUser;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();

    public Artist()
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

    public SonetUser getSonetUser()
    {
        return sonetUser;
    }

    public void setSonetUser(SonetUser sonetUser)
    {
        this.sonetUser = sonetUser;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public List<Album> getAlbums()
    {
        return albums;
    }

    public void setAlbums(List<Album> albums)
    {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
