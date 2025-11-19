package utils;

import java.util.*;
import model.*;

/**
 * La classe {@code CalculateurCout} fournit les outils permettant d'évaluer
 * le coût d'un réseau électrique en fonction de la répartition des charges
 * entre les générateurs.
 * <p>
 * Le coût total prend en compte deux grandeurs :
 * <ul>
 *     <li>la dispersion des niveaux d'utilisation des générateurs,</li>
 *     <li>la surcharge des générateurs dépassant leur capacité maximale.</li>
 * </ul>
 * Ces valeurs sont combinées selon la formule : Coût(S) = Dispersion(S) + LAMBDA × Surcharge(S)
 *
 * où {@code LAMBDA} est un coefficient fixé à {@code 10}.
 */
public class CalculateurCout {
    public static final int LAMBDA = 10;
    /**
     * Calcule et affiche le coût du réseau passé en paramètre.
     *
     * <p>Le calcul se déroule en plusieurs étapes :</p>
     *
     * <ol>
     *   <li>Calcul de la charge de chaque générateur (somme des consommations des maisons connectées).</li>
     *   <li>Calcul du taux d'utilisation de chaque générateur :
     *   u = charge / capacité_max
     *   </li>
     *   <li>Calcul de l'utilisation moyenne.</li>
     *   <li>Calcul de la dispersion :
     *       Dispersion = &Sigma; |u_i - moyenne|
     *   </li>
     *   <li>Calcul de la surcharge :
     *       Surcharge = &Sigma; max(0, u_i - 1)
     *   </li>
     *   <li>Calcul du coût :
     *       Coût = dispersion + LAMBDA × surcharge
     *   </li>
     * </ol>
     *
     * <p>
     * Le résultat est ensuite affiché sous la forme :  
     * Disp(S)=X, Surcharge(S)=Y, Cout(S)=Z
     *
     * @param reseau le réseau dont il faut calculer le coût
     */
    public static void calculer(Reseau reseau) {
        Map<Generateur, Integer> charge = new HashMap<>();

        for (Connexion c : reseau.getConnexions()) {
            charge.merge(c.getGenerateur(), c.getMaison().getConsommation(), Integer::sum);
        }

        int n = charge.size();
        double sommeU = 0;

        Map<Generateur, Double> utilisation = new HashMap<>();
        for (var entry : charge.entrySet()) {
            double u = (double) entry.getValue() / entry.getKey().getCapaciteMax();
            utilisation.put(entry.getKey(), u);
            sommeU += u;
        }

        double moyenne = sommeU / n;
        double dispersion = 0;
        double surcharge = 0;

        for (var entry : utilisation.entrySet()) {
            double u = entry.getValue();
            dispersion += Math.abs(u - moyenne);
            if (u > 1.0) surcharge += (u - 1.0);
        }

        double cout = dispersion + LAMBDA * surcharge;
        System.out.printf("Disp(S)=%.3f, Surcharge(S)=%.3f, Cout(S)=%.3f%n", dispersion, surcharge, cout);
    }

    public double getDispertion(List<Generateur> G){
        double moyG,disp = 0;
        int u = 0;
        for (Generateur g : G) {
            u += g.getCapaciteMax();
        }
        moyG = u/G.size();
        for (Generateur g : G) {
            disp += Math.abs(g.getCapaciteMax() - moyG);
        }
        return disp;
    }
}
