import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeTemplates {

    public static void dateAndTime() {
        // 1. Pobranie dzisiejszej daty (bez czasu)
        LocalDate today = LocalDate.now();
        System.out.println("Dziś: " + today);

        // 2. Pobranie aktualnego czasu (godzina, minuta, sekunda)
        LocalTime now = LocalTime.now();
        System.out.println("Teraz jest: " + now);

        // 3. Pełna data i czas z formatowaniem
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Definiowanie własnego wzoru wyglądu daty
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatted = currentDateTime.format(formatter);
        System.out.println("Sformatowana data: " + formatted);

        // 4. Obliczanie różnicy między datami (np. ile dni minęło)
        LocalDate pastDate = LocalDate.of(2023, 1, 1);
        long daysBetween = ChronoUnit.DAYS.between(pastDate, today);
        System.out.println("Od 1 stycznia 2023 minęło: " + daysBetween + " dni");
        
        // 5. Dodawanie/odejmowanie czasu
        LocalDate nextWeek = today.plusDays(7);
        LocalDate lastMonth = today.minusMonths(1);
    }
}
