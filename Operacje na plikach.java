import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileTemplates {

    // Szablon 1: Czytanie pliku linia po linii (dobre dla dużych plików)
    public static void readFileLineByLine(String filePath) {
        Path path = Paths.get(filePath);
        
        // try-with-resources automatycznie zamknie BufferedReader
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            // Pętla wykonuje się, dopóki są linie do przeczytania
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // Przechwytywanie błędu (np. gdy plik nie istnieje)
            System.err.println("Wystąpił błąd podczas czytania pliku: " + e.getMessage());
        }
    }

    // Szablon 2: Wczytanie całego pliku do listy (tylko dla małych plików)
    public static List<String> readAllLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Błąd odczytu: " + e.getMessage());
            return List.of(); // Zwraca pustą listę w przypadku błędu
        }
    }
}
