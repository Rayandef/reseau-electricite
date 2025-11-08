package model;

public class Generateur {
    private String nom;
    private int capaciteMax;

    public Generateur(String nom, int capaciteMax) {
        this.nom = nom;
        this.capaciteMax = capaciteMax;
    }

    //Getter et setter des attributs de Generateur
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

    //La réecriture de la méthode toString qui permet d'écrire le nom et la capacité du générateur
    @Override
    public String toString() {
        return nom + " (" + capaciteMax + " kW max)";
    }

    
}
