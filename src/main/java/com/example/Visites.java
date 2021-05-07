package com.example;

import java.sql.Timestamp;

public class Visites {
  
    private String visiteId; 
    private String defisId; 
    private String visiteur; 
    private Timestamp dateVisite;  
    private String modeDP; 
    private int notation;
    private int score;   
    private int temps;
    private String status;
    private String commentaire ;


    public String getVisiteId() {
        return visiteId;
    }

    public String getDefisId() {
        return defisId;
    }

    public String getVisiteur() {
        return visiteur;
    }

    public Timestamp getDateVisite() {
        return dateVisite;
    }

    public String getModeDP() {
        return modeDP;
    }

    public int getNotation() {
        return notation;
    }

    public int getScore() {
        return score;
    }

    public int getTemps() {
        return temps;
    }

    public String getStatus() {
        return status;
    }

    public String getCommentaire() {
        return commentaire;
    }
    

    public void setVisiteId(String visiteId) {
        this.visiteId = visiteId;
    }

    public void setDefisId(String defisId) {
        this.defisId = defisId;
    }
    public void setVisiteur(String visiteur) {
        this.visiteur = visiteur;
    }
    public void setDateVisite(Timestamp dateVisite) {
        this.dateVisite = dateVisite;
    }

    public void setModeDP(String modeDP) {
        this.modeDP = modeDP;
    }

    public void setNotation(int notation) {
        this.notation = notation;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    } 

}
