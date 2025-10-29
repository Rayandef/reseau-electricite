package model;

import java.util.Objects;

public class Maison {
    private String nom;
    private int consommation; // en kW

    public Maison(String nom, String type) {
        this.nom = nom;
        switch (type.toUpperCase()) {
            case "BASSE" -> this.consommation = 10;
            case "NORMALE", "NORMAL" -> this.consommation = 20;
            case "FORTE" -> this.consommation = 40;
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        }
    }

    public String getNom(){ 
        return nom; 
    }
    
    public int getConsommation(){ 
        return consommation; 
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setConsommation(int consommation) {
        this.consommation = consommation;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maison maison = (Maison) o;
        return consommation == maison.consommation &&
               Objects.equals(nom, maison.nom);
    }

    @Override

    public String toString() {
        return nom + " (" + consommation + " kW)";
    }
}
