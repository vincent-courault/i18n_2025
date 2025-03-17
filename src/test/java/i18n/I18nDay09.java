package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.util.*;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay09 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals("Margot Peter", traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals("Amelia Amoura Hugo Jack Jakob Junior Mateo", traitement(inputs));
    }

    public String traitement(List<String> inputs) {
        String resultat;

        Map<String, List<String>> personneDates = new HashMap<>();

        for (String input : inputs) {
            String[] parts = input.split(": ");
            String date = parts[0];
            String[] personnes = parts[1].split(", ");

            for (String personne : personnes) {
                personneDates.putIfAbsent(personne, new ArrayList<>());
                personneDates.get(personne).add(date);
            }
        }
        // on détermine le format de date pour chaque personne
        Map<String, String> personneDateFormat = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : personneDates.entrySet()) {
            String personne = entry.getKey();
            List<String> dates = entry.getValue();
            personneDateFormat.put(personne, determineDateFormat(dates));
        }
        // Vérifie qui a un événement le 11 septembre 2001
        List<String> personneAvec11Septembre = new ArrayList<>();
        for (String personne : personneDates.keySet()) {
            for (String date : personneDates.get(personne)) {
                String format = personneDateFormat.get(personne);
                if (format != null && verifieEvenementLe11Septembre(date, format)) {
                    personneAvec11Septembre.add(personne);
                }
            }
        }

        Collections.sort(personneAvec11Septembre);
        resultat = String.join(" ", personneAvec11Septembre);
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }


    private boolean estUnFormatValide(int a, int b, int c, String format) {
        return switch (format) {
            case "YMD" -> {
                try {
                    of(a, b, c);
                } catch (DateTimeException e) {
                    yield false;
                }
                yield true;
            }
            case "YDM" -> {
                try {
                    of(a, c, b);
                } catch (DateTimeException e) {
                    yield false;
                }
                yield true;
            }
            case "DMY" -> {
                try {
                    of(c, b, a);
                } catch (DateTimeException e) {
                    yield false;
                }
                yield true;
            }
            case "MDY" -> {
                try {
                    of(c, a, b);
                } catch (DateTimeException e) {
                    yield false;
                }
                yield true;
            }
            default -> false;
        };
    }

    private String determineDateFormat(List<String> dates) {
        List<String> formatsPossibles = new ArrayList<>(Arrays.asList("YMD", "YDM", "DMY", "MDY"));
        for (String date : dates) {
            String[] parts = date.split("-");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);

            // Éliminer les formats incohérents
            formatsPossibles.removeIf(format -> !estUnFormatValide(a, b, c, format));

            // Si on a trouvé un seul format, pas besoin de continuer
            if (formatsPossibles.size() == 1) {
                break;
            }
        }
        return formatsPossibles.getFirst();
    }

    // Vérifie si une date donnée correspond au 11 septembre 2001
    private boolean verifieEvenementLe11Septembre(String date, String format) {
        String[] parts = date.split("-");
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);
        int c = Integer.parseInt(parts[2]);

        int day = 11, month = 9, year = 1;

        return switch (format) {
            case "YMD" -> (a == year && b == month && c == day);
            case "YDM" -> (a == year && c == month && b == day);
            case "DMY" -> (a == day && b == month && c == year);
            case "MDY" -> (b == day && a == month && c == year);
            default -> false;
        };
    }
}