package model;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ReseauTest {

    @Test
    public void ajouterMaisonAjouteDeuxEntreesSiNomIdentique() throws ComposantException {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "BASSE");
        reseau.ajouterMaison("M1", "FORTE"); // ajoute une seconde entree avec le meme nom

        assertEquals(2, reseau.getMaisons().size());
        long maisonsForte = reseau.getMaisons().stream()
                .filter(m -> "M1".equals(m.getNom()) && m.getConsommation() == 40)
                .count();
        assertEquals(1, maisonsForte);
    }

    @Test
    public void ajouterGenerateurMetAJourCapacite() throws ComposantException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 50);
        reseau.ajouterGenerateur("G1", 80); // ajoute une seconde entree avec le meme nom

        assertEquals(2, reseau.getGenerateurs().size());
        long generateurs80 = reseau.getGenerateurs().stream()
                .filter(g -> "G1".equals(g.getNom()) && g.getCapaciteMax() == 80)
                .count();
        assertEquals(1, generateurs80);
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
        Maison m1 = findMaison(reseau.getMaisons(), "M1");
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
        assertEquals(0, findMaison(reseau.getMaisons(), "M1").getConnected());
        assertEquals(1, findMaison(reseau.getMaisons(), "M2").getConnected());
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
        assertEquals(0, findMaison(reseau.getMaisons(), "M1").getConnected());
        assertEquals(0, findGenerateur(reseau.getGenerateurs(), "G1").getCharge());
    }

    @Test(expected = ConnexionNotFoundException.class)
    public void supprimerConnexionInexistanteLanceException() throws ConnexionNotFoundException {
        new Reseau().supprimerConnexion("M1", "G1");
    }

    @Test(expected = ConnexionNotFoundException.class)
    public void changerConnexionAncienneInvalideLanceException() throws ComposantException, ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "BASSE");
        reseau.ajouterGenerateur("G1", 50);
        reseau.changerConnexion("M1", "G2", "M1", "G1");
    }

    @Test(expected = NullPointerException.class)
    public void changerConnexionNouvelleInvalideDeclencheNullPointer() throws Exception {
        Reseau reseau = new Reseau();
        reseau.ajouterMaison("M1", "BASSE");
        reseau.ajouterMaison("M2", "BASSE");
        reseau.ajouterGenerateur("G1", 50);
        reseau.ajouterConnexion("M1", "G1");

        reseau.changerConnexion("M1", "G1", "M2", "G2");
    }

    @Test(expected = ConnexionNotFoundException.class)
    public void ajouterConnexionNomMaisonInconnuLanceException() throws ComposantException, ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 10);
        reseau.ajouterConnexion("G1", "InconnuMaison");
    }

    private Generateur findGenerateur(List<Generateur> generateurs, String nom) {
        return generateurs.stream()
                .filter(g -> g.getNom().equals(nom))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Generateur introuvable : " + nom));
    }

    private Maison findMaison(List<Maison> maisons, String nom) {
        return maisons.stream()
                .filter(m -> m.getNom().equals(nom))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Maison introuvable : " + nom));
    }
}
