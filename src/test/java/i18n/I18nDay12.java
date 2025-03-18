package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay12 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(1885816494308838L, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(2832324762572800L, traitement(inputs));
    }

    public long traitement(List<String> inputs) {
        long resultat = 1L;

        //English
        Collator collator = Collator.getInstance(Locale.ENGLISH);
        inputs.sort(Comparator.comparing(this::normalize, collator));
        resultat *= Integer.parseInt(inputs.get((inputs.size() / 2)).split(": ")[1]);

        //Swedish
        Locale swedishLocale = Locale.of("sv", "SE");  // Suédois
        collator = Collator.getInstance(swedishLocale);
        inputs.sort(Comparator.comparing(this::normalizeSwedish, collator));
        resultat *= Integer.parseInt(inputs.get((inputs.size() / 2)).split(": ")[1]);

        //Dutch
        Locale dutchLocale = Locale.of("nl", "NL");    // Néerlandais
        collator = Collator.getInstance(dutchLocale);
        inputs.sort(Comparator.comparing(this::normalizeDutch, collator));
        resultat *= Integer.parseInt(inputs.get((inputs.size() / 2)).split(": ")[1]);

        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    private String normalize(String name) {
        // Supprimer tout sauf les lettres (espaces et ponctuation disparaissent sauf ",")
        String clean = name.replaceAll("[^\\p{L}|,]", "");
        // Le caractère Ø n'est pas un caractère accentué à proprement parlé donc on le remplace par O.
        return clean
                .replace("Ø", "O");
    }

    private String normalizeSwedish(String name) {
        String clean = name.replaceAll("[^\\p{L}|,]", "");
        String fixed = clean
                .replace("Æ", "Ä")
                .replace("Ø", "Ö");
        return fixed.toLowerCase();
    }

    private String normalizeDutch(String name) {
        return normalize(name.replace("van ", "")
                .replace("der ", "")
                .replace("den ", "")
                .replace("de ", ""));
    }
}