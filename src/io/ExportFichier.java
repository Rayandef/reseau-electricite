package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.Connexion;
import model.Generateur;
import model.Maison;
import model.Reseau;

public class ExportFichier {

    public void export(Reseau reseau, String chemin) throws IOException{
        try(BufferedWriter  bufferedWriter = new BufferedWriter(new FileWriter(chemin))) {
            for(Generateur g : reseau.getGenerateurs()){
                try {
                    bufferedWriter.write("generateur("+g.getNom()+","+g.getCapaciteMax()+").");
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    throw new IOException ("Erreur lors de l'écriture des générateurs dans le fichier : " + chemin);
                }
            }
            for(Maison m : reseau.getMaisons()){
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
                    throw new IOException("Erreur lors de l'écriture des maisons dans le fichier : " + chemin);
                }
            }
            for(Connexion c: reseau.getConnexions()){
                try {
                    bufferedWriter.write("connexion("+c.getMaison().getNom()+","+c.getGenerateur().getNom()+").");
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    throw new IOException("Erreur lors de l'écriture des connexions dans le fichier : " + chemin);
                }
            }
            bufferedWriter.close();
           
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation du réseau vers le fichier : " + chemin);
            e.printStackTrace();
        }
        
        
    }
}
