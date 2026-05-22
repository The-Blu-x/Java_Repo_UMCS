public class DeathCauseStatistic {
    private String icd10Code;
    private int[] deathsByAgeGroup;

    // Prywatny konstruktor - obiekty tworzymy za pomocą metody fabrykującej fromCsvLine
    private DeathCauseStatistic() {
    }

    // Akcesor do kodu ICD-10
    public String getIcd10Code() {
        return icd10Code;
    }

    // Zadanie 1 - tworzenie obiektu na podstawie linii CSV
    public static DeathCauseStatistic fromCsvLine(String line) {
        DeathCauseStatistic stat = new DeathCauseStatistic();
        // Dzielimy po przecinku
        String[] parts = line.split(",");

        // Zgodnie z poleceniem uwzględniamy tabulator (lub inne białe znaki) i usuwamy go metodą trim()
        stat.icd10Code = parts[0].trim();

        // Tablica ma 20 elementów (od '0-4' do '95 lat i więcej')
        stat.deathsByAgeGroup = new int[20];

        // Pierwsze dwie kolumny to kod i suma 'Razem', poszczególne grupy wiekowe zaczynają się od indeksu 2
        for (int i = 0; i < 20; i++) {
            String countString = parts[i + 2];
            // Kreska '-' oznacza 0 zgonów
            if (countString.trim().equals("-")) {
                stat.deathsByAgeGroup[i] = 0;
            } else {
                stat.deathsByAgeGroup[i] = Integer.parseInt(countString);
            }
        }
        return stat;
    }

    // Zadanie 2 - zwracanie obiektu AgeBracketDeaths
    public AgeBracketDeaths getAgeBracket(int age) {
        int index = age / 5; // Ponieważ grupy są co 5 lat (0-4, 5-9 itd.)
        if (index > 19) {
            index = 19; // Ograniczamy dla osób mających 95+ lat
        }

        int young = index * 5;
        int old = (index == 19) ? Integer.MAX_VALUE : young + 4;

        return new AgeBracketDeaths(young, old, deathsByAgeGroup[index]);
    }

    // Zadanie 2 - wewnętrzna klasa AgeBracketDeaths
    public class AgeBracketDeaths {
        public final int young;
        public final int old;
        public final int deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }
}