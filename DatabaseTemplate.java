import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTemplate {

    // Ścieżka do pliku bazy danych (utworzy się w folderze projektu)
    private static final String URL = "jdbc:sqlite:kolokwium.db";

    public DatabaseTemplate() {
        // Przy tworzeniu obiektu od razu zakładamy tabelę, jeśli nie istnieje
        initializeDatabase();
    }

    // Nawiązywanie połączenia (prywatna metoda pomocnicza)
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Tworzenie tabeli
    private void initializeDatabase() {
        // Zmień nazwy kolumn i typy (INTEGER, REAL, TEXT) według zadania
        String sql = """
            CREATE TABLE IF NOT EXISTS dane (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nazwa TEXT NOT NULL,
                wartosc REAL NOT NULL
            );
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela w bazie gotowa.");
        } catch (SQLException e) {
            System.err.println("Błąd inicjalizacji bazy: " + e.getMessage());
        }
    }

    // Zapisywanie danych (INSERT) - ZAWSZE z użyciem PreparedStatement!
    public void insertData(String nazwa, double wartosc) {
        String sql = "INSERT INTO dane (nazwa, wartosc) VALUES (?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Indeksy znaków zapytania '?' zaczynają się od 1
            pstmt.setString(1, nazwa);
            pstmt.setDouble(2, wartosc);
            
            pstmt.executeUpdate();
            System.out.println("Zapisano do bazy: " + nazwa);
        } catch (SQLException e) {
            System.err.println("Błąd zapisu: " + e.getMessage());
        }
    }

    // Odczytywanie danych (SELECT)
    public void printAllData() {
        String sql = "SELECT id, nazwa, wartosc FROM dane";

        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            // Przechodzimy wiersz po wierszu
            while (rs.next()) {
                int id = rs.getInt("id");
                String nazwa = rs.getString("nazwa");
                double wartosc = rs.getDouble("wartosc");
                
                System.out.println("Rekord: ID=" + id + ", Nazwa=" + nazwa + ", Wartość=" + wartosc);
            }
        } catch (SQLException e) {
            System.err.println("Błąd odczytu: " + e.getMessage());
        }
    }
}
