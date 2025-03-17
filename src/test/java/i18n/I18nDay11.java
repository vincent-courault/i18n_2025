package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay11 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(19, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(213, traitement(inputs));
    }
    static final String alphabet = "αβγδεζηθικλμνξοπρστυφχψω";
    static final String motCible = "Οδυσσε";

    public int traitement(List<String> inputs) {
        int resultat = 0;
        for (String input:inputs){
            for (int decalage = 1; decalage < alphabet.length(); decalage++) {
                String texteDechiffre = appliqueLeDecalageSurLInput(input, decalage);
                if (texteDechiffre.contains(motCible)) {
                    resultat+=decalage;
                    break;
                }
            }
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    public static String appliqueLeDecalageSurLInput(String texte, int decalage) {
        StringBuilder resultat = new StringBuilder();
        for (char c : texte.toCharArray()) {
            int index = alphabet.indexOf(Character.toLowerCase(c));

            if (index != -1) {
                int nouvelIndex = (index + decalage) % alphabet.length();
                char nouveauCaractere = alphabet.charAt(nouvelIndex);
                if (Character.isUpperCase(c)) { //on remet en majuscule si le caractère initial était en majuscule
                    nouveauCaractere = Character.toUpperCase(nouveauCaractere);
                }
                resultat.append(nouveauCaractere);
            } else {
                resultat.append(c);
            }
        }
        return resultat.toString();
    }
}