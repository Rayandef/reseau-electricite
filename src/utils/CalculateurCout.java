package utils;

import java.util.*;
import model.*;

public class CalculateurCout {
    public static final int LAMBDA = 10;

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
}
