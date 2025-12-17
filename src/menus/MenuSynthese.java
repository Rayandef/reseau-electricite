package menus;

import exception.ConnexionNotFoundException;
import io.ExportFichier;
import java.io.*;
import java.util.Scanner;
import model.Reseau;
import utils.*;

/**
 * Cette classe ({@code MenuSynthese}) permet de gérer les éléments du second menu.
 * <p>
 * Elle permet à l'utilisateur de modifier des connexions, voir l'état du réseau et calcul le coût du réseau actuel.
 */
public class MenuSynthese {
     /**
     * Affiche le menu de synthèse et exécute les actions correspondant
     * aux choix de l'utilisateur.
     * <p>
     * Le menu propose les options suivantes :
     * <ul>
     *     <li>Calculer le coût du réseau : utilise la méthode
     *         {@link Reseau#calculerCout()} pour afficher le coût total.</li>
     *     <li>Modifier une connexion : permet à l'utilisateur
     *         de remplacer une connexion existante par une nouvelle via
     *         {@link Reseau#changerConnexion(String, String, String, String)}.</li>
     *     <li>Afficher le réseau : visualisation du réseau
     *         grâce à {@link Reseau#afficherReseau()}.</li>
     *     <li>Quitter : met fin au programme.</li>
     * </ul>
     *
     * <p>
     * Cette méthode valide également les entrées utilisateur afin d'éviter les
     * saisies non numériques, et capture les exceptions courantes :
     * </p>
     * <ul>
     *     <li>{@link IllegalArgumentException} si les valeurs saisies ne
     *         correspondent pas à une option valide ou à une connexion existante</li>
     * </ul>
     *
     * @param sc     le scanner permettant de lire les entrées utilisateur
     * @param reseau l'objet {@link Reseau} sur lequel les opérations seront effectuées
     */

    //Affichage de la deuxième interface
    public static void afficherMenu(Scanner sc, Reseau reseau) {
        boolean running = true;
        
            while (running) {
                try{
                //Genere l'interface dans le terminal
                System.out.println("Menu 2 :");
                System.out.println("1) Optimiser le réseau");
                System.out.println("2) Exporter le réseau vers un fichier txt");
                System.out.println("3) Quitter");
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
                    case 1 -> { //Résolution automatique
                        AlgoBestConnexion A = new AlgoBestConnexion();
                        A.algoBestConnexion(reseau);
                        System.out.println("Le réseau a été modifié");
                        System.out.println("Nouveau réseau :");
                        reseau.afficherReseau();
                        System.out.println("Nouveau coût du réseau :");
                        reseau.calculerCout();
                    }

                    case 2 -> {
                        System.out.println("Veuillez entrer le chemin vers le fichier txt pour l'exportation");
                        String fichExport = sc.nextLine();
                        ExportFichier exporter = new ExportFichier();
                        try{
                            exporter.export(reseau, fichExport);
                        }catch (IOException e){
                            System.err.println("Erreur lors de l'exportation du réseau vers le fichier : " + e.getMessage());
                        }
                    }

                    case 3 -> { //Quitter le programme
                        running = false;
                        System.out.println("Fin du programme.");
                    }
                     //Si l'utilisateur a entré autre chose que 1 2 3 
                    default ->  throw new IllegalArgumentException("Veuillez entrer un entier présent dans le menu.");
                    }
                    
                    }catch (IllegalArgumentException e){
                        System.err.println("Erreur: " + e.getMessage());
                    }
                }
    }
}
