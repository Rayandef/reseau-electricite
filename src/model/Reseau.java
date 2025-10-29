package model;

import utils.CalculateurCout;
import java.util.*;

public class Reseau {
    private Map<String, Maison> maisons = new HashMap<>();
    private Map<String, Generateur> generateurs = new HashMap<>();
    private List<Connexion> connexions = new ArrayList<>();

    public void ajouterMaison(String nom, String type) {
        Maison alreadyExist = maisons.get(nom);
        if (alreadyExist != null) {
        switch (type.toUpperCase()) {
            case "BASSE" -> alreadyExist.setConsommation(10);
            case "NORMALE", "NORMAL" -> alreadyExist.setConsommation(20);
            case "FORTE" -> alreadyExist.setConsommation(40);
            default -> System.out.println("Type inconnu : " + type);
        }
        System.out.println("Maison " + nom + " mise à jour (nouvelle consommation : " + alreadyExist.getConsommation() + " kW)");
    } else {
        maisons.put(nom, new Maison(nom, type));
        System.out.println("Maison " + nom + " ajoutée.");
    }
    }

    public void ajouterGenerateur(String nom, int capacite) {
        Generateur alreadyExist = generateurs.get(nom);
        if (alreadyExist != null) {
            alreadyExist.setCapacite(capacite);
            System.out.println("Générateur " + nom + " mis à jour (nouvelle capacité : " + capacite + " kW)");
            return;
        }
        generateurs.put(nom, new Generateur(nom, capacite));
        System.out.println("Générateur " + nom + " ajouté.");
    }

    public void ajouterConnexion(String nomA, String nomB) {
    Maison m = null;
    Generateur g = null;

    if (maisons.containsKey(nomA) && generateurs.containsKey(nomB)) {
        m = maisons.get(nomA);
        g = generateurs.get(nomB);
    } else if (maisons.containsKey(nomB) && generateurs.containsKey(nomA)) {
        m = maisons.get(nomB);
        g = generateurs.get(nomA);
    } else {
        System.out.println("Erreur : impossible de créer la connexion (" + nomA + ", " + nomB + ").");
        System.out.println("Vérifiez que l'un est une maison et l'autre un générateur.");
        return;
    }

    connexions.add(new Connexion(m, g));
    System.out.println("Connexion ajoutée entre " + m.getNom() + " et " + g.getNom());
}

    public void afficherReseau() {
        System.out.println("\n--- Réseau électrique ---");
        System.out.println("Générateurs : " + generateurs.values());
        System.out.println("Maisons : " + maisons.values());
        System.out.println("Connexions : ");
        connexions.forEach(c -> System.out.println("  " + c));
    }

    public void changerConnexion(String ancienA, String ancienB, String nouveauA, String nouveauB) {
        Maison ancienneMaison = null, nouvelleMaison = null;
        Generateur ancienGenerateur = null, nouveauGenerateur = null;
        if (maisons.containsKey(ancienA) && generateurs.containsKey(ancienB)) {
            ancienneMaison = maisons.get(ancienA);
            ancienGenerateur = generateurs.get(ancienB);
        } else if (maisons.containsKey(ancienB) && generateurs.containsKey(ancienA)) {
            ancienneMaison = maisons.get(ancienB);
            ancienGenerateur = generateurs.get(ancienA);
        } else {
            System.out.println(" Erreur : ancienne connexion invalide (" + ancienA + ", " + ancienB + ")");
            return;
        }

        if (maisons.containsKey(nouveauA) && generateurs.containsKey(nouveauB)) {
            nouvelleMaison = maisons.get(nouveauA);
            nouveauGenerateur = generateurs.get(nouveauB);
        } else if (maisons.containsKey(nouveauB) && generateurs.containsKey(nouveauA)) {
            nouvelleMaison = maisons.get(nouveauB);
            nouveauGenerateur = generateurs.get(nouveauA);
        } else {
            System.out.println(" Erreur : nouvelle connexion invalide (" + nouveauA + ", " + nouveauB + ")");
            return;
        }
        Connexion ancienneConnexion = null;
        for (Connexion c : connexions) {
            if (c.getMaison().equals(ancienneMaison) && c.getGenerateur().equals(ancienGenerateur)) {
                ancienneConnexion = c;
                break;
            }
        }

        if (ancienneConnexion == null) {
            System.out.println("  Aucune connexion trouvée entre " + ancienA + " et " + ancienB);
            return;
        }
        ancienneConnexion.getMaison().setConnected(ancienneConnexion.getMaison().getConnected() - 1);
        ancienneConnexion.setMaison(nouvelleMaison);
        ancienneConnexion.setGenerateur(nouveauGenerateur);
        nouvelleMaison.setConnected(nouvelleMaison.getConnected() + 1);
        System.out.println(" Connexion modifiée : " + ancienneMaison.getNom() + " <-> " + ancienGenerateur.getNom() +
                        " devient " + nouvelleMaison.getNom() + " <-> " + nouveauGenerateur.getNom());
    }


    public void calculerCout() {
        CalculateurCout.calculer(this);
    }

    public Map<String, Maison> getMaisons(){ 
        return maisons; 
    }

    public Map<String, Generateur> getGenerateurs(){ 
        return generateurs; 
    }

    public List<Connexion> getConnexions(){ 
        return connexions; 
    }
}
