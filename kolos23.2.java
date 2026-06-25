import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerUI extends Application {

    // Ta zmienna przechowuje nasz filtr i musi być wątkobezpieczna
    // W najprostszym przypadku użyjemy zmiennej lotnej (volatile) lub AtomicInteger
    public static volatile int currentKernelSize = 1;

    @Override
    public void start(Stage primaryStage) {
        // Suwak: min=1, max=15, zaczyna od 1
        Slider slider = new Slider(1, 15, 1);
        slider.setMajorTickUnit(2); // Przeskoki co 2
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true); // Przyciągaj do nieparzystych (skoro przeskoki co 2 startujące od 1)

        Label label = new Label("Promień filtra (jądro splotu): 1");

        // Detekcja przesuwania slidera
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int val = newVal.intValue();
            // Zabezpieczenie: jeśli przesunięcie zatrzyma się na liczbie parzystej
            if (val % 2 == 0) val += 1;
            if(val > 15) val = 15;
            
            currentKernelSize = val; // Zapisz dla wątku roboczego
            label.setText("Promień filtra (jądro splotu): " + currentKernelSize);
        });

        VBox root = new VBox(10, label, slider);
        Scene scene = new Scene(root, 300, 100);
        primaryStage.setTitle("Konfiguracja Serwera");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Uruchamiamy serwer nasłuchujący w tle
        Thread serverThread = new Thread(() -> new ImageServer().startServer());
        serverThread.setDaemon(true);
        serverThread.start();
    }
}
