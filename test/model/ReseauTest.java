package model;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ReseauTest {

    @Test
    public void ajouterMaisonCreeOuMetAJour() throws ComposantException {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "BASSE");
        reseau.ajouterMaison("M1", "FORTE"); // mise à jour

        assertEquals(1, reseau.getMaisons().size());
        assertEquals(40, reseau.getMaisons().get("M1").getConsommation());
    }

    @Test
    public void ajouterGenerateurMetAJourCapacite() throws ComposantException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 50);
        reseau.ajouterGenerateur("G1", 80); // mise à jour

        assertEquals(1, reseau.getGenerateurs().size());
        assertEquals(80, findGenerateur(reseau.getGenerateurs(), "G1").getCapaciteMax());
    }

    @Test(expected = ComposantException.class)
    public void ajouterGenerateurCapaciteNegativeLanceException() throws ComposantException {
        new Reseau().ajouterGenerateur("G1", -10);
    }

    @Test
    public void ajouterConnexionIncrementeChargeEtConnected() throws Exception {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "NORMALE"); // 20 kW
        reseau.ajouterGenerateur("G1", 100);

        reseau.ajouterConnexion("M1", "G1");

        assertEquals(1, reseau.getConnexions().size());
        Generateur g1 = findGenerateur(reseau.getGenerateurs(), "G1");
        Maison m1 = reseau.getMaisons().get("M1");
        assertEquals(20, g1.getCharge());
        assertEquals(1, m1.getConnected());
    }

    @Test(expected = ConnexionNotFoundException.class)
    public void ajouterConnexionNomsInvalidesLanceException() throws ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterConnexion("InconnuA", "InconnuB");
    }

    @Test
    public void changerConnexionMetAJourMaisonEtGenerateur() throws Exception {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "NORMALE"); // 20
        reseau.ajouterMaison("M2", "BASSE");   // 10
        reseau.ajouterGenerateur("G1", 100);
        reseau.ajouterGenerateur("G2", 100);

        reseau.ajouterConnexion("M1", "G1"); // G1 charge = 20
        reseau.changerConnexion("M1", "G1", "M2", "G2");

        Connexion c = reseau.getConnexions().get(0);
        assertEquals("M2", c.getMaison().getNom());
        assertEquals("G2", c.getGenerateur().getNom());
        assertEquals(0, reseau.getMaisons().get("M1").getConnected());
        assertEquals(1, reseau.getMaisons().get("M2").getConnected());
        assertEquals(0, findGenerateur(reseau.getGenerateurs(), "G1").getCharge());
        assertEquals(10, findGenerateur(reseau.getGenerateurs(), "G2").getCharge());
    }

    @Test
    public void supprimerConnexionRetireEtMetAJourCharge() throws Exception {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "NORMALE"); // 20
        reseau.ajouterGenerateur("G1", 100);
        reseau.ajouterConnexion("M1", "G1"); // charge = 20

        reseau.supprimerConnexion("M1", "G1");

        assertTrue(reseau.getConnexions().isEmpty());
        assertEquals(0, reseau.getMaisons().get("M1").getConnected());
        assertEquals(0, findGenerateur(reseau.getGenerateurs(), "G1").getCharge());
    }

    private Generateur findGenerateur(List<Generateur> generateurs, String nom) {
        return generateurs.stream()
                .filter(g -> g.getNom().equals(nom))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Generateur introuvable : " + nom));
    }
}
