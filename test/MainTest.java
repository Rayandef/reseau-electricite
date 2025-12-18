import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class MainTest {

    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUpStreams() {
        originalOut = System.out;
        originalErr = System.err;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void mainAvecAucunArgumentParcourtLesDeuxMenus() {
        String input = String.join("\n",
                "1", "G1 50",
                "2", "M1 BASSE",
                "3", "M1 G1",
                "6",               // quitter MenuGestion
                "3"                // quitter MenuSynthese
        ) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[]{});

        String sortie = outContent.toString();
        assertTrue(sortie.contains("Menu principal"));
        assertTrue(sortie.contains("Menu 2"));
    }

    @Test
    public void mainAvecArgumentUniqueChargeDepuisFichierEtParcourtMenusDeuxFois() {
        Path fichierExport = null;
        try {
            fichierExport = Files.createTempFile("reseau-export-main", ".txt");
        } catch (Exception e) {
            fichierExport = Paths.get("export.txt");
        }
        String input = String.join("\n",
                "1",                 // optimiser
                "2", fichierExport.toString(),   // exporter
                "3"                  // quitter
        ) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[]{"instances/instance1.txt"});

        String sortie = outContent.toString();
        assertTrue(sortie.contains("Menu 2"));
        assertTrue(sortie.contains("Fin du programme."));
        try {
            Files.deleteIfExists(fichierExport);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void mainAvecDeuxArgumentsChargeFichierEtLambdaPuisMenus() {
        Path fichierExport = null;
        try {
            fichierExport = Files.createTempFile("reseau-export-main", ".txt");
        } catch (Exception e) {
            fichierExport = Paths.get("export.txt");
        }
        String input = String.join("\n",
                "1",                 // optimiser
                "2", fichierExport.toString(),   // exporter
                "3"                  // quitter
        ) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[]{"instances/instance1.txt", "10"});

        String sortie = outContent.toString();
        assertTrue(sortie.contains("Lambda"));
        assertTrue(sortie.contains("Menu 2"));
        try {
            Files.deleteIfExists(fichierExport);
        } catch (Exception ignored) {
        }
    }
}
