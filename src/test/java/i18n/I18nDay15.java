package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.ZonedDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay15 extends Commun {
    private static final DateTimeFormatter HOLIDAY_FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(3030, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(38550, traitement(inputs));
    }

    public int traitement(List<String> inputs) {
        int resultat;
        String[] sections = String.join("\n", inputs).split("\n\n");
        String[] offices = sections[0].split("\n");
        String[] customers = sections[1].split("\n");

        List<Supporter> supporters = new ArrayList<>();
        for (String office : offices) {
            String[] split = office.split("\t");
            ZoneId supporterTz = ZoneId.of(split[1]);
            Supporter supporter = new Supporter(supporterTz, List.of((split[2].split(";"))));
            supporters.add(supporter);
        }

        List<Customer> customersList = new ArrayList<>();
        for (String string : customers) {
            String[] parts = string.split("\t");
            ZoneId customerTz = ZoneId.of(parts[1]);
            Customer customer = new Customer(customerTz, List.of((parts[2].split(";"))));
            customersList.add(customer);
        }

        AtomicInteger minimum = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger maximum = new AtomicInteger();

        customersList.parallelStream().forEach(customer -> {
            ZoneId customerTz = customer.timezone;
            List<String> customerHolidays = customer.holidays;

            int overtime = 0;
            ZonedDateTime currentTime = of(2022, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
                    .withZoneSameInstant(customerTz);
            ZonedDateTime end = of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
                    .withZoneSameInstant(customerTz);

            while (currentTime.isBefore(end)) {
                String formattedDate = currentTime.format(HOLIDAY_FORMAT);
                if (isWeekday(currentTime) && isNotHoliday(customerHolidays, formattedDate)) {
                    boolean noSupportersAvailable = true;
                    for (Supporter s : supporters) {
                        if (s.worksAt(currentTime)) {
                            noSupportersAvailable = false;
                            break;
                        }
                    }
                    if (noSupportersAvailable) {
                        overtime += 30;
                    }
                }
                currentTime = currentTime.plusMinutes(30);
            }

            synchronized (this) {
                minimum.set(Math.min(minimum.get(), overtime));
                maximum.set(Math.max(maximum.get(), overtime));
            }
        });

        resultat = (maximum.get() - minimum.get());
        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    private boolean isNotHoliday(List<String> formattedHolidays, String formattedDate) {
        return !formattedHolidays.contains(formattedDate);
    }

    private boolean isWeekday(ZonedDateTime currentTime) {
        return currentTime.getDayOfWeek().getValue() < 6;
    }

    static class Customer {
        private final ZoneId timezone;
        private final List<String> holidays;

        public Customer(ZoneId customerTz, List<String> customerHolidays) {
            this.timezone = customerTz;
            this.holidays = customerHolidays;
        }
    }

    class Supporter {
        private final ZoneId timezone;
        private final List<String> holidays;

        public Supporter(ZoneId timezone, List<String> holidays) {
            this.timezone = timezone;
            this.holidays = holidays;
        }

        public boolean worksAt(ZonedDateTime now) {
            ZonedDateTime localNow = now.withZoneSameInstant(timezone);
            String formattedDate = localNow.format(HOLIDAY_FORMAT);
            return isWeekday(localNow) && ((localNow.getHour() == 8 && localNow.getMinute() >= 30)
                    || (localNow.getHour() >= 9 && localNow.getHour() < 17)) && isNotHoliday(holidays, formattedDate);
        }
    }

}