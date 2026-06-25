import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTemplate {

    public void runTasks() {
        // Tworzymy pulę wątków. Liczbę wątków dopasowujemy do rdzeni procesora.
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        System.out.println("Utworzono pulę na " + cores + " wątków.");

        // Zlecamy np. 100 małych zadań
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            
            // submit() przekazuje zadanie do kolejki. Kiedy jakiś wątek z puli będzie wolny, weźmie się za to.
            executor.submit(() -> {
                System.out.println("Zadanie " + taskId + " wykonywane przez: " + Thread.currentThread().getName());
                try {
                    // Symulacja ciężkiej pracy (np. przetwarzanie rzędu pikseli)
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Ważne: zamykamy przyjmowanie nowych zadań! Bez tego program nigdy się nie wyłączy.
        executor.shutdown();

        // (Opcjonalnie) Czekamy aż wszystkie wątki skończą pracę
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
            System.out.println("Wszystkie zadania z puli zostały zakończone.");
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    public static void main(String[] args) {
        new ThreadPoolTemplate().runTasks();
    }
}
