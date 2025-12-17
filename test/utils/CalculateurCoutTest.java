package utils;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
import model.Generateur;
import model.Reseau;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculateurCoutTest {

    @Test
    public void calculerRetourneDispersionSansSurcharge() throws ComposantException, ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 60);
        reseau.ajouterGenerateur("G2", 60);

        reseau.ajouterMaison("M1", "FORTE");   // 40 kW
        reseau.ajouterMaison("M2", "FORTE");   // 40 kW
        reseau.ajouterMaison("M3", "NORMALE"); // 20 kW

        reseau.ajouterConnexion("M1", "G1");
        reseau.ajouterConnexion("M2", "G2");
        reseau.ajouterConnexion("M3", "G1");

        List<Double> stats = CalculateurCout.getResult(reseau);
        assertEquals(0.333, stats.get(0), 0.001);
        assertEquals(0.000, stats.get(1), 0.001);
        assertEquals(0.333, stats.get(2), 0.001);
    }

    @Test
    public void calculerAppliquePenaliteDeSurcharge() throws ComposantException, ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 50);
        reseau.ajouterGenerateur("G2", 50);

        reseau.ajouterMaison("M1", "FORTE");   // 40 kW
        reseau.ajouterMaison("M2", "NORMALE"); // 20 kW
        reseau.ajouterMaison("M3", "BASSE");   // 10 kW
        reseau.ajouterMaison("M4", "NORMALE"); // 20 kW
        reseau.ajouterMaison("M5", "BASSE");   // 10 kW

        reseau.ajouterConnexion("M1", "G1");
        reseau.ajouterConnexion("M2", "G1");
        reseau.ajouterConnexion("M3", "G1"); // G1 charge = 70
        reseau.ajouterConnexion("M4", "G2");
        reseau.ajouterConnexion("M5", "G2"); // G2 charge = 30

        List<Double> stats = CalculateurCout.getResult(reseau);
        assertEquals(0.800, stats.get(0), 0.001);
        assertEquals(0.400, stats.get(1), 0.001);
        assertEquals(4.800, stats.get(2), 0.001);
    }

    @Test
    public void getDispersionCalculeLEcartAutourDeLaMoyenneDesTauxDeCharge() {
        List<Generateur> generateurs = Arrays.asList(
                new Generateur("G1", 50),
                new Generateur("G2", 50),
                new Generateur("G3", 50)
        );

        generateurs.get(0).setCharge(10); // taux 0.2
        generateurs.get(1).setCharge(20); // taux 0.4
        generateurs.get(2).setCharge(30); // taux 0.6

        double disp = CalculateurCout.getDispersion(generateurs);

        assertEquals(0.4, disp, 0.0001);
    }
}
