import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTemplate {

    // Port, na którym serwer będzie nasłuchiwał (zmień zgodnie z poleceniem na kolokwium)
    private static final int PORT = 12345;

    // Metoda uruchamiająca serwer. Wywołaj ją w nowym wątku z klasy głównej!
    // Np.: new Thread(() -> serverTemplate.startServer()).start();
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer uruchomiony. Nasłuchuje na porcie: " + PORT);

            // Nieskończona pętla oczekująca na nowe połączenia
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Tu program czeka na klienta
                System.out.println("Nowy klient połączony: " + clientSocket.getInetAddress());

                // KROK KLUCZOWY: Odpalamy obsługę klienta w nowym wątku
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (Exception e) {
            System.err.println("Błąd głównego serwera: " + e.getMessage());
        }
    }

    // Metoda obsługująca pojedynczego klienta w jego własnym wątku
    private void handleClient(Socket clientSocket) {
        // try-with-resources automatycznie zamknie socket i czytnik na koniec
        try (
            clientSocket; // Dodajemy socket, by sam się zamknął po wyjściu z bloku
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            
            // Czytamy linijka po linijce, dopóki klient się nie rozłączy (zwróci null)
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (inputLine.isEmpty()) continue;

                // --- TUTAJ LOGIKA KOLOKWIUM ---
                System.out.println("Otrzymano wiadomość: " + inputLine);
                
                // Przykład parsowania komendy (np. "KROPKA 50 100"):
                // String[] parts = inputLine.split("\\s+");
                // if (parts[0].equals("KROPKA")) { ... }
                
                // PAMIĘTAJ: Jeśli ta wiadomość ma coś narysować na ekranie, 
                // musisz to zlecić głównemu wątkowi JavaFX!
                // Np.: Platform.runLater(() -> rysujKropke(x, y));
                // ------------------------------
            }
        } catch (Exception e) {
            System.err.println("Błąd komunikacji z klientem lub klient się rozłączył: " + e.getMessage());
        }
    }
}
