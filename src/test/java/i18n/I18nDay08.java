package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay08 extends Commun {

    private static final Pattern PATTERN_CHIFFRE = Pattern.compile("\\d");
    private static final Pattern PATTERN_VOYELLE = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_CONSONNE = Pattern.compile("[bcdfghjklmnpqrstvwxyz]", Pattern.CASE_INSENSITIVE);

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(2, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(809, traitement(inputs));
    }

    public long traitement(List<String> inputs) {
        long resultat = inputs.stream().filter(this::verifieSiLeMotDePasseEstValide).count();
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    public boolean verifieSiLeMotDePasseEstValide(String password) {

        password = Normalizer.normalize(password, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        if (password.length() < 4 || password.length() > 12) {
            return false;
        }
        if (!PATTERN_CHIFFRE.matcher(password).find()) {
            return false;
        }
        if (!PATTERN_VOYELLE.matcher(password).find()) {
            return false;
        }
        if (!PATTERN_CONSONNE.matcher(password).find()) {
            return false;
        }
        return verifieLAbsenceDeLettresDupliquees(password);
    }

    private boolean verifieLAbsenceDeLettresDupliquees(String password) {
        HashSet<Character> lettres = new HashSet<>();

        for (char c : password.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                if (!lettres.add(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}