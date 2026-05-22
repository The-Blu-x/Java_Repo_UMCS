import java.util.*;

public class ContainerTemplates {
    public static void collectionsExamples() {
        // LISTA: Pozwala na duplikaty, zachowuje kolejność dodawania
        List<String> names = new ArrayList<>();
        names.add("Anna");
        names.add("Tomasz");
        
        // ZBIÓR (SET): Brak duplikatów, zazwyczaj nie zachowuje kolejności
        Set<Integer> uniqueNumbers = new HashSet<>();
        uniqueNumbers.add(1);
        uniqueNumbers.add(1); // To się nie doda podwójnie
        
        // MAPA (MAP): Przechowuje dane w formacie Klucz -> Wartość
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("Apple", "Jabłko");
        dictionary.put("Car", "Samochód");
        
        // Pobieranie z mapy
        String plWord = dictionary.get("Apple"); // Zwróci "Jabłko"
    }
}
