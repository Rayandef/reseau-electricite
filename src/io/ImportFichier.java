package io;
import exception.*;
import java.io.*;
import model.*;

public class ImportFichier {
    
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
    }
}
