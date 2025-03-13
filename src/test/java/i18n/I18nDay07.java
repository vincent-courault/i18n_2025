package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay07 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(866, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(32152346, traitement(inputs));
    }

    public int traitement(List<String> inputs) {
        int resultat = 0;
        List<ZonedDateTime> datesCorrigees = new ArrayList<>();
        for (String input : inputs) {
            String[] parts = input.split("\\t");
            ZonedDateTime dateZ = convertitEnZonedDate(parts[0]);
            datesCorrigees.add(dateZ.plusMinutes(Long.parseLong(parts[1])).minusMinutes(Long.parseLong(parts[2])));
        }
        for (int i = 0; i < datesCorrigees.size(); i++) {
            resultat += (i + 1) * datesCorrigees.get(i).getHour();
        }
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    public ZonedDateTime convertitEnZonedDate(String date) {
        OffsetDateTime offsetDate = OffsetDateTime.parse(date);
        ZoneId zoneHalifax = ZoneId.of("America/Halifax");
        ZoneId zoneSantiago = ZoneId.of("America/Santiago");

        if (verifieSiLaZoneCorrespondALaDate(offsetDate, zoneHalifax)) {
            return ZonedDateTime.parse(date).withZoneSameInstant(zoneHalifax);
        } else {
            return ZonedDateTime.parse(date).withZoneSameInstant(zoneSantiago);
        }
    }

    public boolean verifieSiLaZoneCorrespondALaDate(OffsetDateTime offsetDate, ZoneId zone) {
        // Convertir l'OffsetDateTime en ZonedDateTime pour la zone testée
        ZonedDateTime zonedDate = offsetDate.atZoneSameInstant(zone);
        // Obtenir l'offset officiel pour cette zone
        ZoneOffset offsetDeLaZone = zonedDate.getOffset();
        return offsetDeLaZone.equals(offsetDate.getOffset());
    }
}