package model;

import java.util.Objects;

public class Maison {
    private String nom;
    private int consommation; // en kW
    private int connected;

    public Maison(String nom, String type) {
        this.nom = nom;
        this.connected = 0;
        switch (type.toUpperCase()) {
            case "BASSE" -> this.consommation = 10;
            case "NORMALE", "NORMAL" -> this.consommation = 20;
            case "FORTE" -> this.consommation = 40;
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        }
    }

    //Getters et setters des différents attributs de la maison
    public String getNom(){ 
        return nom; 
    }
    
    public int getConsommation(){ 
        return consommation; 
    }

    public int getConnected() {
        return connected;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setConsommation(int consommation) {
        this.consommation = consommation;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    /*Réecriture de la méthode equals qui permet de savoir si l'instance de la maison 
    est égale à celle qui est passée en argument*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maison maison = (Maison) o;
        return (consommation == maison.consommation) && Objects.equals(nom, maison.nom);
    }

    //Réecriture de la méthtode toString qui permet de retourner le nom et la consommation d'une maison
    @Override
    public String toString() {
        return nom + " (" + consommation + " kW)";
    }
}
