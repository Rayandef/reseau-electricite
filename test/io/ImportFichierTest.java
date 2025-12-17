package io;

import model.Reseau;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;


public class ImportFichierTest {

    @Test 
    public void testImportReseauComplet() throws Exception {
        ImportFichier loader = new ImportFichier();
        Reseau reseau = new Reseau();
        loader.creationReseau("instances/instance1.txt", reseau);

        assertEquals(6, reseau.getGenerateurs().size());
        assertEquals(9, reseau.getMaisons().size());
        assertEquals(9, reseau.getConnexions().size());
    }

    @Test(expected = NumberFormatException.class)
    public void testImportAvecErreursFormatEtNombre() throws Exception {
        String contenu = String.join("\n",
                "generateur(GX,notNumber).", // NumberFormatException
                "maison(MX,INCONNU).",       // ComposantException
                "connexion(MX,GX).",         // ConnexionNotFoundException
                "ligneSansParenthese"        // ComposantException format
        );
        Path temp = Files.createTempFile("reseau-import", ".txt");
        Files.write(temp, contenu.getBytes(StandardCharsets.UTF_8));
        try {
            ImportFichier loader = new ImportFichier();
            Reseau reseau = new Reseau();
            loader.creationReseau(temp.toString(), reseau);
        } finally {
            Files.deleteIfExists(temp);
        }
    }
}
