package com.example;

import java.sql.Timestamp;

public class Defis {
    private String id;
    private String titre;
    private Timestamp datedecreation;
    private String description;
    private String auteur;

     public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Timestamp getDatedecreation() {
        return datedecreation;
    }

    public String getDescription() {
        return description;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDatedecreation(Timestamp datedecreation) {
        this.datedecreation = datedecreation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
