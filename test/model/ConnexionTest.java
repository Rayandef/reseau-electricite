package model;

import exception.ComposantException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ConnexionTest {

    @Test
    public void constructeurAssocieEtIncrementeLesConnexions() throws ComposantException {
        Maison maison = new Maison("M1", "NORMALE");
        Generateur generateur = new Generateur("G1", 100);

        Connexion connexion = new Connexion(maison, generateur);

        assertSame(maison, connexion.getMaison());
        assertSame(generateur, connexion.getGenerateur());
        assertEquals(1, maison.getConnected());
        assertEquals("M1 <-> G1", connexion.toString());
    }

    @Test
    public void settersPermettentDeRemplacerMaisonEtGenerateur() throws ComposantException {
        Maison maisonInitiale = new Maison("M1", "BASSE");
        Generateur generateurInitial = new Generateur("G1", 50);
        Connexion connexion = new Connexion(maisonInitiale, generateurInitial);

        Maison nouvelleMaison = new Maison("M2", "FORTE");
        Generateur nouveauGenerateur = new Generateur("G2", 80);

        connexion.setMaison(nouvelleMaison);
        connexion.setGenerateur(nouveauGenerateur);

        assertSame(nouvelleMaison, connexion.getMaison());
        assertSame(nouveauGenerateur, connexion.getGenerateur());
    }
}
