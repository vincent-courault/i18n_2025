package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay06 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(50, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(11252, traitement(inputs));
    }

    public int traitement(List<String> inputs) {
        int resultat = 0;
        List<String> mots = new ArrayList<>();
        List<String> puzzle = new ArrayList<>();
        boolean finMots = false;
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).isEmpty()) {
                finMots = true;
                i++;
            }
            if (!finMots) {
                String utf8EncodedString = inputs.get(i);
                if ((i + 1) % 3 == 0) { // modulo 3
                    utf8EncodedString = new String(utf8EncodedString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                }
                if ((i + 1) % 5 == 0) { // modulo 5
                    utf8EncodedString = new String(utf8EncodedString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                }
                mots.add(utf8EncodedString);
            } else {
                puzzle.add(inputs.get(i).trim());
            }
        }

        for (String s : puzzle) {
            for (int j = 0; j < mots.size(); j++) {
                if (mots.get(j).matches(s)) { //on vérifie si le mot matche le pattern.
                    resultat += j + 1; //on compte à partir de 1 et pas à partir de 0.
                    break;
                }
            }
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }
}