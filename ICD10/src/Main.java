import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Zakładamy, że pliki znajdują się w tym samym katalogu co skompilowany program
        String csvPath = "zgony(1).csv";
        String icdPath = "icd10.txt";

        System.out.println("Rozpoczynam ładowanie statystyk zgonów z pliku CSV...");
        DeathCauseStatisticList statsList = new DeathCauseStatisticList();
        statsList.repopulate(csvPath);
        System.out.println("Statystyki załadowane pomyślnie.\n");

        System.out.println("Rozpoczynam ładowanie bazy kodów ICD-10 do pamięci RAM...");
        // Wykorzystujemy implementację zoptymalizowaną pod kątem czasu
        ICDCodeTabular icdTabular = new ICDCodeTabularOptimizedForTime(icdPath);
        System.out.println("Kody ICD-10 załadowane pomyślnie.\n");

        // Parametry naszego wyszukiwania:
        int targetAge = 65;
        int topN = 5;

        System.out.println("==========================================================");
        System.out.println("Top " + topN + " najczęstszych przyczyn zgonów dla wieku: " + targetAge + " lat");
        System.out.println("==========================================================");

        // Pobieramy listę najgroźniejszych chorób
        List<DeathCauseStatistic> topDiseases = statsList.mostDeadlyDiseases(targetAge, topN);

        // Wypisujemy wyniki
        for (int i = 0; i < topDiseases.size(); i++) {
            DeathCauseStatistic stat = topDiseases.get(i);
            String code = stat.getIcd10Code();
            DeathCauseStatistic.AgeBracketDeaths bracket = stat.getAgeBracket(targetAge);

            String description;
            try {
                // Próbujemy pobrać opis choroby na podstawie kodu
                description = icdTabular.getDescription(code);
            } catch (IndexOutOfBoundsException e) {
                // Jeśli w pliku icd10.txt brakuje opisu dla danego kodu
                description = "Brak opisu w bazie dla tego kodu";
            }

            System.out.printf("%d. Kod: %-6s | Zgony: %-5d | Przedział wiekowy: %d-%d | Opis: %s%n",
                    (i + 1),
                    code,
                    bracket.deathCount,
                    bracket.young,
                    bracket.old,
                    description
            );
        }
    }
}