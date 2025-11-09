package model;

import java.util.*;
import utils.CalculateurCout;

/**
 * Cette classe ({@code Reseau}) représente un réseau électrique composé de maisons,
 * de générateurs et de connexions entre eux.
 * <p>
 * Elle permet :
 * <ul>
 *     <li>d'ajouter ou mettre à jour des maisons,</li>
 *     <li>d'ajouter ou mettre à jour des générateurs,</li>
 *     <li>de créer, modifier ou supprimer des connexions,</li>
 *     <li>d'afficher le réseau entier,</li>
 *     <li>de calculer le coût global via {@link CalculateurCout}.</li>
 * </ul>
 */

public class Reseau {
    /** Ensemble des maisons du réseau, indexées par leur nom. */
    private Map<String, Maison> maisons = new HashMap<>();

    /** Ensemble des générateurs du réseau, indexés par leur nom. */
    private Map<String, Generateur> generateurs = new HashMap<>();

    /** Liste des connexions entre les maisons et les générateurs. */
    private List<Connexion> connexions = new ArrayList<>();

    /**
     * Ajoute une maison au réseau ou met à jour sa consommation si elle existe déjà.
     *
     * @param nom  nom de la maison
     * @param type type de consommation (BASSE, NORMALE, NORMAL, FORTE)
     * @throws IllegalArgumentException si le type de consommation est inconnu
     */
    public void ajouterMaison(String nom, String type) {
        Maison alreadyExist = maisons.get(nom);
        if (alreadyExist != null) {
            switch (type.toUpperCase()) {
                case "BASSE" -> alreadyExist.setConsommation(10);
                case "NORMALE" -> alreadyExist.setConsommation(20);
                case "FORTE" -> alreadyExist.setConsommation(40);
                default -> throw new IllegalArgumentException("Type inconnu : " + type);
            }
            System.out.println("Maison " + nom + " mise à jour (nouvelle consommation : " + alreadyExist.getConsommation() + " kW)");
        } else {
            maisons.put(nom, new Maison(nom, type));
            System.out.println("Maison " + nom + " ajoutée.");
        }
    }

    /**
     * Ajoute un générateur ou met à jour sa capacité s'il existe déjà.
     *
     * @param nom       nom du générateur
     * @param capacite  capacité maximale en kW
     * @throws IllegalArgumentException si la capacité est négative
     */
    public void ajouterGenerateur(String nom, int capacite) {
        Generateur alreadyExist = generateurs.get(nom);
        //Si le générateur a une valeur négative, alors on renvoie une erreur
        if (capacite < 0) throw new IllegalArgumentException("Erreur: Un générateur ne peut pas avoir une capacite negative.");
            if (alreadyExist != null) {
                alreadyExist.setCapacite(capacite);
                System.out.println("Générateur " + nom + " mis à jour (nouvelle capacité : " + capacite + " kW)");
                return;
        }
        generateurs.put(nom, new Generateur(nom, capacite)); //On ajoute la pair nom,capacité dans générateur
        System.out.println("Générateur " + nom + " ajouté.");
    }

    /**
     * Ajoute une connexion entre une maison et un générateur.
     * <p>
     * L'ordre des paramètres n'a pas d'importance : la méthode détecte 
     * automatiquement qui est la maison et qui est le générateur.     *
     * @param nomA nom d'une maison ou d'un générateur
     * @param nomB nom d'une maison ou d'un générateur
     * @throws IllegalArgumentException si l'un des noms ne correspond pas
     *         à une maison ou à un générateur
     */
    public void ajouterConnexion(String nomA, String nomB) {
        Maison m;
        Generateur g;
        if (maisons.containsKey(nomA) && generateurs.containsKey(nomB)) { //On regarde si l'utilisateur a mis le nom de la maison ou le nom du générateur d'abord
            m = maisons.get(nomA);
            g = generateurs.get(nomB);
        } else if (maisons.containsKey(nomB) && generateurs.containsKey(nomA)) {
            m = maisons.get(nomB);
            g = generateurs.get(nomA);
        } else {
            if(!((maisons.containsKey(nomB))||(generateurs.containsKey(nomB)))){ //Si nomB est ni un générateur ni une maison
                throw new IllegalArgumentException(nomB + " est ni un generateur, ni une maison");
            }else if(!(maisons.containsKey(nomA) || generateurs.containsKey(nomA))){ //Si nomA est ni un générateur ni une maison
                throw new IllegalArgumentException(nomA + " est ni un generateur, ni une maison");
            }else{
                throw new IllegalArgumentException(nomA + " " + nomB + " ne sont ni des generateurs, ni des maisons"); //Si aucun des deux est un générateur ou une maison
            }
    }

    connexions.add(new Connexion(m, g)); //On ajoute la paire m,g dans connexion 
    System.out.println("Connexion ajoutée entre " + m.getNom() + " et " + g.getNom());
}

    /**
     * Affiche l'ensemble du réseau dans le terminal la liste des générateurs, maisons et connexions.
     */
    public void afficherReseau() { //On affiche le réseau électrique 
        System.out.println("\n--- Réseau électrique ---");
        System.out.println("Générateurs : " + generateurs.values());
        System.out.println("Maisons : " + maisons.values());
        System.out.println("Connexions : ");
        connexions.forEach(c -> System.out.println("  " + c));
    }

