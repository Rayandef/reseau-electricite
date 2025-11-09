package model;

/**
 * Cette classe ({@code Connexion}) représente une connexion entre une maison et un générateur dans le réseau électrique.
 * <p>
 * Une connexion lie une instance de {@link Maison} à une instance de {@link Generateur}.  
 * Lors de sa création, elle incrémente automatiquement le compteur de connexions de la maison
 * via {@link Maison#setConnected(int)}.
 */
public class Connexion {

    /** La maison associée à cette connexion. */
    private Maison maison;

    /** Le générateur associé à cette connexion. */
    private Generateur generateur;

    /**
     * Construit une connexion entre une maison et un générateur.
     * <p>
     * À l'ajout de la connexion, le nombre de connexions de la maison est incrémenté de 1.
     * </p>
     *
     * @param maison     la maison connectée au générateur
     * @param generateur le générateur auquel la maison est connectée
     */
    public Connexion(Maison maison, Generateur generateur) {
        this.maison = maison;
        this.generateur = generateur;
        this.maison.setConnected(this.maison.getConnected() + 1);
    }

    /**
     * Retourne la maison associée à cette connexion.
     *
     * @return la maison de la connexion
     */
    public Maison getMaison() {
        return maison;
    }

    /**
     * Retourne le générateur associé à cette connexion.
     *
     * @return le générateur de la connexion
     */
    public Generateur getGenerateur() {
        return generateur;
    }

    /**
     * Modifie le générateur associé à cette connexion.
     *
     * @param generateur le nouveau générateur assigné
     */
    public void setGenerateur(Generateur generateur) {
        this.generateur = generateur;
    }

    /**
     * Modifie la maison associée à cette connexion.
     *
     * @param maison la nouvelle maison assignée
     */
    public void setMaison(Maison maison) {
        this.maison = maison;
    }

    /**
     * Retourne une représentation textuelle de la connexion sous la forme :
     * <pre>
     *     Maison &lt;-&gt; Generateur
     * </pre>
     *
     * @return une chaîne décrivant la connexion
     */
    @Override
    public String toString() {
        return maison.getNom() + " <-> " + generateur.getNom();
    }
}
