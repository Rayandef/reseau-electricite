package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.Generateur;
import model.Maison;
import model.Reseau;
import model.Connexion;

public class ExportFichier {
    public ExportFichier() {
    }

    public void export(Reseau reseau, String chemin){
        try {
            BufferedWriter  bufferedWriter = new BufferedWriter(new FileWriter(chemin)) ;
            for(Generateur g : reseau.getGenerateurs()){
                try {
                    

                    bufferedWriter.write("generateur("+g.getNom()+","+g.getCapaciteMax()+").");
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    System.out.println("Erreur lors de l'écriture des générateurs dans le fichier : " + chemin);
                    e.printStackTrace();
                }
            }
            for(Maison m : reseau.getMaisons().values()){
                try {
                    int capacite = m.getConsommation();
                    String capaString = null;
                    if(capacite == 10){
                        capaString = "BASSE";
                    } else if (capacite == 20) {
                        capaString = "NORMAL";
                    } else if (capacite == 40) {
                        capaString = "FORTE";
                    }
                    bufferedWriter.write("maison("+m.getNom()+","+capaString+").");
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    System.out.println("Erreur lors de l'écriture des maisons dans le fichier : " + chemin);
                    e.printStackTrace();
                }
            }
            for(Connexion c: reseau.getConnexions()){
                try {
                    bufferedWriter.write("connexion("+c.getMaison().getNom()+","+c.getGenerateur().getNom()+").");
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    System.out.println("Erreur lors de l'écriture des connexions dans le fichier : " + chemin);
                    e.printStackTrace();
                }
            }
            bufferedWriter.close();
           
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation du réseau vers le fichier : " + chemin);
            e.printStackTrace();
        }
        
        
    }
}
