package com.moodify.backend.services.database.objects;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discographies")
@Getter
@Setter
public class DiscographyDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "discography_id")
    private List<MoodifySingleDO> singles;

    @Column(nullable = false)
    private String picture_small;

    @Column(nullable = false)
    private String picture_big;

    public DiscographyDO() {
        this.singles = new ArrayList<MoodifySingleDO>();
    }
}
