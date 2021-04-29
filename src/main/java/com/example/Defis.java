package com.example;

import java.sql.Timestamp;

public class Defis {
    private String id; 
    private String titre; 
    private int idType; 
    private Timestamp dateCreation;  
    private Timestamp dateModification; 
    private String auteur;   
    private String codeArret;
    private int points;
    private String duree;
    private String prologue;
    private String epilogue;
    private String commentaire ;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateModification(Timestamp dateModification) {
        this.dateModification = dateModification;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setCodeArret(String codeArret) {
        this.codeArret = codeArret;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public void setPrologue(String prologue) {
        this.prologue = prologue;
    }

    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getIdType() {
        return idType;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public Timestamp getDateModification() {
        return dateModification;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getCodeArret() {
        return codeArret;
    }

    public int getPoints() {
        return points;
    }

    public String getDuree() {
        return duree;
    }

    public String getPrologue() {
        return prologue;
    }

    public String getEpilogue() {
        return epilogue;
    }

    public String getCommentaire() {
        return commentaire;
    }
      
}