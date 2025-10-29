package model;

public class Connexion {
    private Maison maison;
    private Generateur generateur;

    public Connexion(Maison maison, Generateur generateur) {
        this.maison = maison;
        this.generateur = generateur;
        this.maison.setConnected(this.maison.getConnected() + 1);
    }

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
    @Override
    
    public String toString() {
        return maison.getNom() + " <-> " + generateur.getNom();
    }
}
