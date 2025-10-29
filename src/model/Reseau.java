package model;

import utils.CalculateurCout;
import java.util.*;

public class Reseau {
    private Map<String, Maison> maisons = new HashMap<>();
    private Map<String, Generateur> generateurs = new HashMap<>();
    private List<Connexion> connexions = new ArrayList<>();

    public void ajouterMaison(String nom, String type) {
        maisons.put(nom, new Maison(nom, type));
    }

    public void ajouterGenerateur(String nom, int capacite) {
        generateurs.put(nom, new Generateur(nom, capacite));
    }

    public void ajouterConnexion(String nomMaison, String nomGenerateur) {
        Maison m = maisons.get(nomMaison);
        Generateur g = generateurs.get(nomGenerateur);
        if (m == null || g == null) {
            System.out.println("Erreur : maison ou générateur inexistant.");
            return;
        }
        connexions.add(new Connexion(m, g));
    }

    public void afficherReseau() {
        System.out.println("\n--- Réseau électrique ---");
        System.out.println("Générateurs : " + generateurs.values());
        System.out.println("Maisons : " + maisons.values());
        System.out.println("Connexions : ");
        connexions.forEach(c -> System.out.println("  " + c));
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
