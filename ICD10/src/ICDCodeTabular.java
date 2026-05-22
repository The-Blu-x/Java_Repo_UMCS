import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

// Interfejs zdefiniowany w zadaniu
public interface ICDCodeTabular {
    String getDescription(String code) throws IndexOutOfBoundsException;
}

// Implementacja zoptymalizowana pod kątem CZASU - z użyciem pamięci operacyjnej
class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    private final Map<String, String> cache = new HashMap<>();

    public ICDCodeTabularOptimizedForTime(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.skip(87).forEach(line -> {
                String trimmedLine = line.trim();
                // Walidujemy, czy linia faktycznie zawiera kod (litera + dwie cyfry na początku)
                if (trimmedLine.matches("^[A-Z][0-9]{2}.*")) {
                    String[] parts = trimmedLine.split("\\s+", 2); // rozdzielamy przy pierwszym ciągu spacji
                    if (parts.length == 2) {
                        cache.put(parts[0], parts[1]);
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas ładowania bazy ICD-10: " + e.getMessage());
        }
    }

    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        if (!cache.containsKey(code)) {
            throw new IndexOutOfBoundsException("Nie odnaleziono opisu dla kodu: " + code);
        }
        return cache.get(code);
    }
}

// Implementacja zoptymalizowana pod kątem PAMIĘCI - wyszukiwanie na żywo w pliku tekstowym
class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    private final String filePath;

    public ICDCodeTabularOptimizedForMemory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            Optional<String> result = lines.skip(87)
                    .map(String::trim)
                    // Szukamy linii rozpoczynającej się od szukanego kodu oraz spacji
                    .filter(line -> line.startsWith(code + " "))
                    .findFirst();

            if (result.isPresent()) {
                String line = result.get();
                // Oddzielamy kod od opisu
                return line.substring(code.length()).trim();
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas odczytu pliku ICD-10: " + e.getMessage());
        }

        throw new IndexOutOfBoundsException("Nie odnaleziono opisu dla kodu: " + code);
    }
}