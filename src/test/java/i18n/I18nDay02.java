package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay02 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals("2019-06-05T12:15:00+00:00", traitementEtape1(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals("2020-10-25T01:30:00+00:00", traitementEtape1(inputs));
    }

    public String traitementEtape1(List<String> datesStr) {
        String resultat = datesStr.stream().map(OffsetDateTime::parse).
                map(date -> date.atZoneSameInstant(ZoneId.of("UTC"))
                        .toLocalDateTime())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).orElseThrow().getKey().toString()+":00+00:00";
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }
}
