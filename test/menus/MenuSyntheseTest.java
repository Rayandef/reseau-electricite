package menus;

import exception.ComposantException;
import exception.ConnexionNotFoundException;
import model.Reseau;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MenuSyntheseTest {

    @Test
    public void enchaineLesActionsEtExporteLeReseau() throws IOException, ComposantException, ConnexionNotFoundException {
        Reseau reseau = new Reseau();
        reseau.ajouterGenerateur("G1", 100);
        reseau.ajouterGenerateur("G2", 80);
        reseau.ajouterMaison("M1", "NORMALE");
        reseau.ajouterMaison("M2", "BASSE");
        reseau.ajouterConnexion("M1", "G1");
        reseau.ajouterConnexion("M2", "G2");

        Path fichierExport = Files.createTempFile("reseau-export-menu", ".txt");
        String input = String.join("\n",
                "9",              // option invalide -> default
                "3",              // afficher reseau
                "2", "M1 G1", "M1 G2", // changer connexion
                "1",              // calculer cout
                "4", fichierExport.toString(), // exporter
                "5"               // quitter
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        MenuSynthese.afficherMenu(scanner, reseau);

        assertEquals("G2", reseau.getConnexions().get(0).getGenerateur().getNom());
        assertTrue(Files.size(fichierExport) > 0);
        Files.deleteIfExists(fichierExport);
    }
}
