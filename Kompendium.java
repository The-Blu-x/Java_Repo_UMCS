import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * To jest komentarz blokowy (dokumentacyjny - Javadoc).
 * Opisuje się tu całe klasy lub metody.
 * 
 * Zasada 1: Każdy program w Javie musi składać się z klas.
 * Zasada 2: Nazwa pliku musi być identyczna z nazwą klasy publicznej (tutaj: Kompendium.java).
 */
public class Kompendium {

    // ==========================================
    // 1. ZMIENNE I TYPY DANYCH (Pola klasy)
    // ==========================================
    
    // Zmienne prymitywne (mała litera, przechowują proste wartości, zajmują mało pamięci)
    int liczbaCalkowita = 10;
    double liczbaZmiennoprzecinkowa = 3.14;
    boolean wartoscLogiczna = true; // true lub false
    char pojedynczyZnak = 'A'; // Pojedyncze cudzysłowy dla typu char!

    // Zmienne obiektowe / referencyjne (Wielka litera, przechowują "adres" do obiektu w pamięci)
    String tekst = "Witaj świecie!"; // Podwójne cudzysłowy dla tekstu!
    
    // Zmienne statyczne (static) należą do całej klasy, a nie do konkretnego obiektu.
    // Zmienne "final" to stałe - nie można zmienić ich wartości po przypisaniu.
    public static final double PI = 3.14159;


    // ==========================================
    // 2. PUNKT STARTOWY PROGRAMU (Metoda main)
    // ==========================================
    
    // To jest najważniejsza metoda. Kiedy klikasz "Run", Java szuka dokładnie tej linijki.
    public static void main(String[] args) {
        
        // Wypisywanie na ekran
        System.out.println("Zaczynamy powtórkę z Javy!"); // 'println' dodaje enter na końcu
        System.out.print("To jest w jednej linii... ");
        System.out.println("A to jest doklejone do niej.");

        // Aby użyć metod z naszej klasy, musimy najpierw stworzyć jej OBIEKT (instancję)
        Kompendium mojeKompendium = new Kompendium();
        
        // Wywołujemy inne metody na naszym obiekcie
        mojeKompendium.instrukcjeSterujace();
        mojeKompendium.kolekcjeTablice();
        mojeKompendium.wyjatki();
        
        // Użycie własnej klasy obiektowej (zdefiniowanej na dole pliku)
        Samochod auto = new Samochod("Toyota", 2020);
        auto.uruchom();
    }


    // ==========================================
    // 3. METODY I INSTRUKCJE STERUJĄCE
    // ==========================================
    
    // Konstrukcja metody:
    // [modyfikator_dostępu] [zwracany_typ] [nazwa_metody]([argumenty])
    // void - oznacza, że metoda nie zwraca żadnego wyniku (tylko coś robi).
    public void instrukcjeSterujace() {
        
        System.out.println("\n--- INSTRUKCJE STERUJĄCE ---");

        // IF - instrukcja warunkowa
        int wiek = 20;
        if (wiek >= 18) {
            System.out.println("Pełnoletni");
        } else if (wiek == 17) {
            System.out.println("Prawie pełnoletni");
        } else {
            System.out.println("Niepełnoletni");
        }

        // Pętla FOR - kiedy wiesz, ile razy chcesz coś wykonać
        // (stan_początkowy; warunek_zakończenia; co_krok)
        for (int i = 0; i < 3; i++) {
            System.out.println("Pętla for, iteracja nr: " + i);
        }

        // Pętla WHILE - kiedy nie wiesz, ile razy się wykona, 
        // ale ma się wykonywać dopóki warunek jest prawdziwy
        int licznik = 0;
        while (licznik < 2) {
            System.out.println("Pętla while, licznik: " + licznik);
            licznik++; // Zwiększamy o 1, inaczej pętla byłaby nieskończona!
        }
    }


    // ==========================================
    // 4. TABLICE I KOLEKCJE (Listy, Mapy)
    // ==========================================
    
