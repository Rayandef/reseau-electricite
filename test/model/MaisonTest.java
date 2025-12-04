package model;

import exception.ComposantException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MaisonTest {

    @Test
    public void constructeurAssigneConsommationSelonType() throws ComposantException {
        assertEquals(10, new Maison("B1", "BASSE").getConsommation());
        assertEquals(20, new Maison("N1", "NORMALE").getConsommation());
        assertEquals(20, new Maison("N2", "NORMAL").getConsommation());
        assertEquals(40, new Maison("F1", "FORTE").getConsommation());
    }

    @Test(expected = ComposantException.class)
    public void constructeurTypeInconnuLanceException() throws ComposantException {
        new Maison("X1", "INCONNU");
    }

    @Test
    public void mutateursEtEqualsFonctionnent() throws ComposantException {
        Maison maison = new Maison("M1", "BASSE");
        maison.setNom("M2");
        maison.setConsommation(30);
        maison.setConnected(2);

        assertEquals("M2", maison.getNom());
        assertEquals(30, maison.getConsommation());
        assertEquals(2, maison.getConnected());

        Maison maisonIdentique = new Maison("M2", "BASSE");
        maisonIdentique.setConsommation(30);
        assertTrue(maison.equals(maisonIdentique));

        Maison maisonDifferentNom = new Maison("M3", "BASSE");
        assertFalse(maison.equals(maisonDifferentNom));
    }

    @Test
    public void toStringRetourneFormatAttendu() throws ComposantException {
        Maison maison = new Maison("MaisonX", "FORTE");
        assertEquals("MaisonX (40 kW)", maison.toString());
    }
}
