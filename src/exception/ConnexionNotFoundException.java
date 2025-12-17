package exception;


/**
 *  Cette classe ({@code ConnexionNotFoundException}) désigne les erreurs pouvant être provoquées lors de l'ajout, la modification ou de la suppression d'une {@link model.Connexion}. (Exemple: connexion innexistante, erreur dans un des éléments de la connexion)
 */
public class ConnexionNotFoundException extends Exception {
    /**
     * 
     * @param message le message envoyé
     */
    public ConnexionNotFoundException(String message){
        super(message);
    }
}