    public void kolekcjeTablice() {
        
        System.out.println("\n--- TABLICE I KOLEKCJE ---");

        // TABLICA - ma z góry ustalony rozmiar. Nie można jej rozciągnąć.
        int[] liczby = new int[3]; // Tablica na 3 elementy (indeksy 0, 1, 2)
        liczby[0] = 10;
        liczby[1] = 20;
        
        // LISTA (ArrayList) - potrafi automatycznie rosnąć.
        // W <> podajemy typ obiektowy (np. Integer zamiast int, Double zamiast double).
        List<String> imiona = new ArrayList<>();
        imiona.add("Anna");
        imiona.add("Jan");
        imiona.add("Piotr");
        imiona.remove("Jan"); // Usuwamy element

        // Najwygodniejszy sposób iterowania po liście (tzw. pętla for-each)
        // Czytaj jako: "dla każdego Stringa imie znajdującego się w liście imiona"
        for (String imie : imiona) {
            System.out.println("Imię z listy: " + imie);
        }

        // MAPA (Słownik) - przechowuje dane w parach Klucz -> Wartość.
        // Klucze muszą być unikalne. Przydatne do np. bazy danych w pamięci.
        Map<String, Integer> oceny = new HashMap<>();
        oceny.put("Kowalski", 5);
        oceny.put("Nowak", 3);
        System.out.println("Ocena Kowalskiego to: " + oceny.get("Kowalski"));
    }


    // ==========================================
    // 5. OBSŁUGA BŁĘDÓW (Wyjątki - try/catch)
    // ==========================================
    
    public void wyjatki() {
        System.out.println("\n--- WYJĄTKI ---");
        
        // Blok try-catch pozwala programowi przetrwać błąd, 
        // zamiast "wywalić się" do pulpitu z czerwonym napisem w konsoli.
        try {
            // Kod, który potencjalnie może rzucić błąd
            int wynik = 10 / 0; // Nie wolno dzielić przez zero!
            System.out.println(wynik); // Ta linijka się nigdy nie wykona
        } catch (ArithmeticException e) {
            // Kod, który ratuje sytuację, gdy wystąpi ten konkretny błąd
            System.out.println("Złapano błąd matematyczny: Nie dziel przez zero!");
        } catch (Exception e) {
            // "Exception" wyłapie absolutnie każdy inny, nieprzewidziany błąd
            System.out.println("Inny błąd: " + e.getMessage());
        } finally {
            // Ten blok wykona się ZAWSZE, bez względu na to, czy był błąd, czy nie.
            // Służy np. do zamykania połączeń sieciowych czy z bazą danych!
            System.out.println("Blok finally sprząta po wykonaniu (np. zamyka pliki).");
        }
    }
}

// ==========================================
// 6. PROGRAMOWANIE OBIEKTOWE (Klasy i hermetyzacja)
// ==========================================

// To jest dodatkowa klasa wewnątrz tego samego pliku (nie może być public).
// W prawdziwych projektach każda klasa powinna mieć swój oddzielny plik.
class Samochod {
    
    // Pola zazwyczaj są "private" (hermetyzacja) - dostęp tylko z wnętrza tej klasy.
    private String marka;
    private int rocznik;

    // KONSTRUKTOR - specjalna metoda odpalana tylko raz, w momencie użycia słowa "new".
    // Nie ma zwracanego typu (nawet void) i musi nazywać się DOKŁADNIE tak jak klasa.
    public Samochod(String marka, int rocznik) {
        // "this" odnosi się do pola danej klasy. 
        // Używamy tego, gdy nazwa argumentu metody jest taka sama jak pola klasy.
        this.marka = marka;
        this.rocznik = rocznik;
    }

    // Zwykła metoda przypisana do tego obiektu
    public void uruchom() {
        System.out.println("\n--- OBIEKTOWOŚĆ ---");
        System.out.println("Brum! Uruchomiono " + marka + " z rocznika " + rocznik);
    }

    // Gettery i Settery - publiczne metody do bezpiecznego odczytu/zapisu prywatnych pól.
    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        if (marka != null && !marka.isEmpty()) {
            this.marka = marka;
        } else {
            System.out.println("Marka nie może być pusta!");
        }
    }
}
