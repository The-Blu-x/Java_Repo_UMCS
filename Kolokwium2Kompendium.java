import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Importy dla JavaFX (wymagają skonfigurowanego środowiska JavaFX)
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

/**
 * KOMPENDIUM: DRUGIE KOLOKWIUM
 * Zawiera absolutne minimum i gotowe wzorce (tzw. boilerplate), 
 * z których poskładasz zadania na egzaminie.
 */
public class Kolokwium2Kompendium {

    public static void main(String[] args) {
        System.out.println("--- ROZPOCZYNAMY POWTÓRKĘ DO KOLOKWIUM 2 ---");

        Kolokwium2Kompendium kompendium = new Kolokwium2Kompendium();
        
        // Odkomentuj wybraną metodę, aby przetestować jej działanie:
        // kompendium.pokazWielowatkowosc();
        // kompendium.pokazBazeDanych();
        // kompendium.pokazSerwerSieciowy(); // To zablokuje program, bo czeka na klienta!
        
        // Uruchomienie aplikacji JavaFX (wykomentowane, bo wymaga odpalenia z wątku FX)
        // Application.launch(SzablonGraficznyFX.class, args);
    }

    // ==========================================
    // 1. WIELOWĄTKOWOŚĆ (Threads & Executors)
    // ==========================================
    public void pokazWielowatkowosc() {
        System.out.println("\n[1] WIELOWĄTKOWOŚĆ");

        // SPOSÓB A: Pojedynczy wątek (np. do nasłuchiwania serwera)
        // Definiujemy zadanie wewnątrz wyrażenia lambda () -> { ... }
        Thread watekWTle = new Thread(() -> {
            System.out.println("Zadanie w tle wykonuje: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Usypia wątek na 1 sekundę
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        watekWTle.setDaemon(true); // Jeśli true, wątek zginie po wyłączeniu głównego programu
        watekWTle.start(); // PAMIĘTAJ! .start(), a nie .run(). Samo .run() nie tworzy nowego wątku!

        // SPOSÓB B: Pula wątków (np. do obróbki obrazka po wierszach)
        // Tworzymy "brygadę robotników", w tym przypadku 4 wątki.
        ExecutorService pulaWatkow = Executors.newFixedThreadPool(4);
        
        // Zlecamy puli 10 zadań. System sam rozdzieli je między 4 wolne wątki.
        for (int i = 0; i < 10; i++) {
            final int idZadania = i;
            pulaWatkow.submit(() -> {
                System.out.println("Zadanie nr " + idZadania + " zrobione przez " + Thread.currentThread().getName());
            });
        }
        // Bardzo ważne: zamknij pulę po dodaniu wszystkich zadań! 
        // Inaczej program nigdy się nie wyłączy, bo pula będzie czekać na nowe zlecenia.
        pulaWatkow.shutdown(); 
    }

    // ==========================================
    // 2. BAZY DANYCH (JDBC - SQLite)
    // ==========================================
    public void pokazBazeDanych() {
        System.out.println("\n[2] BAZY DANYCH (SQLite)");

        // Adres URL do bazy - w SQLite baza to po prostu plik. Tutaj plik "testowa.db".
        String url = "jdbc:sqlite:testowa.db";

        // Używamy bloku try-with-resources!
        // Wszystko, co zadeklarujemy w nawiasach (...), ZAMKNIE SIĘ SAMO (connection, statement).
        // To zapobiega blokowaniu pliku bazy na dysku (Database is locked).
        String sqlTworzenieTabeli = "CREATE TABLE IF NOT EXISTS uzytkownicy (id INTEGER PRIMARY KEY, nazwa TEXT)";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            
            // Wykonujemy proste polecenie bez parametrów (tworzenie tabeli)
            stmt.execute(sqlTworzenieTabeli);
            System.out.println("Tabela gotowa.");

            // DODAWANIE DANYCH (INSERT) - Zawsze używaj PreparedStatement dla bezpieczeństwa!
            // Zamiast doklejać zmienne przez plusiki (co grozi zhakowaniem przez SQL Injection),
            // wstawiamy znaki zapytania '?', a potem je wypełniamy.
            String sqlInsert = "INSERT INTO uzytkownicy (nazwa) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                pstmt.setString(1, "Jan Kowalski"); // Podmieniamy pierwszy znak zapytania
                pstmt.executeUpdate(); // executeUpdate do modyfikacji bazy (INSERT, UPDATE, DELETE)
            }

            // ODCZYTYWANIE DANYCH (SELECT)
            String sqlSelect = "SELECT id, nazwa FROM uzytkownicy";
            try (ResultSet rs = stmt.executeQuery(sqlSelect)) { // executeQuery do SELECT
                // rs.next() przesuwa kursor do kolejnego wiersza tabeli
                while (rs.next()) {
                    System.out.println("Z bazy: ID=" + rs.getInt("id") + ", Nazwa=" + rs.getString("nazwa"));
                }
            }

        } catch (Exception e) {
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

    // ==========================================
    // 3. GNIAZDA SIECIOWE (Sockets - Serwer i Klient)
    // ==========================================
    public void pokazSerwerSieciowy() {
        System.out.println("\n[3] SIECI - SERWER NASŁUCHUJĄCY");
        
        // Serwer zajmuje dany port (np. 12345)
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Serwer czeka na połączenia...");
            
            // Pętla while(true), aby serwer przyjmował klientów bez końca
            while (true) {
                // Ta linijka ZABLOKUJE PROGRAM, dopóki ktoś się nie podłączy.
                // Dlatego cały ten kod musi zawsze być w oddzielnym wątku (Thread)!
                Socket klient = serverSocket.accept(); 
                System.out.println("Ktoś się podłączył!");

                // Otrzymaliśmy klienta. Oddajemy go do obsługi w nowym wątku, 
                // a główna pętla serwera wraca na początek czekać na kolejną osobę.
                new Thread(() -> obsluzKlienta(klient)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metoda pomocnicza dla serwera sieciowego z sekcji 3
    private void obsluzKlienta(Socket socket) {
        // BufferedReader pozwala czytać tekst linijka po linijce
        try (BufferedReader wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter wyjscie = new PrintWriter(socket.getOutputStream(), true)) {
             
            String wiadomosc;
            // Pętla odczytuje wiadomości dopóki klient się nie rozłączy (readLine() zwroci null)
            while ((wiadomosc = wejscie.readLine()) != null) {
                System.out.println("Klient przysłał: " + wiadomosc);
                wyjscie.println("Serwer potwierdza: " + wiadomosc); // Odpowiedź do klienta
            }
        } catch (Exception e) {
            System.out.println("Klient się rozłączył.");
        }
    }
}

// ==========================================
// 4. GRAFIKA (JavaFX)
// ==========================================
/**
 * Główna klasa GUI zawsze dziedziczy po 'Application'.
 * Metoda start() to taki "public static void main", ale tylko dla okienek.
 */
class SzablonGraficznyFX extends Application {

    // Canvas to "płótno". Narzędzie do którego podpinamy "pędzel" (GraphicsContext)
    private Canvas canvas;
    private double pilkaX = 50, pilkaY = 50;

    @Override
    public void start(Stage okno) {
        canvas = new Canvas(400, 400); // Ustawiamy rozmiar płótna
        Pane root = new Pane(canvas);  // Pane to pojemnik, do którego wrzucamy płótno
        Scene scena = new Scene(root); // Scena to cała zawartość okna

        okno.setScene(scena);
        okno.setTitle("Ściąga JavaFX");
        okno.show(); // Pokazujemy okno na ekranie

        rysujKlatke(); // Wywołujemy nasze własne rysowanie

        // --- PĘTLA GRY (Opcjonalnie, gdy w zadaniu jest gra jak Breakout) ---
        // AnimationTimer działa cały czas (zazwyczaj 60 razy na sekundę).
        AnimationTimer petlaGry = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Ta metoda wywołuje się w kółko co ułamek sekundy
                pilkaX += 1; // Zmieniamy pozycję piłki
                rysujKlatke(); // Przemalowujemy ekran
            }
        };
        // petlaGry.start(); 

        // --- ZŁOTA ZASADA JAVAFX: INTERAKCJE SIECIOWE ---
        // Jeżeli w tle działa u Ciebie klient sieciowy i dostanie od kogoś 
        // polecenie "ZMIEN_KOLOR", wątek sieciowy NIE MOŻE dotknąć elementów okna (Canvasu).
        // Wywali to błąd "Not on FX application thread".
        // Rozwiązanie: Owijamy zmianę grafiki w Platform.runLater():
        
        // Z wątku w tle wywołujesz:
        // Platform.runLater(() -> {
        //     rysujKlatke(); // To wykona się bezpiecznie na właściwym wątku
        // });
    }

    // Twoja własna metoda rysująca to, co chcesz
    private void rysujKlatke() {
        // Z płótna bierzemy GraphicsContext - to nasz "pędzel"
        GraphicsContext pędzel = canvas.getGraphicsContext2D();

        // Najpierw zamazujemy wszystko starą farbą (czyszczenie ekranu)
        pędzel.setFill(Color.WHITE);
        pędzel.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Rysujemy nowe kształty
        pędzel.setFill(Color.BLUE);
        pędzel.fillOval(pilkaX, pilkaY, 30, 30); // Kółko na współrzędnych pilkaX, pilkaY
    }
}
