package i18n;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay10 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(4, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(1413, traitement(inputs));
    }

    public int traitement(List<String> inputs) {
        int resultat = 0;
        boolean finHashed = false;
        Map<String, String> passwordHashed = new HashMap<>();
        List<String> passwordInput = new ArrayList<>();
        for (String input : inputs) {
            if (!finHashed && !input.isEmpty()) {
                passwordHashed.put(input.split(" ")[0], input.split(" ")[1]);
            }
            if (input.isEmpty()) {
                finHashed = true;
            }
            if (finHashed && !input.isEmpty()) {
                passwordInput.add(input);
            }
        }

        Map<String, Boolean> memo = new HashMap<>();
        for (String s : passwordInput) {
            String[] input = s.split(" ");
            String inputNormalized = Normalizer.normalize(input[1], Normalizer.Form.NFC);
            List<String> variations = genereLesCombinaisons(inputNormalized);
            for (String variation : variations) {
                boolean test;
                if (memo.containsKey(variation + input[0])) {
                    test = memo.get(variation + input[0]);
                } else {
                    test = BCrypt.checkpw(variation, passwordHashed.get(input[0]));
                    memo.put(variation + input[0], test);
                }
                if (test) {
                    resultat++;
                    break;
                }
            }
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    // Génère toutes les formes Unicode possibles d'un caractère
    private List<String> determineLesVariantes(String str) {
        Set<String> variantes = new HashSet<>();
        // Ajouter les différentes formes Unicode
        variantes.add(str); // Forme originale
        variantes.add(Normalizer.normalize(str, Normalizer.Form.NFD)); // Forme décomposée
        return new ArrayList<>(variantes);
    }

    // Génère toutes les combinaisons possibles en remplaçant les caractères accentués par leurs variantes
    private List<String> genereLesCombinaisons(String input) {
        List<List<String>> charVariantes = new ArrayList<>();

        for (String car : input.split("")) {
            charVariantes.add(determineLesVariantes(car));
        }

        // Liste pour stocker toutes les combinaisons
        List<String> resultats = new ArrayList<>();
        creeLesVariations(charVariantes, 0, new StringBuilder(), resultats);
        return resultats;
    }

    private void creeLesVariations(List<List<String>> variantes, int index, StringBuilder courant, List<String> resultats) {
        if (index == variantes.size()) {
            resultats.add(courant.toString());
            return;
        }

        for (String variant : variantes.get(index)) {
            int longueurAvant = courant.length();
            courant.append(variant);
            creeLesVariations(variantes, index + 1, courant, resultats);
            courant.setLength(longueurAvant); // Annule le dernier ajout
        }
    }
}