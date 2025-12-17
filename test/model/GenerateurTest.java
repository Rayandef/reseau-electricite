package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenerateurTest {

    @Test
    public void constructeurEtMutateursFonctionnent() {
        Generateur generateur = new Generateur("G1", 50);
        assertEquals("G1", generateur.getNom());
        assertEquals(50, generateur.getCapaciteMax());
        assertEquals(0, generateur.getCharge());

        generateur.setNom("G2");
        generateur.setCapacite(80);
        generateur.setCharge(10);
        generateur.addCharge(5);

        assertEquals("G2", generateur.getNom());
        assertEquals(80, generateur.getCapaciteMax());
        assertEquals(15, generateur.getCharge());
    }

    @Test
    public void toStringAfficheNomCapaciteEtCharge() {
        Generateur generateur = new Generateur("G3", 70);
        generateur.setCharge(20);

        String attendu = "G3 (70 kW max) | Charge actuelle 20kW";
        assertEquals(attendu, generateur.toString());
    }
}
