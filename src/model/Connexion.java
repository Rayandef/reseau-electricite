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

    @Override
    
    public String toString() {
        return maison.getNom() + " <-> " + generateur.getNom();
    }
}
