import model.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Reseau reseau = new Reseau();
        boolean running = true;

        while (running) {
            System.out.println("\nMenu principal :");
            System.out.println("1) Ajouter un générateur");
            System.out.println("2) Ajouter une maison");
            System.out.println("3) Ajouter une connexion");
            System.out.println("4) Fin");
            System.out.print("Choix : ");

            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.print("Nom et capacité (ex: G1 60) : ");
                    String nom = sc.next();
                    int cap = sc.nextInt();
                    reseau.ajouterGenerateur(nom, cap);
                }
                case 2 -> {
                    System.out.print("Nom et type (ex: M1 NORMALE) : ");
                    String nom = sc.next();
                    String type = sc.next();
                    reseau.ajouterMaison(nom, type);
                }
                case 3 -> {
                    System.out.print("Connexion (ex: M1 G1) : ");
                    String a = sc.next();
                    String b = sc.next();
                    if (a.startsWith("M")) reseau.ajouterConnexion(a, b);
                    else reseau.ajouterConnexion(b, a);
                }
                case 4 -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }

        reseau.afficherReseau();
        reseau.calculerCout();
        sc.close();
    }
}
