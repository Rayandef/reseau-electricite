package model;

public class Generateur {
    private String nom;
    private int capaciteMax;

    public Generateur(String nom, int capaciteMax) {
        this.nom = nom;
        this.capaciteMax = capaciteMax;
    }

    public String getNom(){ 
        return nom; 
    }

    public int getCapaciteMax(){ 
        return capaciteMax; 
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCapacite(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    @Override
    public String toString() {
        return nom + " (" + capaciteMax + " kW max)";
    }

    
}
