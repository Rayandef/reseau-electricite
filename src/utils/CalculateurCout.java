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
    public static void calculer(Reseau reseau) {
        CoutResult result = calculerDetails(reseau);
        System.out.printf("Disp(S)=%.3f, Surcharge(S)=%.3f, Cout(S)=%.3f%n", result.dispersion, result.surcharge, result.cout);
    }

    /**
     * Retourne le cout du reseau passe en parametre.
     *
     * @param reseau le reseau a evaluer
     * @return le cout calcule
     */
    public static double cout(Reseau reseau) {
        return calculerDetails(reseau).cout;
    }

    private static CoutResult calculerDetails(Reseau reseau) {
        int n = reseau.getGenerateurs().size();
        double sommeU = 0.0;
        // Recuperation du taux d utilisation de chaque generateur
        Map<Generateur, Double> taux = new HashMap<>();
        for (Generateur g : reseau.getGenerateurs()){
            double u = (double) g.getCharge() / g.getCapaciteMax();
            taux.put(g, u);
            sommeU += u;
        }

        double moyenneU = sommeU / n;
        double dispersion = 0.0;
        double surcharge = 0.0;

        // Calcul de la dispersion et de la surcharge
        for(Generateur g : reseau.getGenerateurs()){
            double u = taux.get(g);
            double Cg = (double)g.getCapaciteMax();
            double Lg = (double)g.getCharge();
            dispersion += Math.abs(u - moyenneU);
            if (Lg > Cg){
                surcharge += (Lg - Cg)/ Cg;
            }
        }
        // Calcul du cout total
        double cout = dispersion + LAMBDA * surcharge;
        return new CoutResult(dispersion, surcharge, cout);
    }

    public static double getDispersion(List<Generateur> G) {
        int n = G.size();
        double somme = 0;// Somme des charges actuelles de tous les générateurs
        for (Generateur g : G)
            somme += g.getCharge();
        double moyenne = somme / n;// Charge moyenne du réseau
        double disp = 0;// Dispersion totale
        for (Generateur g : G)
            disp += Math.abs(g.getCharge() - moyenne);
        return disp;
    }

    private static class CoutResult {
        final double dispersion;
        final double surcharge;
        final double cout;

        CoutResult(double dispersion, double surcharge, double cout) {
            this.dispersion = dispersion;
            this.surcharge = surcharge;
            this.cout = cout;
        }
    }
}
