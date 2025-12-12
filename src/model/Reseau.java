package model;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
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
    private List<Maison> maisons = new ArrayList<>();

    /** Ensemble des générateurs du réseau, indexés par leur nom. */
    private List<Generateur> generateurs = new ArrayList<>();

    /** Liste des connexions entre les maisons et les générateurs. */
    private List<Connexion> connexions = new ArrayList<>();

    /**
     * Ajoute une maison au réseau ou met à jour sa consommation si elle existe déjà.
     *
     * @param nom  nom de la maison
     * @param type type de consommation (BASSE, NORMALE, NORMAL, FORTE)
     * @throws ComposantException si le type de consommation est inconnu
     */
    public void ajouterMaison(String nom, String type) throws ComposantException {
        Maison ifExist = new Maison(nom,type);
        if (maisons.contains(ifExist)) {
            switch (type.toUpperCase()) {
                case "BASSE" -> ifExist.setConsommation(10);
                case "NORMALE" -> ifExist.setConsommation(20);
                case "FORTE" -> ifExist.setConsommation(40);
                default -> {
                    throw new ComposantException("Type inconnu : " + type);
                }
            }
            System.out.println("Maison " + ifExist.getNom() + " mise à jour (nouvelle consommation : " + ifExist.getConsommation() + " kW)");
        } else {
            maisons.add(ifExist);
            System.out.println("Maison " + ifExist.getNom() + " ajoutée.");
        }
    }

    /**
     * Ajoute un générateur ou met à jour sa capacité s'il existe déjà.
     *
     * @param nom       nom du générateur
     * @param capacite  capacité maximale en kW
     * @throws ComposantException si la capacité est négative
     */
    public void ajouterGenerateur(String nom, int capacite) throws ComposantException{
        Generateur ifExist = new Generateur(nom, capacite);
        //Si le générateur a une valeur négative, alors on renvoie une erreur
        if (capacite < 0) throw new ComposantException("Erreur: Un générateur ne peut pas avoir une capacite negative.");
        if (generateurs.contains(ifExist)) {
                ifExist.setCapacite(capacite);
                System.out.println("Générateur " + nom + " mis à jour (nouvelle capacité : " + capacite + " kW)");
        }else{
            generateurs.add(ifExist); //On ajoute la pair nom,capacité dans générateur
            System.out.println("Générateur " + nom + " ajouté.");
        }
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
    public void ajouterConnexion(String nomA, String nomB) throws ConnexionNotFoundException {
        Maison m;
        Generateur g;
        if (getMaison(nomA) != null && getGenerateur(nomB) != null) { //On regarde si l'utilisateur a mis le nom de la maison ou le nom du générateur d'abord
            m = getMaison(nomA);
            g = getGenerateur(nomB);
        } else if (getMaison(nomB) != null && getGenerateur(nomA) != null) {
            m = getMaison(nomB);
            g = getGenerateur(nomA);
        } else {
            if(getMaison(nomB) == null && getGenerateur(nomB) == null){ //Si nomB est ni un générateur ni une maison
                throw new ConnexionNotFoundException(nomB + " est ni un generateur, ni une maison");
            }else if(getMaison(nomA) == null & getGenerateur(nomA) == null){ //Si nomA est ni un générateur ni une maison
                throw new ConnexionNotFoundException(nomA + " est ni un generateur, ni une maison");
            }else{
                throw new ConnexionNotFoundException(nomA + " " + nomB + " ne sont ni des generateurs, ni des maisons"); //Si aucun des deux est un générateur ou une maison
            }
    }

    connexions.add(new Connexion(m, g)); //On ajoute la paire m,g dans connexion 
    g.addCharge(m.getConsommation()); //On ajoute la consommation de la maison à la charge du générateur
    System.out.println("Connexion ajoutée entre " + m.getNom() + " et " + g.getNom());
}

    /**
     * Affiche l'ensemble du réseau dans le terminal la liste des générateurs, maisons et connexions.
     */
    public void afficherReseau() { //On affiche le réseau électrique 
        System.out.println("\n--- Réseau électrique ---");
        System.out.println("Générateurs : ");
        generateurs.forEach(g -> System.out.println("  " + g));
        System.out.println("Maisons : ");
        maisons.forEach(m -> System.out.println("  " + m));
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
     * @throws ConnexionNotFoundException si l'ancienne connexion n'existe pas
     *                                  ou si les nouveaux noms sont invalides
     */
    public void changerConnexion(String ancienA, String ancienB, String nouveauA, String nouveauB) throws ConnexionNotFoundException {
        Maison ancienneMaison, nouvelleMaison;
        Generateur ancienGenerateur, nouveauGenerateur;
        if (getMaison(ancienA)!=null && getGenerateur(ancienB)!=null) { //On vérifie que ancienA et ancienB soient bien une paire de maison / générateur
            ancienneMaison = getMaison(ancienA);
            ancienGenerateur = getGenerateur(ancienB);
        } else if (getMaison(ancienB)!=null && getGenerateur(ancienA)!=null) {
            ancienneMaison = getMaison(ancienB);
            ancienGenerateur = getGenerateur(ancienA);
        } else { //Si ce n'est pas le cas alors on retourne une erreur
            throw new ConnexionNotFoundException("Ancienne connexion invalide (" + ancienA + ", " + ancienB + ")");
        }

        if (getMaison(ancienA)!=null && getGenerateur(ancienB)!=null) { //On vérifie que nouveauA et nouveauA soient bien une paire de maison / générateur
            nouvelleMaison = getMaison(nouveauA);
            nouveauGenerateur = getGenerateur(nouveauB);
        } else if (getMaison(ancienB)!=null && getGenerateur(ancienA)!=null) {
            nouvelleMaison = getMaison(nouveauB);
            nouveauGenerateur = getGenerateur(nouveauA);
        } else { //Si ce n'est pas le cas alors on retourne une erreur
            throw new ConnexionNotFoundException("Nouvelle connexion invalide (" + nouveauA + ", " + nouveauB + ")");
        }
        Connexion ancienneConnexion = null;

        for (Connexion c : connexions) { //On vérifie que la connexion entre ancienA et ancienB existe
            if (c.getMaison().equals(ancienneMaison) && c.getGenerateur().equals(ancienGenerateur)) {
                ancienneConnexion = c;
                break;
            }
        }

        if (ancienneConnexion == null) { //Si elle n'existe pas alors on renvoie une erreur
            throw new ConnexionNotFoundException("Aucune connexion trouvée entre " + ancienA + " et " + ancienB);
        }
        //Sinon on décrémente la connexion entre ces maisons, on crée une nouvelle connexion entre nouveauA et nouveauB
        ancienneConnexion.getMaison().setConnected(ancienneConnexion.getMaison().getConnected() - 1);
        ancienneConnexion.getGenerateur().addCharge(-ancienneConnexion.getMaison().getConsommation());
        ancienneConnexion.setMaison(nouvelleMaison);
        ancienneConnexion.setGenerateur(nouveauGenerateur);
        nouvelleMaison.setConnected(nouvelleMaison.getConnected() + 1);
        nouveauGenerateur.addCharge(nouvelleMaison.getConsommation());
        System.out.println(" Connexion modifiée : " + ancienneMaison.getNom() + " <-> " + ancienGenerateur.getNom() + " devient " + nouvelleMaison.getNom() + " <-> " + nouveauGenerateur.getNom());
    }

    /**
     * Supprime une connexion entre une maison et un générateur.
     * Décrémente le nombre de connexions de la maison.
     * @param nomA nom de la maison
     * @param nomB nom du générateur
     * @throws ConnexionNotFoundException si aucune connexion ne correspond aux noms fournis
     */
    public void supprimerConnexion(String nomA, String nomB) throws ConnexionNotFoundException {
        Connexion connexion = null;
        for (Connexion c : connexions) { //On cherche si la connexion entre nomA et nomB existe
            if (c.getMaison().getNom().equals(nomA) && c.getGenerateur().getNom().equals(nomB)) {
                connexion = c;
                break;
            }
        }

        if (connexion != null) { //Si elle existe alors on retire la connexion
            connexion.getMaison().setConnected(connexion.getMaison().getConnected() - 1);
            connexion.getGenerateur().addCharge(-connexion.getMaison().getConsommation());
            connexions.remove(connexion);
        } else { //Sinon on renvoie une erreur
            throw new ConnexionNotFoundException("Aucune connexion trouvée entre " + nomA + " et " + nomB);
        }
    }

    /**
     * Calcule et affiche le coût total du réseau en utilisant {@link CalculateurCout}.
     */
    public void calculerCout() {
        CalculateurCout.printCout(this);
    }

    //Getters et setters des différents attributs de Reseau
    /**
     * Retourne la collection des maisons du réseau.
     *
     * @return une map des maisons indexées par nom
     */
    public List<Maison> getMaisons(){ 
        return maisons; 
    }

    private Maison getMaison(String nom) {
        for (Maison m : maisons) {
            if (m.getNom().equals(nom)) {
                return m;
            }
        }
        return null;
    }

    private Generateur getGenerateur(String nom) {
        for (Generateur g : generateurs) {
            if (g.getNom().equals(nom)) {
                return g;
            }
        }
        return null;
    }
    /**
     * Retourne la collection des générateurs du réseau.
     *
     * @return une map des générateurs indexés par nom
     */
    public List<Generateur> getGenerateurs() {
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

    public void genReverseSort(){
        this.generateurs.sort(Comparator.comparingInt(Generateur::getCapaciteMax).reversed());
    }

    public void maisReverseSort(){
        this.maisons.sort(Comparator.comparingInt(Maison::getConsommation).reversed());
    }
}
