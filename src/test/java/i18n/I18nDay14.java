package i18n;

import com.ibm.icu.text.RuleBasedNumberFormat;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay14 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException, ParseException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(2177741195L, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException, ParseException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(130675442686L, traitement(inputs));
    }

    public long traitement(List<String> inputs) throws ParseException {
        long resultat = 0;
        for (String input : inputs) {
            String[] parts = input.split(" × ");
            double valeur = litLaValeurEnShaku(parts[0]);
            double valeur2 = litLaValeurEnShaku(parts[1]);
            resultat += Math.round(valeur * valeur2 * 10 / 33 * 10 / 33);
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }


    private double litLaValeurEnShaku(String parts) throws ParseException {
        char unite;
        double valeurNumerique = parseJapaneseNumber(parts).doubleValue();
        unite = parts.charAt(parts.length() - 1);
        valeurNumerique = convertitEnShaku(valeurNumerique, unite);
        return valeurNumerique;
    }

    public Number parseJapaneseNumber(String kanjiNumber) throws ParseException {
        RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(Locale.JAPANESE, RuleBasedNumberFormat.SPELLOUT);
        return rbnf.parse(kanjiNumber);
    }

    private double convertitEnShaku(double valeur, char unite) {
        switch (unite) {
            case '間' -> {
                return valeur * 6;
            }
            case '丈' -> {
                return valeur * 10;
            }
            case '町' -> {
                return valeur * 360;
            }
            case '里' -> {
                return valeur * 12960;
            }
            case '尺' -> {
                return valeur;
            }
            case '寸' -> {
                return valeur / 10;
            }
            case '分' -> {
                return valeur / 100;
            }
            case '厘' -> {
                return valeur / 1000;
            }
            case '毛' -> {
                return valeur / 10000;
            }
            default -> throw new RuntimeException("unité inconnue");
        }
    }

}