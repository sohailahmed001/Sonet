package com.tendo.Sonet.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
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
    @Column(name = "primary_photo", nullable = false)
    private byte[] primaryPhoto;

    @Lob
    @Column(name = "audio_file", nullable = false)
    private byte[] audioFile;

    public Song()
    {
    }
}