import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.Color;

public class BlurFilter {

    public static long applyBlur(String inputPath, String outputPath, int kernelRadius) {
        long startTime = System.currentTimeMillis();

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputPath));
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            
            // Tworzymy nową mapę bitową, żeby nie czytać z tego co w tej samej chwili niszczymy (nadpisujemy blurem)
            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            int cores = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(cores);

            // Obliczamy "grubość" kawałka, który zlecimy każdemu z wątków
            int chunkHeight = height / cores;

            for (int i = 0; i < cores; i++) {
                final int startY = i * chunkHeight;
                // Jeśli jest to ostatni rdzeń, zleć mu paczkę z ewentualną nieszczęsną resztą (by nie wyjść poza tablicę)
                final int endY = (i == cores - 1) ? height : startY + chunkHeight;

                executor.submit(() -> processChunk(inputImage, outputImage, startY, endY, width, height, kernelRadius));
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS); // Blokuj dopóki nie skończą

            // Zapis zmodyfikowanego
            ImageIO.write(outputImage, "png", new File(outputPath));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return System.currentTimeMillis() - startTime; // Krok 5: potrzebny czas wykonywania do bazy danych
    }

    private static void processChunk(BufferedImage source, BufferedImage target, int startY, int endY, int width, int height, int radius) {
        for (int y = startY; y < endY; y++) {
            for (int x = 0; x < width; x++) {
                
                int redSum = 0, greenSum = 0, blueSum = 0;
                int count = 0;

                // Sprawdzamy sąsiadów wokół (kernelRadius mianuje zasięg od 1 do 15 we wszystkich kierunkach)
                for (int ny = Math.max(0, y - radius); ny <= Math.min(height - 1, y + radius); ny++) {
                    for (int nx = Math.max(0, x - radius); nx <= Math.min(width - 1, x + radius); nx++) {
                        Color pixel = new Color(source.getRGB(nx, ny));
                        redSum += pixel.getRed();
                        greenSum += pixel.getGreen();
                        blueSum += pixel.getBlue();
                        count++;
                    }
                }

                // Kalkulujemy średnią dla rozmytego piksela (Krok 4)
                int newRed = redSum / count;
                int newGreen = greenSum / count;
                int newBlue = blueSum / count;

                Color newColor = new Color(newRed, newGreen, newBlue);
                target.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}
