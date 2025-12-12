package utils;

import java.util.ArrayList;
import model.Connexion;
import model.Generateur;
import model.Maison;
import model.Reseau;

public class AlgoBestConnexion {
    /*Logique de l'algorithme: On trie les maisons dans l'ordre décroissant afin de stocker ceux qui ont la plus grosse consommation en premier.
    Puis, cherche tout d'abord à répartir les maisons de manière équitable sur les générateurs sans créer de surcharge.
    Lorsque la surcharge est inévitable: On ajoute la maison lorsque le coût est minimal */
   public void algoBestConnexion(Reseau r) {
    r.maisReverseSort(); //On trie dans l'ordre décroissant les maisons en fonction de leur consommation
    r.getConnexions().clear(); //On vide les connexions existantes

    for (Generateur g : r.getGenerateurs())g.setCharge(0);  //Réinitialisation des charges
    for (Maison m: r.getMaisons())m.setConnected(0); //Réinitialisation des connexions

    for (Maison m : r.getMaisons()) { //Parcours de toutes les maisons

        Generateur meilleurGenerateur = null; //Initialisation du meilleur générateur
        double bestDisp = Double.MAX_VALUE; //Initialisation de la meilleure dispersion

        for (Generateur g: r.getGenerateurs()){ //Parcours de tous les générateurs

            if (g.getCharge() + m.getConsommation() <= g.getCapaciteMax()) { //S'il n'y a pas de surcharge
                g.setCharge(g.getCharge()+m.getConsommation()); //On simule l'ajout de la maison
                double disp = CalculateurCout.getDispersion(r.getGenerateurs()); //On calcule la dispersion

                if ((meilleurGenerateur == null) || (disp < bestDisp)) { //Si c'est le premier générateur testé ou qu'on trouve une meilleure dispersion
                    //On l'ajoute comme meilleur générateur et meilleure dispersion
                    meilleurGenerateur = g;
                    bestDisp = disp;

                }
                g.setCharge(g.getCharge() - m.getConsommation()); //Backtrack, on passe au générateur suivant
            }
        }

        if (meilleurGenerateur == null) { //Gestion ajout maisons dans des générateurs entrainant une surcharge
            ArrayList<Double> Results = new ArrayList<>();
            double meilleurCout = Double.MAX_VALUE;
            //Même logique que précédemment, au lieu d'évaluer la dispersion on évalue le coût potentiel
            for (Generateur g: r.getGenerateurs()){
                g.setCharge(g.getCharge()+m.getConsommation());
                //On a stocké les données dans une ArrayList avec la dispersion en indice 0, la surcharge en indice 1 et le cout en indice 2
                Results = CalculateurCout.getResult(r);
                if ((meilleurGenerateur == null) || Results.get(2) < meilleurCout){
                    meilleurGenerateur = g;
                    meilleurCout = Results.get(2);
                }
                g.setCharge(g.getCharge() - m.getConsommation());
            }
        }

        if (meilleurGenerateur != null){ //On crée la connexion avec le meilleur générateur trouvé
            r.getConnexions().add(new Connexion(m, meilleurGenerateur));
            meilleurGenerateur.setCharge(m.getConsommation()+meilleurGenerateur.getCharge());
            m.setConnected(m.getConnected() + 1);
        }
    }
    }
}
