package com.example;

import java.sql.Timestamp;

public class Defis {
    private String id;
    private String titre;
    private Timestamp datedecreation;
    private String auteur;
    private String arret;
    private String motCles;
    private int points; 
    private int duree;
    private String description;
    private String epilogue;

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

    public String getArret() {
        return arret;
    }

    public String getMotCles() {
        return motCles;
    }
    public int getPoints() {
        return points;
    }

    public int getDuree() {
        return duree;
    }

    public String getEpilogue() {
        return epilogue;
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

    public void setArret(String arret) {
        this.arret = arret;
    }

    public void setMotCles(String motCles) {
        this.motCles = motCles;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }
}
