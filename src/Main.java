import java.util.Scanner;

import io.ImportFichier;
import menus.*;
import model.*;
import exception.*;

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
                try{
                loader.creationReseau(args[0], reseau);
                }catch (ComposantException | ConnexionNotFoundException e){
                    System.err.println("Erreur lors de l'import du fichier: " + e.getMessage());
                }
                MenuGestion.afficherMenu(sc, reseau);
                MenuSynthese.afficherMenu(sc, reseau);
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
