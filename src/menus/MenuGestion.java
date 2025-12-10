package menus;
import exception.*;
import io.ImportFichier;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Maison;
import model.Reseau;

/**
 * Cette classe ({@code MenuGestion}) permet de gérer les différents éléments du premier menu.  
 * <p>
 * Elle permet à l'utilisateur d'ajouter des générateurs, des maisons et de
 * créer ou supprimer des connexions entre maisons et générateurs.
 */
public class MenuGestion {
     /**
     * Affiche le menu principal et gère les actions saisies par l'utilisateur.     *
     * <p>Le menu propose les options suivantes :</p>
     * <ul>
     *     <li>1) Ajouter un générateur</li>
     *     <li>2) Ajouter une maison</li>
     *     <li>3) Ajouter une connexion</li>
     *     <li>4) Supprimer une connexion</li>
     *     <li>5) Passer au récapitulatif</li>
     * </ul>
     *
     * <p>
     * Des vérifications de validité sont effectuées sur les entrées utilisateur
     * ainsi que sur l'état du réseau, avec gestion d'exceptions adaptées :
     * <ul>
     *     <li>{@link InputMismatchException} si un entier est attendu lors du choix de l'action de l'utilisateur</li>
     *     <li>{@link IllegalArgumentException} si les paramètres fournis ne sont pas valides</li>
     *     <li>{@link ComposantException} s'il l'utilisateur entre des données incorrects pour un un générateur ou une maison</li>
     *     <li>{@link ComposantException} si le réseau n'est pas correctement configuré avant validation</li>
     * </ul>
     *
     * @param sc     le scanner utilisé pour lire les entrées utilisateur
     * @param reseau le réseau dans lequel les éléments sont ajoutés et modifiés
     */
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;

       
            while (running) {
                try{
                //Génère l'interface dans le terminal
                System.out.println("Menu principal :");
                System.out.println("1) Ajouter un générateur");
                System.out.println("2) Ajouter une maison");
                System.out.println("3) Ajouter une connexion");
                System.out.println("4) Supprimer une connexion");
                System.out.println("5) Passer au recapitulatif");
                System.out.println("6: Créer le réseau depuis un fichier txt");
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
                        }catch(ComposantException e){ //Si la capacité du générateur est négatif
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
                        } catch (ComposantException e) { //Si l'utilisateur a entré autre chose que les types BASSE, NORMALE, FORTE
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
                        }catch(ConnexionNotFoundException e){ //Si la maison ou le générateur n'existe pas
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
                        }catch(ConnexionNotFoundException e){  //Si la connexion n'existe pas
                            System.err.println("Erreur : impossible de supprimer la connexion (" + a + ", " + b + ")" + e.getMessage());
                        }finally{
                            sc.nextLine();
                        }
                    }
                    case 5 -> {
                        /*Permet à l'utilisateur de créer un réseau depuis un fichier texte s'il ne l'a pas fait en lançant le programme */
                        System.out.println("Veuillez entrée le chemin vers le fichier txt");
                        String fich =sc.nextLine();
                        ImportFichier loader = new ImportFichier();
                        try {
                            loader.creationReseau(fich, reseau);   
                        } catch (ComposantException | ConnexionNotFoundException e) {
                            System.err.println("Erreur lors de la création du réseau depuis le fichier : " + e.getMessage());
                        }
                    }
                    case 6-> {
                        /*On vérifie qu'il y ait bien des maisons, des générateurs et au moins une connection entre un générateur et 
                        une maison avant de passer au menu suivant.*/
                        try{
                            if (reseau.getGenerateurs().isEmpty() && reseau.getMaisons().isEmpty()){
                                throw new ComposantException("L'ajout de generateur et de maison est obligatoire");
                            }
                            if(reseau.getMaisons().isEmpty()){
                                throw new ComposantException("L'ajout de maison est obligatoire");
                            }
                            if (reseau.getGenerateurs().isEmpty()){
                                throw new ComposantException("L'ajout de generateur est obligatoire");
                            }
                            for (Maison m : reseau.getMaisons()) {
                                if (m.getConnected() == 0) {
                                    throw new ComposantException("La maison " + m.getNom() + " n'est connectée à aucun générateur !");
                                }else if (m.getConnected() > 1) {
                                    throw new ComposantException("La maison " + m.getNom() + " est connectée à plusieurs générateurs (" + m.getConnected() + ") !");
                                }
                            }
                            running = false;
                        }catch(ComposantException e){
                            System.err.println("Erreur: " + e.getMessage());
                        }
                    }
                    //Si l'utilisateur a entré autre chose que les entiers 1 2 3 4 5
                    default -> throw new IllegalArgumentException("Veuillez entrer un entier présent dans le menu.");
                }
                }catch(IllegalArgumentException e){
                    System.err.println("Erreur: " + e.getMessage());
                }
            }
    }
}