    /**
     * Modifie une connexion existante pour la remplacer par une nouvelle.
     *
     * @param ancienA  ancien nom (maison ou générateur)
     * @param ancienB  ancien nom (maison ou générateur)
     * @param nouveauA nouveau nom (maison ou générateur)
     * @param nouveauB nouveau nom (maison ou générateur)
     * @throws IllegalArgumentException si l'ancienne connexion n'existe pas
     *                                  ou si les nouveaux noms sont invalides
     */
    public void changerConnexion(String ancienA, String ancienB, String nouveauA, String nouveauB) {
        Maison ancienneMaison, nouvelleMaison;
        Generateur ancienGenerateur, nouveauGenerateur;
        if (maisons.containsKey(ancienA) && generateurs.containsKey(ancienB)) { //On vérifie que ancienA et ancienB soient bien une pair de maison / générateur
            ancienneMaison = maisons.get(ancienA);
            ancienGenerateur = generateurs.get(ancienB);
        } else if (maisons.containsKey(ancienB) && generateurs.containsKey(ancienA)) {
            ancienneMaison = maisons.get(ancienB);
            ancienGenerateur = generateurs.get(ancienA);
        } else { //Si ce n'est pas le cas alors on retourne une erreur
            throw new IllegalArgumentException("Ancienne connexion invalide (" + ancienA + ", " + ancienB + ")");
        }

        if (maisons.containsKey(nouveauA) && generateurs.containsKey(nouveauB)) { //On vérifie que nouveauA et nouveauA soient bien une pair de maison / générateur
            nouvelleMaison = maisons.get(nouveauA);
            nouveauGenerateur = generateurs.get(nouveauB);
        } else if (maisons.containsKey(nouveauB) && generateurs.containsKey(nouveauA)) {
            nouvelleMaison = maisons.get(nouveauB);
            nouveauGenerateur = generateurs.get(nouveauA);
        } else { //Si ce n'est pas le cas alors on retourne une erreur
            throw new IllegalArgumentException("Nouvelle connexion invalide (" + nouveauA + ", " + nouveauB + ")");
        }
        Connexion ancienneConnexion = null;

        for (Connexion c : connexions) { //On vérifie que la connexion entre ancienA et ancienB existe
            if (c.getMaison().equals(ancienneMaison) && c.getGenerateur().equals(ancienGenerateur)) {
                ancienneConnexion = c;
                break;
            }
        }

        if (ancienneConnexion == null) { //Si elle n'existe pas alors on renvoie une erreur
            throw new IllegalArgumentException("Aucune connexion trouvée entre " + ancienA + " et " + ancienB);
        }
        //Sinon on décrémente la connexion entre ces maisons, on crée une nouvelle connexion entre nouveauA et nouveauB
        ancienneConnexion.getMaison().setConnected(ancienneConnexion.getMaison().getConnected() - 1);
        ancienneConnexion.setMaison(nouvelleMaison);
        ancienneConnexion.setGenerateur(nouveauGenerateur);
        nouvelleMaison.setConnected(nouvelleMaison.getConnected() + 1);
        System.out.println(" Connexion modifiée : " + ancienneMaison.getNom() + " <-> " + ancienGenerateur.getNom() + " devient " + nouvelleMaison.getNom() + " <-> " + nouveauGenerateur.getNom());
    }

    /**
     * Supprime une connexion entre une maison et un générateur.
     * Décrémente le nombre de connexions de la maison.
     * @param nomA nom de la maison
     * @param nomB nom du générateur
     * @throws IllegalArgumentException si aucune connexion ne correspond aux noms fournis
     */
    public void supprimerConnexion(String nomA, String nomB) {
        Connexion connexion = null;
        for (Connexion c : connexions) { //On cherche si la connexion entre nomA et nomB existe
            if (c.getMaison().getNom().equals(nomA) && c.getGenerateur().getNom().equals(nomB)) {
                connexion = c;
                break;
            }
        }

        if (connexion != null) { //Si elle existe alors on retire la connexion
            connexion.getMaison().setConnected(connexion.getMaison().getConnected() - 1);
            connexions.remove(connexion);
        } else { //Sinon on renvoie une erreur
            throw new IllegalArgumentException("Aucune connexion trouvée entre " + nomA + " et " + nomB);
        }
    }

    /**
     * Calcule et affiche le coût total du réseau en utilisant {@link CalculateurCout}.
     */
    public void calculerCout() {
        CalculateurCout.calculer(this);
    }

    //Getters et setters des différents attributs de Reseau
    /**
     * Retourne la collection des maisons du réseau.
     *
     * @return une map des maisons indexées par nom
     */
    public Map<String, Maison> getMaisons(){ 
        return maisons; 
    }

    /**
     * Retourne la collection des générateurs du réseau.
     *
     * @return une map des générateurs indexés par nom
     */
    public Map<String, Generateur> getGenerateurs(){ 
        return generateurs; 
    }

    /**
     * Retourne la liste des connexions du réseau.
     *
     * @return une liste des connexions
     */
    public List<Connexion> getConnexions(){ 
        return connexions; 
    }
}
