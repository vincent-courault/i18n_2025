package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay04 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(3143, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(16451, traitement(inputs));
    }

    public long traitement(List<String> inputs) {
        long resultat = 0;
        Duration duree = Duration.ofDays(0);
        for (int i = 0; i < inputs.size(); i += 3) {
            String depart = inputs.get(i);
            String arrivee = inputs.get(i + 1);
            duree = duree.plus(Duration.between(parseDate(depart), parseDate(arrivee)));
        }
        resultat = duree.toMinutes();
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    public static ZonedDateTime parseDate(String dateStr) {
        String zoneIdStr = dateStr.split(": ")[1].trim().substring(0, 31).trim();
        String datePart = dateStr.split(": ")[1].trim().substring(31).trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(datePart, formatter);
        ZoneId zoneId = ZoneId.of(zoneIdStr);
        return ZonedDateTime.of(localDateTime, zoneId);
    }
}
