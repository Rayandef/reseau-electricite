package menus;

import model.Reseau;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MenuGestionTest {

    @Test
    public void parcoursCompletAvecImportEtValidationFinale() {
        Reseau reseau = new Reseau();
        String input = String.join("\n",
                "9",               // option invalide -> default
                "1", "G1 60",      // ajout generateur
                "2", "M1 NORMALE", // ajout maison
                "3", "M1 G1",      // connexion
                "4", "M1 G1",      // suppression de la connexion
                "5", "instances/instance1.txt", // import depuis fichier
                "3", "M1 G1",      // reconnexion pour valider le reseau
                "6"                // quitter apres validation
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        MenuGestion.afficherMenu(scanner, reseau);

        assertEquals(1, findMaison(reseau, "M1").getConnected());
        assertEquals(20, reseau.getConnexions().stream()
                .filter(c -> "M1".equals(c.getMaison().getNom()))
                .findFirst()
                .orElseThrow()
                .getGenerateur()
                .getCharge());
    }

    @Test
    public void parcoursBranchesErreursPuisValidation() {
        Reseau reseau = new Reseau();
        String input = String.join("\n",
                "abc",                 // pas un entier -> ignore
                "2", "Mbad INCONNU",   // maison type invalide
                "3", "M1 G1",          // connexion inexistante
                "4", "M1 G1",          // suppression inexistante
                "1", "G1 50",          // ajout generateur
                "2", "M1 BASSE",       // ajout maison valide
                "3", "M1 G1",          // connexion valide
                "6"                    // valider et quitter
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        MenuGestion.afficherMenu(scanner, reseau);

        assertEquals(1, reseau.getConnexions().size());
        assertEquals(1, findMaison(reseau, "M1").getConnected());
    }

    private model.Maison findMaison(Reseau reseau, String nom) {
        Optional<model.Maison> maison = reseau.getMaisons().stream()
                .filter(m -> nom.equals(m.getNom()))
                .findFirst();
        assertNotNull("Maison " + nom + " introuvable", maison.orElse(null));
        return maison.get();
    }
}
