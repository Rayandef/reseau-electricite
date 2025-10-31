import Menus.*;
import java.util.Scanner;
import model.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Reseau reseau = new Reseau();
        MenuCreation.afficherMenu(sc, reseau);
        reseau.afficherReseau();
        reseau.calculerCout();
        Menu2.afficherMenu(sc, reseau);
        sc.close();
    }
}
