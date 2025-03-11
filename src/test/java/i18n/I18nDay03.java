package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay03 extends Commun {


    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(2, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(509, traitement(inputs));
    }

    public int traitement(List<String> passwords) {
        int resultat = 0;
        for (String password : passwords) {
            if (password.length() >= 4 && password.length() <= 12) {
                boolean hasUpperCase = false;
                boolean hasLowerCase = false;
                boolean hasDigit = false;
                boolean hasOutside7Bits = false;
                for (Character caractere : password.toCharArray()) {
                    if (Character.isUpperCase(caractere)) {
                        hasUpperCase = true;
                    }
                    if (Character.isLowerCase(caractere)) {
                        hasLowerCase = true;
                    }
                    if (Character.isDigit(caractere)) {
                        hasDigit = true;
                    }
                    if ((int) caractere > 127) {
                        hasOutside7Bits = true;
                    }
                }
                if (hasUpperCase && hasLowerCase && hasDigit && hasOutside7Bits) {
                    resultat++;
                }
            }
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }
}
