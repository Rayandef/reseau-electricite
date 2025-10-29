package Menus;
import model.Reseau;
import model.Maison;
import java.util.Scanner;

public class MenuCreation {
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

        while (running) {
            System.out.println("\nMenu principal :");
            System.out.println("1) Ajouter un générateur");
            System.out.println("2) Ajouter une maison");
            System.out.println("3) Ajouter une connexion");
            System.out.println("4) Fin");
            System.out.print("Choix : ");

            int choix;
            if (sc.hasNextInt()) {
                choix = sc.nextInt();
                sc.nextLine(); 
            } else {
                System.out.println("Veuillez entrer un nombre entier valide");
                sc.nextLine();
                continue;
            }

            switch (choix) {
                case 1 -> {
                    System.out.print("Nom et capacité (ex: G1 60) : ");
                    String nom = sc.next();
                    int cap;
                    if (sc.hasNextInt()) {
                        cap = sc.nextInt();
                        reseau.ajouterGenerateur(nom, cap);
                    } else {
                        System.out.println("Capacité invalide, opération annulée.");
                        sc.nextLine();
                    }
                }
                case 2 -> {
                    System.out.print("Nom et type (ex: M1 NORMALE) : ");
                    System.out.println("(Types possibles : BASSE, NORMALE, FORTE)");
                    String nom = sc.next();
                    String type = sc.next();
                    try {
                        reseau.ajouterMaison(nom, type);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erreur lors de l'ajout de la maison : " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.print("Connexion (ex: M1 G1) : ");
                    String a = sc.next();
                    String b = sc.next();
                    if (a.startsWith("M"))
                        reseau.ajouterConnexion(a, b);
                    else
                        reseau.ajouterConnexion(b, a);
                }
                case 4 -> {
                    for (Maison m : reseau.getMaisons().values()) {
                        if (m.getConnected() == 0) {
                            System.out.println("La maison " + m.getNom() + " n'est connectée à aucun générateur !");
                        } else if (m.getConnected() > 1) {
                            System.out.println("La maison " + m.getNom() + " est connectée à plusieurs générateurs (" + m.getConnected() + ") !");
                        }
                    }
                    running = false;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}
