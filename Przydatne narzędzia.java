import java.util.Random;

public class UtilsTemplates {

    // Szablon: Mierzenie czasu wykonania bloku kodu
    public static void measureExecutionTime() {
        // Zapisujemy czas przed wykonaniem kodu (w nanosekundach)
        long startTime = System.nanoTime();

        // TUTAJ WSTAWIASZ SWÓJ KOD, KTÓRY CHCESZ ZMIERZYĆ
        for (int i = 0; i < 1000000; i++) {
            Math.pow(i, 2);
        }

        // Zapisujemy czas po wykonaniu kodu
        long endTime = System.nanoTime();
        
        // Obliczamy różnicę i konwertujemy na milisekundy
        long durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Czas wykonania: " + durationInMillis + " ms");
    }

    // Szablon: Generowanie losowej liczby całkowitej w danym zakresie (min, max)
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        // random.nextInt() generuje od 0 (włącznie) do wartości podanej w nawiasie (wyłącznie)
        return random.nextInt((max - min) + 1) + min;
    }
}
