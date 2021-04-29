package com.example;

public class BlocsTexte {
    
    private int blocsTexteId;
    private int questionsId;
    private int indiceId;
    private String texte;
    private String defisId;
    

    public int getBlocsTexteId() {
        return blocsTexteId;
    }

    public int getQuestionsId() {
        return questionsId;
    }

    public int getIndiceId() {
        return indiceId;
    }
    public String getTexte() {
        return texte;
    }
    public String getDefisId() {
        return defisId;
    }

   

    public void setBlocsTexteId(int blocsTexteId) {
        this.blocsTexteId = blocsTexteId;
    }

    public void setQuestionsId(int questionsId) {
        this.questionsId = questionsId;
    }

    public void setIndiceId(int indiceId) {
        this.indiceId = indiceId;
    }
    public void setTexte(String texte) {
        this.texte = texte;
    }
    public void setDefisId(String defisId) {
        this.defisId = defisId;
    }
    
}
