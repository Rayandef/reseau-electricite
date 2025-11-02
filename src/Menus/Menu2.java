package Menus;

import java.util.Scanner;
import model.Reseau;

public class Menu2 {
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

        try{
            while (running) {
                System.out.println("Menu 2 :");
                System.out.println("1) Calculer le coût du réseau électrique actuel");
                System.out.println("2) Modifier une connexion");
                System.out.println("3) Afficher le réseau");
                System.out.println("4) Quitter");
                System.out.print("Choix : ");

                int choix;
                if (sc.hasNextInt()) {
                    choix = sc.nextInt();
                    sc.nextLine();
                } else {
                    System.out.println("Veuillez entrer un nombre entier valide.");
                    sc.nextLine();
                    continue;
                }

                switch (choix) {
                    case 1 -> {
                        reseau.calculerCout();
                    }

                    case 2 -> {
                        try{
                        System.out.println("Entrez la connexion à modifier (ex: M1 G1 ou G1 M1) : ");
                        String ancienA = sc.next();
                        String ancienB = sc.next();

                        System.out.println("Entrez la nouvelle connexion (ex: M2 G2 ou G2 M2) : ");
                        String nouveauA = sc.next();
                        String nouveauB = sc.next();

                        reseau.changerConnexion(ancienA, ancienB, nouveauA, nouveauB);
                        }catch(IllegalArgumentException e){
                            System.err.println("Erreur: " + e.getMessage());
                        }
                    }

                    case 3 -> {
                        reseau.afficherReseau();
                    }

                    case 4 -> {
                        running = false;
                        System.out.println("Fin du programme.");
                    }
                    default ->  throw new IllegalArgumentException("Veuillez entrer un entier présent dans le menu.");
                    }
                }
        }catch (IllegalArgumentException e){
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
