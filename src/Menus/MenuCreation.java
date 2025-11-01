package Menus;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Maison;
import model.Reseau;

public class MenuCreation {
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

        while (running) {
            System.out.println("Menu principal :");
            System.out.println("1) Ajouter un générateur");
            System.out.println("2) Ajouter une maison");
            System.out.println("3) Ajouter une connexion");
            System.out.println("4) Passer au recapitulatif");
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
                    try{
                        int cap = sc.nextInt();
                        reseau.ajouterGenerateur(nom, cap);
                    }catch(InputMismatchException e){ //Si l'utilisateur n'entre un entier
                        System.err.println("Erreur: Entier non detecte. Veuillez entrer un entier");
                    }catch(IllegalArgumentException e){ //Si la capacité du générateur est négatif
                        System.err.println(e.getMessage()+ " Veuillez recommencer");
                    }finally{ //Nettoie le scanner
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
                        System.err.println("Erreur lors de l'ajout de la maison : " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.print("Connexion (ex: M1 G1) : ");
                    String a = sc.next();
                    String b = sc.next();
                    try{
                        if (a.startsWith("M")){
                            reseau.ajouterConnexion(a, b);
                        }else{
                            reseau.ajouterConnexion(b, a);
                        }
                    }catch(IllegalArgumentException e){
                        System.err.println("Erreur : impossible de créer la connexion (" + a + ", " + b + ")" + e.getMessage());
                    }
                }
                case 4 -> {
                    for (Maison m : reseau.getMaisons().values()) {
                        if (m.getConnected() == 0) {
                            System.out.println("La maison " + m.getNom() + " n'est connectée à aucun générateur !");
                        }else if (m.getConnected() > 1) {
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
