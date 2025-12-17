package io;
import exception.*;
import java.io.*;
import model.*;

/**
 * Cette classe ({@code ImportFichier}) permet d'importer un réseau stocké sous la forme d'un fichiet .txt.
 */
public class ImportFichier {
    /**
     * Méthode qui permet l'importation d'un fichier texte. Vérifie que le fichier texte est au bon format et qu'il existe. Ne créer que les éléments respectant le formatage ou les conditions de création
     * @param CheminWithTxt //chemin du fichier 
     * @param reseau le réseau qui va etre créer
     * @throws ComposantException si il y a un élément de création incorrect
     * @throws ConnexionNotFoundException si il y a une erreur dans les connexions
     * @throws NumberFormatException si il y a une erreur dans le formatage des nombres
     */
    public void creationReseau(String CheminWithTxt,Reseau reseau)throws  ComposantException, ConnexionNotFoundException, NumberFormatException {
        File fileImport = new File(CheminWithTxt);
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileImport))){
            String getData;
            while ((getData = fileReader.readLine()) != null) {
                        String checkData = getData.trim();
                        if (checkData.isEmpty()) continue;
                        int dataStart = checkData.indexOf("(");
                        int dataEnd = checkData.indexOf(")");
                        if (dataStart == -1 || dataEnd == -1) {
                            throw new ComposantException("Format incorrect (parenthèses manquantes) : " + getData);
                        }
                        String data = checkData.substring(dataStart + 1,dataEnd);
                        String dataList[] = data.split(",");
                        dataList[0] = dataList[0].trim();
                        dataList[1] = dataList[1].trim();
                    if (checkData.toLowerCase().contains("connexion")){
                        reseau.ajouterConnexion(dataList[0], dataList[1]);
                    }else if (checkData.toLowerCase().contains("maison")){
                        reseau.ajouterMaison(dataList[0], dataList[1]);
                    }else if (checkData.toLowerCase().contains("generateur")){
                        String genNom = dataList[0]; 
                        int genCapacite = Integer.parseInt(dataList[1]);
                        reseau.ajouterGenerateur(genNom,genCapacite);
                    }else{
                        throw new ComposantException("Erreur: " + getData + "n'est pas un générateur, une maison ou une connexion");
                    }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(ConnexionNotFoundException e){
            e.printStackTrace();
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
    }
}
