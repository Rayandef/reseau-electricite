package exception;

/**
*    Cette classe ({@code ComposantException}) désigne les erreurs pouvant être provoquées lors de la créeation d'une {@link model.Maison}, {@link model.Generateur} ou {@link model.Connexion}. Elle indique qu'un des éléments de création est incrorrect. (Exemple: le type de la maison, la charge maximale du générateur, etc...)
*/
public class ComposantException extends Exception {
    /**
     * 
     * @param message le message envoyé
     */
    public ComposantException(String message){
        super(message);
    }
}
