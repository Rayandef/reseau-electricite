package model;

public class Connexion {
    private Maison maison;
    private Generateur generateur;

    public Connexion(Maison maison, Generateur generateur) {
        this.maison = maison;
        this.generateur = generateur;
        this.maison.setConnected(this.maison.getConnected() + 1);
    }

    //Getter et setter des différents attributs de Connexion
    public Maison getMaison(){ 
        return maison; 
    }

    public Generateur getGenerateur(){ 
        return generateur; 
    }

    public void setGenerateur(Generateur generateur) {
        this.generateur = generateur;
    }

    public void setMaison(Maison maison) {
        this.maison = maison;
    }

    //La réecriture de la méthode toString qui permet de retourner une connexion entre une maison et un générateur
    @Override
    public String toString() {
        return maison.getNom() + " <-> " + generateur.getNom();
    }
}
