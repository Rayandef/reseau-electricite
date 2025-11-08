package menus;

import java.util.Scanner;
import model.Reseau;

public class MenuSynthese {
    //Affichage de la deuxième interface
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

        try{
            while (running) {
                //Genere l'interface dans le terminal
                System.out.println("Menu 2 :");
                System.out.println("1) Calculer le coût du réseau électrique actuel");
                System.out.println("2) Modifier une connexion");
                System.out.println("3) Afficher le réseau");
                System.out.println("4) Quitter");
                System.out.print("Choix : ");

                int choix;
                //On vérifie que choix est bien un entier
                if (sc.hasNextInt()) {
                    choix = sc.nextInt();
                    sc.nextLine();
                } else {
                    System.out.println("Veuillez entrer un nombre entier valide.");
                    sc.nextLine();
                    continue;
                }

                switch (choix) {
                    case 1 -> { //Cas 1: On calcule le cout total
                        reseau.calculerCout();
                    }

                    case 2 -> {
                        try{ //On essaye de modifier les connexions entre les différents réseaux
                        System.out.println("Entrez la connexion à modifier (ex: M1 G1 ou G1 M1) : ");
                        String ancienA = sc.next();
                        String ancienB = sc.next();

                        System.out.println("Entrez la nouvelle connexion (ex: M2 G2 ou G2 M2) : ");
                        String nouveauA = sc.next();
                        String nouveauB = sc.next();

                        reseau.changerConnexion(ancienA, ancienB, nouveauA, nouveauB);
                        }catch(IllegalArgumentException e){ //Si une erreur est détectée
                            System.err.println("Erreur: " + e.getMessage());
                        }finally{
                            sc.nextLine();
                        }
                    }

                    case 3 -> { //affiche le réseau
                        reseau.afficherReseau();
                    }

                    case 4 -> { //quitte le programme
                        running = false;
                        System.out.println("Fin du programme.");
                    }
                     //Si l'utilisateur a entré autre chose que 1 2 3 4
                    default ->  throw new IllegalArgumentException("Veuillez entrer un entier présent dans le menu.");
                    }
                }
        }catch (IllegalArgumentException e){
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
