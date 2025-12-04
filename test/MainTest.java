import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

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
                "5",
                "3",
                "5"
        ) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[]{});

        String sortie = outContent.toString();
        assertTrue(sortie.contains("Menu principal"));
        assertTrue(sortie.contains("Menu 2"));
    }

    @Test
    public void mainAvecArgumentUniqueChargeDepuisFichierEtParcourtMenusDeuxFois() {
        String input = String.join("\n",
                "5", // menu gestion 1
                "5", // menu synthese 1
                "5", // menu gestion 2 (apres default)
                "5"  // menu synthese 2
        ) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[]{"instance1.txt"});

        String sortie = outContent.toString();
        assertTrue(sortie.contains("Erreur dans le nombre d'arguments"));
        assertTrue(sortie.contains("Fin du programme."));
    }

    @Test
    public void mainAvecPlusieursArgumentsDeclencheDefault() {
        String input = String.join("\n",
                "1", "G1 50",
                "2", "M1 BASSE",
                "3", "M1 G1",
                "5",               // fin menu gestion
                "5"                // fin menu synthese
        ) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[]{"arg1", "arg2"});

        String sortie = outContent.toString();
        assertTrue(sortie.contains("Erreur dans le nombre d'arguments"));
        assertTrue(sortie.contains("Lancement du programme par d"));
    }
}
