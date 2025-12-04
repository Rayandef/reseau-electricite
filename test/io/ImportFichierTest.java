package io;

import model.Reseau;
import org.junit.jupiter.api.Test;
import java.io.IOException;


public class ImportFichierTest {

    @Test 
    public void testImportReseauComplet() throws IOException {
        ImportFichier loader = new ImportFichier();
        Reseau reseau = new Reseau();
        loader.creationReseau("instance1.txt", reseau);
        reseau.afficherReseau();
    }

    @Test
    public void testImportAvecErreursFormatEtNombre() throws IOException {
        String contenu = String.join("\n",
                "generateur(GX,notNumber).", // NumberFormatException
                "maison(MX,INCONNU).",       // ComposantException
                "connexion(MX,GX).",         // ConnexionNotFoundException
                "ligneSansParenthese"        // ComposantException format
        );
        java.nio.file.Path temp = java.nio.file.Files.createTempFile("reseau-import", ".txt");
        java.nio.file.Files.write(temp, contenu.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        try {
            ImportFichier loader = new ImportFichier();
            Reseau reseau = new Reseau();
            loader.creationReseau(temp.toString(), reseau);
        } finally {
            java.nio.file.Files.deleteIfExists(temp);
        }
    }
}
