import model.*;
import menus.MenuGestion;   
import menus.MenuSynthese;
import utils.CalculateurCout;
import io.ImportFichier;
import exception.ComposantException;
import exception.ConnexionNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Reseau reseau = new Reseau();

        switch (args.length) {
            case 0:
                MenuGestion.afficherMenu(sc, reseau);
                MenuSynthese.afficherMenu(sc, reseau);
                break;

            case 1:
                ImportFichier loader = new ImportFichier();
                try {
                    loader.creationReseau(args[0], reseau);
                } catch (ComposantException | ConnexionNotFoundException e) {
                    System.err.println("Erreur lors de l'import du fichier: " + e.getMessage());
                }
                MenuSynthese.afficherMenu(sc, reseau);
                break;

            case 2:
                ImportFichier loader3 = new ImportFichier();
                try {
                    loader3.creationReseau(args[0], reseau);
                } catch (ComposantException | ConnexionNotFoundException e) {
                    System.err.println("Erreur lors de l'import du fichier: " + e.getMessage());
                }
                try {
                    int lambda = Integer.parseInt(args[1]);
                    CalculateurCout.setLambda(lambda);
                    System.out.println("Lambda modifié : " + lambda);
                } catch (NumberFormatException e) {
                    System.err.println("Le troisième argument doit être un entier. Lambda reste par défaut.");
                }
                MenuSynthese.afficherMenu(sc, reseau);
                break;

            default:
                System.out.println("Erreur dans le nombre d'arguments donnés en entrée");
                System.out.println("Lancement du programme par défaut");
                MenuGestion.afficherMenu(sc, reseau);
                MenuSynthese.afficherMenu(sc, reseau);
                break;
        }

        sc.close();
    }
}
