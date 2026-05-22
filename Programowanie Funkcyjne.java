import java.util.List;
import java.util.stream.Collectors;

public class FunctionalTemplates {
    
    public static void streamExamples() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Użycie strumienia (Stream) do filtrowania i transformacji danych
        List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)      // Zostaw tylko parzyste (LAMBDA)
            .map(n -> n * n)              // Podnieś do kwadratu
            .collect(Collectors.toList());// Zbierz z powrotem do nowej listy (w Java 16+ można użyć .toList())
            
        System.out.println(evenSquares); // Wynik: [4, 16, 36, 64, 100]
    }
}
