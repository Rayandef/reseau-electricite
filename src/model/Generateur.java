package model;

/**
 * Cette classe ({@code Generateur}) représente un générateur électrique du réseau.
 * <p>
 * Un générateur possède un nom et une capacité maximale de production exprimée
 * en kilowatts (kW). 
 */
public class Generateur {

    /** Nom du générateur. */
    private String nom;

    /** Capacité maximale de production (en kW). */
    private int capaciteMax;

    /** Charge actuelle du générateur */
    private int charge;
    /**
     * Construit un générateur électrique avec un nom et une capacité maximale.
     *
     * @param nom          le nom du générateur
     * @param capaciteMax  la puissance maximale produite par le générateur en kW
     */
    public Generateur(String nom, int capaciteMax) {
        this.nom = nom;
        this.capaciteMax = capaciteMax;
    }

    /**
     * Retourne le nom du générateur.
     *
     * @return le nom du générateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la capacité maximale du générateur.
     *
     * @return la capacité maximale en kW
     */
    public int getCapaciteMax() {
        return capaciteMax;
    }

    /**
     * Retourne la charge actuelle du générateur
     * @return La charge générateur
     */
    public int getCharge(){
        return this.charge;
    }

    /**
     * Modifie le nom du générateur.
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifie la capacité maximale du générateur.
     *
     * @param capaciteMax la nouvelle capacité maximale en kW
     */
    public void setCapacite(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    /**
     * Modifie la charge du générateur
     * @param consommations
     */
    public void setCharge(int consommations){
        this.charge += consommations;
    }

    /**
     * Retourne une description textuelle du générateur sous la forme :
     * <pre>
     *     Nom (X kW max)
     * </pre>
     *
     * @return une chaîne représentant le générateur
     */
    @Override
    public String toString() {
        return nom + " (" + capaciteMax + " kW max)" + "Charge actuelle " + charge + "kW";
    }
}
