import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageServer {

    private final int port = 5000; // Zgodnie z tym, czego szuka Client.java

    public void startServer() {
        // Upewniamy się, że folder "images" istnieje przed startem
        Path imageDir = Paths.get("images");
        if (!Files.exists(imageDir)) {
            try {
                Files.createDirectory(imageDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serwer gotowy do obierania obrazów...");

            while (true) {
                // To blokuje dopóki klient się nie połączy
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Podłączono klienta!");

                // Odbierz plik (Kroki 1 i 2)
                String savedFilePath = receiveImage(clientSocket);

                if (savedFilePath != null) {
                   // Tu nastąpią kolejne kroki (Blur, DB, Odsyłanie)
                   // ...
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receiveImage(Socket clientSocket) {
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            
            // Generujemy nazwę pliku w folderze images (Krok 2)
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filePath = "images/" + timeStamp + ".png";
            
            FileOutputStream output = new FileOutputStream(filePath);

            byte[] buffer = new byte[8192]; // Rozmiar bufora używanego przez klienta
            int count;
            int receivedSize = 0;
            
            // Odczytujemy wagę pliku wg formatu Klienta
            long fileSize = input.readLong(); 

            while (receivedSize < fileSize) {
                count = input.read(buffer);
                output.write(buffer, 0, count);
                receivedSize += count;
            }
            output.close();
            System.out.println("Odebrano i zapisano: " + filePath);
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
