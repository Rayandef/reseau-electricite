package Menus;

import model.Reseau;
import java.util.Scanner;

public class Menu2 {
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

        while (running) {
            System.out.println("\nMenu 2 :");
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
                    System.out.println("Entrez la connexion à modifier (ex: M1 G1 ou G1 M1) : ");
                    String ancienA = sc.next();
                    String ancienB = sc.next();

                    System.out.println("Entrez la nouvelle connexion (ex: M2 G2 ou G2 M2) : ");
                    String nouveauA = sc.next();
                    String nouveauB = sc.next();

                    reseau.changerConnexion(ancienA, ancienB, nouveauA, nouveauB);
}

                case 3 -> {
                    reseau.afficherReseau();
                }

                case 4 -> {
                    running = false;
                    System.out.println("Quitter le menu 2.");
                }

                default -> {
                    System.out.println("Veuillez choisir une option valide.");
                }
            }
        }
    }
}