import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTemplate {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public void startClient() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Połączono z serwerem!");

            // KROK 1: Uruchamiamy WĄTEK W TLE do odbierania wiadomości od serwera
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("Serwer pisze: " + serverResponse);
                        // Jeśli używasz JavaFX, tutaj zrób: Platform.runLater(() -> ...);
                    }
                } catch (Exception e) {
                    System.out.println("Rozłączono z serwerem.");
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();

            // KROK 2: Główny wątek służy do wysyłania wiadomości (tu: z konsoli)
            // W wersji JavaFX ten krok pominiesz na rzecz podpięcia 'out.println()' pod przyciski
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("/quit")) {
                    break;
                }
                out.println(userInput); // Wysyłanie do serwera
            }

        } catch (Exception e) {
            System.err.println("Błąd klienta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ClientTemplate().startClient();
    }
}
