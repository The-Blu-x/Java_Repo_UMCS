import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameLoopTemplate extends Application {

    private double ballX = 250;
    private double ballY = 250;
    private double velocityX = 3; // Prędkość w poziomie
    private double velocityY = 3; // Prędkość w pionie

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        // --- PĘTLA GRY ---
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // 'now' to czas w nanosekundach. Możesz obliczać delta time,
                // ale w najprostszej wersji wystarczy po prostu przesuwać obiekty i rysować.

                updatePhysics(canvas.getWidth(), canvas.getHeight());
                render(gc, canvas.getWidth(), canvas.getHeight());
            }
        };
        timer.start(); // Uruchomienie pętli gry

        primaryStage.setTitle("Szablon Pętli Gry - AnimationTimer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Logika (ruch, kolizje z krawędziami)
    private void updatePhysics(double width, double height) {
        ballX += velocityX;
        ballY += velocityY;

        // Odbicie od lewej/prawej ściany
        if (ballX <= 0 || ballX >= width - 20) { // 20 to średnica piłki
            velocityX *= -1;
        }
        // Odbicie od góry/dołu
        if (ballY <= 0 || ballY >= height - 20) {
            velocityY *= -1;
        }
    }

    // Rysowanie zaktualizowanego stanu
    private void render(GraphicsContext gc, double width, double height) {
        // 1. Zmaż starą klatkę
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // 2. Narysuj nowe obiekty
        gc.setFill(Color.WHITE);
        gc.fillOval(ballX, ballY, 20, 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
