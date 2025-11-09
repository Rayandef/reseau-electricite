package model;

import java.util.Objects;

/**
 * Cette classe ({@code Maison}) représente une maison connectée au réseau électrique.
 * <p>
 * Une maison possède un nom, une consommation électrique et un compteur de connexions.
 */
public class Maison {

    /** Nom de la maison. */
    private String nom;

    /** Consommation électrique de la maison (en kW). */
    private int consommation;

    /** Nombre de générateurs actuellement connectés à cette maison. */
    private int connected;

    /**
     * Construit une maison en fonction de son nom et de son type de consommation.
     * <p>
     * Le type peut être : BASSE -&gt; 10 kW, NORMALE -&gt; 20 kW, FORTE -&gt; 40 kW.
     * @param nom  le nom de la maison
     * @param type le type de consommation (BASSE, NORMALE, NORMAL, FORTE)
     * @throws IllegalArgumentException si le type est inconnu
     */
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

    /**
     * Retourne le nom de la maison.
     *
     * @return le nom de la maison
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la consommation électrique de la maison.
     *
     * @return la consommation en kW
     */
    public int getConsommation() {
        return consommation;
    }

    /**
     * Retourne le nombre de générateurs actuellement connectés.
     *
     * @return le nombre de connexions
     */
    public int getConnected() {
        return connected;
    }

    /**
     * Modifie le nom de la maison.
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifie la consommation électrique de la maison.
     *
     * @param consommation la nouvelle consommation en kW
     */
    public void setConsommation(int consommation) {
        this.consommation = consommation;
    }

    /**
     * Modifie le nombre de générateurs connectés à cette maison.
     *
     * @param connected le nouveau nombre de connexions
     */
    public void setConnected(int connected) {
        this.connected = connected;
    }

    /**
     * Compare cette maison avec un autre objet pour vérifier leur égalité.
     * <p>
     * Deux maisons sont considérées comme égales si elles ont :
     * <ul>
     *     <li>le même nom,</li>
     *     <li>la même consommation électrique.</li>
     * </ul>
     * @param o l'objet à comparer
     * @return {@code true} si les deux maisons sont égales, sinon {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maison maison = (Maison) o;
        return consommation == maison.consommation && Objects.equals(nom, maison.nom);
    }

    /**
     * Retourne une chaîne descriptive représentant la maison sous la forme :
     * <pre>
     *     Nom (X kW)
     * </pre>
     *
     * @return une représentation textuelle de la maison
     */
    @Override
    public String toString() {
        return nom + " (" + consommation + " kW)";
    }
}
