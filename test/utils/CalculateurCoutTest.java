package utils;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
import model.Generateur;
import model.Reseau;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class CalculateurCoutTest {

    @Test
    public void calculerAfficheDispersionSansSurcharge() throws ComposantException, ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 60);
        reseau.ajouterGenerateur("G2", 60);

        reseau.ajouterMaison("M1", "FORTE");   // 40 kW
        reseau.ajouterMaison("M2", "FORTE");   // 40 kW
        reseau.ajouterMaison("M3", "NORMALE"); // 20 kW

        reseau.ajouterConnexion("M1", "G1");
        reseau.ajouterConnexion("M2", "G2");
        reseau.ajouterConnexion("M3", "G1");

        String sortie = capturerSortie(() -> CalculateurCout.calculer(reseau));

        CoutStats stats = extraireStats(sortie);
        assertEquals(0.333, stats.dispersion, 0.001);
        assertEquals(0.000, stats.surcharge, 0.001);
        assertEquals(0.333, stats.cout, 0.001);
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

        String sortie = capturerSortie(() -> CalculateurCout.calculer(reseau));

        CoutStats stats = extraireStats(sortie);
        assertEquals(0.800, stats.dispersion, 0.001);
        assertEquals(0.400, stats.surcharge, 0.001);
        assertEquals(4.800, stats.cout, 0.001);
    }

    @Test
    public void getDispertionCalculeLecartAutourDeLaMoyenne() {
        List<Generateur> generateurs = Arrays.asList(
                new Generateur("G1", 50),
                new Generateur("G2", 70),
                new Generateur("G3", 90)
        );

        double disp = new CalculateurCout().getDispertion(generateurs);

        assertEquals(40.0, disp, 0.0001);
    }

    private String capturerSortie(Runnable runnable) {
        PrintStream sortieOriginale = System.out;
        ByteArrayOutputStream tampon = new ByteArrayOutputStream();
        System.setOut(new PrintStream(tampon));
        try {
            runnable.run();
        } finally {
            System.setOut(sortieOriginale);
        }
        return tampon.toString();
    }

    private CoutStats extraireStats(String sortie) {
        Pattern p = Pattern.compile("Disp\\(S\\)=([\\d.,]+), Surcharge\\(S\\)=([\\d.,]+), Cout\\(S\\)=([\\d.,]+)");
        Matcher m = p.matcher(sortie.trim());
        if (!m.find()) {
            throw new AssertionError("Format inattendu : " + sortie);
        }
        return new CoutStats(
                parseNombre(m.group(1)),
                parseNombre(m.group(2)),
                parseNombre(m.group(3))
        );
    }

    private double parseNombre(String valeur) {
        return Double.parseDouble(valeur.replace(',', '.'));
    }

    private static class CoutStats {
        final double dispersion;
        final double surcharge;
        final double cout;

        CoutStats(double dispersion, double surcharge, double cout) {
            this.dispersion = dispersion;
            this.surcharge = surcharge;
            this.cout = cout;
        }
    }
}
