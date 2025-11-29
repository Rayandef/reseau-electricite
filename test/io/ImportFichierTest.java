package io;

import model.Reseau;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
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