package utils;

import java.util.*;
import model.*;

/**
 * La classe CalculateurCout fournit les outils permettant d evaluer
 * le cout d un reseau electrique en fonction de la repartition des charges
 * entre les generateurs.
 *
 * Co�t(S) = Dispersion(S) + LAMBDA * Surcharge(S)
 */
public class CalculateurCout {
    public static final int LAMBDA = 10;

    /**
     * Calcule et affiche le cout du reseau passe en parametre.
     * Affichage : Disp(S)=X, Surcharge(S)=Y, Cout(S)=Z
     */
    public static void printCout(Reseau reseau) {
        ArrayList<Double> result = getResult(reseau);
        System.out.printf("Disp(S)=%.3f, Surcharge(S)=%.3f, Cout(S)=%.3f%n", result.get(0), result.get(1), result.get(2));
    }

    /**
     * Retourne le cout du reseau passe en parametre.
     *
     * @param reseau le reseau a evaluer
     * @return le cout calcule
     */

    public static ArrayList<Double> getResult(Reseau reseau) {
        ArrayList<Double>allResult = new ArrayList<>();
        double dispersion = getDispersion(reseau.getGenerateurs());
        allResult.add(dispersion);
        double surcharge = getSurcharge(reseau.getGenerateurs());
        allResult.add(surcharge);
        // Calcul du cout total
        double cout = dispersion + LAMBDA * surcharge;
        allResult.add(cout);
        return allResult;
    }

    public static double getSurcharge(List<Generateur> G){
        double surcharge = 0.0;
        // Calcul de la surcharge
        for(Generateur g : G){
            double Cg = (double)g.getCapaciteMax();
            double Lg = (double)g.getCharge();
            if (Lg > Cg){
                surcharge += (Lg - Cg)/ Cg;
            }
        }
        return surcharge;
    }

    public static double getDispersion(List<Generateur> G) {
        int n = G.size();
        double sommeTaux = 0.0;// Somme des charges actuelles de tous les générateurs
        double taux;
        for (Generateur g : G){
            taux = (double)g.getCharge() / (double)g.getCapaciteMax();
            sommeTaux += taux;
        }
        double moyenne = sommeTaux / n;// Charge moyenne du réseau
        double disp = 0;// Dispersion totale
        for (Generateur g : G) {
            taux = (double)g.getCharge() / (double)g.getCapaciteMax();
            disp += Math.abs(taux - moyenne);
        }
        return disp;
    }
}
