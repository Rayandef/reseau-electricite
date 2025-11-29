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
}