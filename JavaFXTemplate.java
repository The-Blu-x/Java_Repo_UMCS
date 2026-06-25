import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {

    // Wymiary okna (zmień zgodnie z poleceniem)
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // --- OBSŁUGA ZDARZEŃ (Mysz i Klawiatura) ---
        
        // Kliknięcie myszą w płótno
        canvas.setOnMouseClicked(this::handleMouseClick);
        
        // Wciśnięcie klawisza (wymaga skupienia na scenie)
        scene.setOnKeyPressed(this::handleKeyPress);

        // --- INICJALIZACJA OKNA ---
        primaryStage.setTitle("Kolokwium 2 - Szablon JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        // Pierwsze narysowanie klatki
        redraw(); 
        
        primaryStage.show();
        canvas.requestFocus(); // Ważne, żeby klawiatura od razu działała

        // --- TUTAJ URUCHAMIAJ SERWER W TLE ---
        // ServerTemplate server = new ServerTemplate();
        // Thread serverThread = new Thread(() -> server.startServer());
        // serverThread.setDaemon(true); // Zabije serwer po zamknięciu okna
        // serverThread.start();
    }

    // Metoda do obsługi myszy
    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        System.out.println("Kliknięto myszą w: " + x + ", " + y);
        // Zapisz do listy, wyślij do serwera, itp.
    }

    // Metoda do obsługi klawiatury
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> System.out.println("Strzałka w górę");
            case DOWN -> System.out.println("Strzałka w dół");
            case LEFT -> System.out.println("Strzałka w lewo");
            case RIGHT -> System.out.println("Strzałka w prawo");
        }
        // Po zmianie stanu gry/aplikacji zawsze odświeżamy ekran!
        redraw();
    }

    // GŁÓWNA METODA RYSUJĄCA
    // Pamiętaj: Jeśli wywołujesz ją z innego wątku (np. z wątku serwera), 
    // musisz użyć: Platform.runLater(this::redraw);
    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 1. Zawsze najpierw czyścimy ekran (np. na biało)
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 2. Rysujemy nasze elementy z list/bazy danych
        gc.setFill(Color.RED);
        gc.fillOval(50, 50, 20, 20); // Przykładowe kółko
        
        gc.setStroke(Color.BLACK);
        gc.strokeLine(100, 100, 200, 200); // Przykładowa linia
    }

    public static void main(String[] args) {
        launch(args);
    }
}
