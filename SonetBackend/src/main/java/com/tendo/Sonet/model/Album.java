package com.tendo.Sonet.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.*;

@Entity
public class Album
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "release_date", nullable = false)
    private Date releaseDate;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;

    @Lob
    private byte[] coverImage;

    public Album()
    {
    }
}
