package i18n;

import i18n.utils.Grid;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay05 extends Commun {


    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(2, traitement(inputs, true));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(74, traitement(inputs, true));
    }

    public int traitement(List<String> inputs, boolean etape1) {

        int resultat = 0;
        List<List<String>> input = inputs.stream().map(this::decoupe).toList();
        Grid<String> parc = new Grid<>(input);

        int positionLigne = 0;
        int positionColonne = 0;
        for (int i = 0; i < parc.getHeight(); i++) {
            if (parc.get(positionLigne, positionColonne).equals("\uD83D\uDCA9")) {
                resultat++;
            }
            positionLigne += 1;
            positionColonne = (positionColonne + 2) % parc.getWidth();
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);

        return resultat;
    }

    //https://stackoverflow.com/questions/59311482/splitting-a-string-that-contains-emojis
    private List<String> decoupe(String s) {
        return Pattern.compile("\\P{M}\\p{M}*+").matcher(s)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}
