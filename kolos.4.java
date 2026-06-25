import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

// Poniższa funkcja należy do głównej pętli Servera sieciowego (tam pod komentarzem "Tu nastąpią kolejne kroki"):

// [...] Wnętrze pętli z ImageServer
            String savedFilePath = receiveImage(clientSocket);

            if (savedFilePath != null) {
                // Czytamy zmienną z GUI
                int kernelSize = ServerUI.currentKernelSize; 
                String processedPath = savedFilePath.replace(".png", "_blur.png");

                // Filtrowanie Box Blur i odczyt czasu (Kroki 4)
                long delay = BlurFilter.applyBlur(savedFilePath, processedPath, kernelSize);

                // Rejestracji w bazie (Krok 5)
                logToDatabase(processedPath, kernelSize, delay);

                // Odpowiedź na rzuconą piłkę. Wysyłamy i gasimy po sobie (Krok 6)
                sendProcessedImage(processedPath, clientSocket);
                
                clientSocket.close(); // Kończymy to połączenie i przygotowujemy na kolejne (Krok 6)[cite: 10]
            }

// [...] Poniżej oddzielne metody

    private void logToDatabase(String path, int size, long delay) {
        String dbUrl = "jdbc:sqlite:images/index.db";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS conversions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "path TEXT NOT NULL,"
                + "size INTEGER,"
                + "delay INTEGER"
                + ");";
        String insertSQL = "INSERT INTO conversions(path, size, delay) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
             
            // Weryfikacja istnienia struktury tabeli bazy[cite: 10]
            stmt.execute(createTableSQL);

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, path);
                pstmt.setInt(2, size);
                pstmt.setLong(3, delay);
                pstmt.executeUpdate();
                System.out.println("Zapisano log w bazie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendProcessedImage(String path, Socket socket) {
        try {
            File file = new File(path);
            FileInputStream input = new FileInputStream(file);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            byte[] buffer = new byte[8192];
            int count;
            // Podajemy długość dla Klienta, żeby jego pętla while() mogła oszacować wagę przed zbuforowaniem[cite: 9]
            output.writeLong(file.length());
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            output.flush(); // Zabezpiecza by resztka wisząca ew. w lejku została zrzucona

            System.out.println("Odesłano rozmyty plik do Klienta.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
