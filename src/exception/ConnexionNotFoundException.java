package exception;


/**
 *  Cette classe ({@code ConnexionNotFoundException}) désigne les erreurs pouvant être provoquées lors de l'ajout, la modification ou de la suppression d'une connexion
 */
public class ConnexionNotFoundException extends Exception {
    public ConnexionNotFoundException(String message){
        super(message);
    }
}