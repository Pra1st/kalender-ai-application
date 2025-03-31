package com.example.webanwendungenmitjavahausarbeit.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

// hibernate einkaufslisten class
@Entity
@Table(name = "einkaufslisten")
public class Einkaufsliste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "title", nullable = false)
    private String title;


    @OneToMany(mappedBy = "einkaufsliste", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EinkaufslisteItem> items;

    public Einkaufsliste() {}

    public Einkaufsliste(Long userId, LocalDate datum, String title) {
        this.userId = userId;
        this.datum = datum;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return "test";
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public List<EinkaufslisteItem> getItems() {
        return items;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getTitle() {return this.title;}

    public void setItems(List<EinkaufslisteItem> items) {
        this.items = items;
    }
}
