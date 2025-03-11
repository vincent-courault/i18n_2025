package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay01 extends Commun {

    private static boolean isTwit(int charLength) {
        return charLength <= 140;
    }

    private static boolean isSMS(int byteLength) {
        return byteLength <= 160;
    }

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(31, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(107989, traitement(inputs));
    }

    public int traitement(List<String> inputs) {
        int resultat = 0;

        for (String input : inputs) {
            int byteLength = input.getBytes().length;
            int charLength = input.length();
            if (isSMS(byteLength) && isTwit(charLength)) {
                resultat += 13;
            } else {
                if (isSMS(byteLength)) {
                    resultat += 11;
                } else {
                    if (isTwit(charLength)) {
                        resultat += 7;
                    }
                }
            }
        }

        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

}
