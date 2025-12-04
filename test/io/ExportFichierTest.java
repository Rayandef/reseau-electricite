package io;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
import model.Reseau;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ExportFichierTest {

    @Test
    public void exportEcritGenerateursMaisonsEtConnexions() throws ComposantException, ConnexionNotFoundException, IOException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 100);
        reseau.ajouterGenerateur("G2", 50);
        reseau.ajouterMaison("M1", "BASSE");
        reseau.ajouterMaison("M2", "FORTE");
        reseau.ajouterConnexion("M1", "G1");
        reseau.ajouterConnexion("M2", "G2");

        Path fichier = Files.createTempFile("reseau-export", ".txt");
        try {
            new ExportFichier().export(reseau, fichier.toString());

            List<String> lignes = Files.readAllLines(fichier);
            Set<String> contenu = new HashSet<>(lignes);

            assertTrue(contenu.contains("generateur(G1,100)."));
            assertTrue(contenu.contains("generateur(G2,50)."));
            assertTrue(contenu.contains("maison(M1,BASSE)."));
            assertTrue(contenu.contains("maison(M2,FORTE)."));
            assertTrue(contenu.contains("connexion(M1,G1)."));
            assertTrue(contenu.contains("connexion(M2,G2)."));
        } finally {
            Files.deleteIfExists(fichier);
        }
    }
}
