package utils;

import model.Connexion;
import model.Generateur;
import model.Maison;
import model.Reseau;

public class AlgoBestConnexion {

   public void algoBestConnexion(Reseau r) {
    r.maisReverseSort(); //On trie dans l'ordre décroissant les maisons en fonction de leur consommation
    r.getConnexions().clear(); //On vide les connexions existantes

    for (Generateur g : r.getGenerateurs())g.setCharge(0);  //Réinitialisation des charges
    for (Maison m: r.getMaisons())m.setConnected(0); //Réinitialisation des connexions

    for (Maison m : r.getMaisons()) { //Parcours de toutes les maisons

        Generateur meilleurGenerateur = null; //Initialisation du meilleur générateur
        double bestDisp = 0.0; //Initialisation de la meilleure dispersion

        for (Generateur g: r.getGenerateurs()){ //Parcours de tous les générateurs

            if (g.getCharge() + m.getConsommation() <= g.getCapaciteMax()) { //S'il n'y a pas de surcharge
                g.setCharge(g.getCharge()+m.getConsommation()); //On ajoute la charge temporairement
                double disp = CalculateurCout.getDispersion(r.getGenerateurs()); //On calcule la dispersion

                if (meilleurGenerateur == null){ //Si c'est le premier générateur testé
                    //On l'ajoute comme meilleur générateur et meilleure dispersion
                    meilleurGenerateur = g;
                    bestDisp = disp;

                }else if (disp < bestDisp){ //Sinon on compare la dispersion
                    //Si disp est meilleure, alors elle devient la meilleure dispersion et le meilleur générateur est également remplacé
                    meilleurGenerateur = g;
                    bestDisp = disp;
                }
                g.setCharge(g.getCharge() - m.getConsommation()); //Backtrack, on passe au générateur suivant
            }
        }

        if (meilleurGenerateur == null) { //Gestion ajout maisons dans des générateurs entrainant une surcharge
            //Même logique que précédemment, mais avec les surcharges
            for (Generateur g: r.getGenerateurs()){ 
                g.setCharge(g.getCharge()+m.getConsommation());
                double disp = CalculateurCout.getDispersion(r.getGenerateurs());
                if (meilleurGenerateur == null){
                    meilleurGenerateur = g;
                    bestDisp = disp;
                }else if (disp < bestDisp){
                    meilleurGenerateur = g;
                    bestDisp = disp;
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
