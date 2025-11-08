package menus;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Maison;
import model.Reseau;

public class MenuGestion {
    //Crée et gère l'interface de départ
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

        try{
            while (running) {
                //Génère l'interface dans le terminal
                System.out.println("Menu principal :");
                System.out.println("1) Ajouter un générateur");
                System.out.println("2) Ajouter une maison");
                System.out.println("3) Ajouter une connexion");
                System.out.println("4) Supprimer une connexion");
                System.out.println("5) Passer au recapitulatif");
                System.out.print("Choix : ");

                int choix;
                //On vérifie que choix est bien un entier
                if (sc.hasNextInt()) {
                    choix = sc.nextInt();
                    sc.nextLine(); 
                } else {
                    System.out.println("Veuillez entrer un nombre entier valide");
                    sc.nextLine();
                    continue;
                }

                switch (choix) {
                    case 1 -> { //Ajout de générateur
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
                    case 2 -> { //Ajout de maison
                        System.out.print("Nom et type (ex: M1 NORMALE) : ");
                        System.out.println("(Types possibles : BASSE, NORMALE, FORTE)");
                        String nom = sc.next();
                        String type = sc.next();
                        try { //On essaye d'ajouter la maison
                            reseau.ajouterMaison(nom, type);
                        } catch (IllegalArgumentException e) { //Si l'utilisateur a entré autre chose que les types BASSE, NORMALE, FORTE
                            System.err.println("Erreur lors de l'ajout de la maison : " + e.getMessage());
                        }finally{ //On nettoie le scanner
                            sc.nextLine();
                        }
                    }
                    case 3 -> { //Ajout d'une connexion
                        System.out.print("Connexion (ex: M1 G1) : ");
                        String a = sc.next();
                        String b = sc.next();
                        try{
                            if (a.startsWith("M")){
                                reseau.ajouterConnexion(a, b);
                            }else{
                                reseau.ajouterConnexion(b, a);
                            }
                        }catch(IllegalArgumentException e){ //Si la maison ou le générateur n'existe pas
                            System.err.println("Erreur : impossible de créer la connexion (" + a + ", " + b + ")" + e.getMessage());
                        }finally{
                            sc.nextLine();
                        }
                    }
                    case 4 -> { //Suppression de connexion
                        System.out.print("Connexion à supprimer (ex: M1 G1) : ");
                        String a = sc.next();
                        String b = sc.next();
                        try{
                            reseau.supprimerConnexion(a,b);
                        }catch(IllegalArgumentException e){  //Si la connexion n'existe pas
                            System.err.println("Erreur : impossible de supprimer la connexion (" + a + ", " + b + ")" + e.getMessage());
                        }finally{
                            sc.nextLine();
                        }
                    }
                    case 5 -> {
                        /*On vérifie qu'il y ait bien des maisons, des générateurs et au moins une connection entre un générateur et 
                        une maison avant de passer au menu suivant.*/
                        try{
                            if (reseau.getGenerateurs().isEmpty() && reseau.getMaisons().isEmpty()){
                                throw new IllegalStateException("L'ajout de generateur et de maison est obligatoire");
                            }
                            if(reseau.getMaisons().isEmpty()){
                                throw new IllegalStateException("L'ajout de maison est obligatoire");
                            }
                            if (reseau.getGenerateurs().isEmpty()){
                                throw new IllegalStateException("L'ajout de generateur est obligatoire");
                            }
                            for (Maison m : reseau.getMaisons().values()) {
                                if (m.getConnected() == 0) {
                                    System.out.println("La maison " + m.getNom() + " n'est connectée à aucun générateur !");
                                }else if (m.getConnected() > 1) {
                                    System.out.println("La maison " + m.getNom() + " est connectée à plusieurs générateurs (" + m.getConnected() + ") !");
                                }
                            }
                            running = false;
                        }catch(IllegalStateException e){
                            System.err.println("Erreur: " + e.getMessage());
                        }
                    }
                    //Si l'utilisateur a entré autre chose que les entiers 1 2 3 4 5
                    default -> throw new IllegalArgumentException("Veuillez entrer un entier présent dans le menu.");
                }
            }
        }catch(IllegalArgumentException e){
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
