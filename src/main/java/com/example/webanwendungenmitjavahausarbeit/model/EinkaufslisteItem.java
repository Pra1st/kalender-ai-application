package com.example.webanwendungenmitjavahausarbeit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "einkaufsliste_items")
public class EinkaufslisteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "einkaufsliste_id", nullable = false)
    private Einkaufsliste einkaufsliste;

    @Column(name = "item", nullable = false)
    private String item;

    public EinkaufslisteItem() {}

    public EinkaufslisteItem(Einkaufsliste einkaufsliste, String item) {
        this.einkaufsliste = einkaufsliste;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public Einkaufsliste getEinkaufsliste() {
        return einkaufsliste;
    }

    public void setEinkaufsliste(Einkaufsliste einkaufsliste) {
        this.einkaufsliste = einkaufsliste;
    }

    public String getItem() {
        return item;
    }



    public void setItem(String item) {
        this.item = item;
    }
}
