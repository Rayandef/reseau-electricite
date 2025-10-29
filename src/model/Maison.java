package model;

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

    @Override

    public String toString() {
        return nom + " (" + consommation + " kW)";
    }
}
