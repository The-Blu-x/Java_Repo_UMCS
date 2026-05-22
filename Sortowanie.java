import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortTemplates {

    public static void sortingExamples() {
        // 1. Sortowanie tablicy prymitywów (rosnąco)
        int[] numbers = {5, 2, 8, 1, 9};
        Arrays.sort(numbers); 
        // Wynik: [1, 2, 5, 8, 9]

        // 2. Sortowanie listy Stringów (alfabetycznie)
        List<String> names = Arrays.asList("Zosia", "Ania", "Tomek");
        Collections.sort(names); 
        // Alternatywa: names.sort(null);

        // 3. Sortowanie listy za pomocą lambdy (np. po długości słowa)
        List<String> words = Arrays.asList("Krab", "Słoń", "Zwierzęta", "Kot");
        words.sort((a, b) -> Integer.compare(a.length(), b.length()));
        // Wynik: Kot, Krab, Słoń, Zwierzęta

        // 4. Sortowanie malejąco (odwrotne)
        List<Integer> scores = Arrays.asList(10, 50, 20, 90);
        scores.sort(Collections.reverseOrder());
        // Wynik: [90, 50, 20, 10]
    }
}
