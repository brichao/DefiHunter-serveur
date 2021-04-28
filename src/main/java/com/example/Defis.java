package com.example;

import java.sql.Timestamp;

public class Defis {
    public String id; 
    public String defi; 
    public String titre;  
    public String Type;  
    public String auteur;  
    public Timestamp datedecreation;  
    public Timestamp datedemodification;   
    public int Version; 
    public String arret;
    public String code_arret;
    public String motscle;
    public int Points;
    public String duree;
    public String description; 
    public String Prologue;
    public String Epilogue;
    public String commentaire;

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

    public String getdefi(){
        return defi;
    }

    public String getType(){
        return Type;
    }

    public Timestamp getdatedemodification(){
        return datedemodification;
    }

    public int getVersion(){
        return Version;
    }

    public String getarret(){
        return arret;
    }

    public String getcode_arret(){
        return code_arret;
    }

    public String getmotscle(){
        return motscle;
    }

    public int getPoints(){
        return Points;
    }

    public String getduree(){
        return duree;
    }

    public String getPrologue(){
        return Prologue;
    }

    public String getEpilogue(){
        return Epilogue;
    }

    public String getcommentaire(){
        return commentaire;
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

    public void setdefi (String defi) {
        this.defi = defi;
    }

    public void settype (String Type){
        this.Type = Type;
    }

    public void setdatedemodification (Timestamp datedemodification){
        this.datedemodification = datedemodification;
    }

    public void setVersion (int Version){
        this.Version = Version;
    }

    public void setarret (String arret){
        this.arret = arret;
    }

    public void setcodearret (String code_arret){
        this.code_arret = code_arret;
    }

    public void setmotscle (String motscle){
        this.motscle = motscle;
    }

    public void setpoints (int Points){
        this.Points = Points;
    }

    public void setduree (String duree) {
        this.duree = duree;
    }

    public void setdescription (String description){
        this.description = description;
    }

    public void setprologue (String Prologue){
        this.Prologue = Prologue;
    }

    public void setEpilogue (String Epilogue){
        this.Epilogue = Epilogue; 
    }

    public void setCommentaire (String commentaire){
        this.commentaire = commentaire;
    }
}
