import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeathCauseStatisticList {
    // Lista przetrzymująca informacje o wszystkich obiektach
    private List<DeathCauseStatistic> statistics = new ArrayList<>();

    // Metoda czyszcząca i ponownie zapełniająca strukturę danych
    public void repopulate(String path) {
        statistics.clear();

        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            statistics = lines
                    // Sprawdzenie poprawności kodu ICD-10: zaczyna się od jednej litery [A-Z] i dwóch cyfr [0-9]{2}
                    .filter(line -> line.trim().matches("^[A-Z][0-9]{2}.*"))
                    .map(DeathCauseStatistic::fromCsvLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas odczytu pliku: " + e.getMessage());
        }
    }

    // Metoda zwracająca n chorób najczęściej powodujących śmierć
    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n) {
        return statistics.stream()
                // Sortowanie malejące na podstawie ilości zgonów w odpowiedniej dla podanego wieku grupie
                .sorted((stat1, stat2) -> Integer.compare(
                        stat2.getAgeBracket(age).deathCount,
                        stat1.getAgeBracket(age).deathCount
                ))
                // Zwrócenie maksymalnie n elementów
                .limit(n)
                .collect(Collectors.toList());
    }
}