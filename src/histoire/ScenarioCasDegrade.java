package histoire;

import villagegaulois.Etal;
import villagegaulois.Etal.EtalNonOccupeException;
import villagegaulois.*;
import personnages.*;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();
        
        try {
            etal.libererEtal();
            System.out.println("Libération de l'étal réussie.");
        } catch (EtalNonOccupeException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        try {
            etal.acheterProduit(5, null); // Acheteur null
        } catch (NullPointerException e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace(); // Afficher la pile d'erreurs sur la sortie d'erreur
        }
        
        System.out.println("Fin du test");
    }
}
