public class ExceptionTemplates {
    
    public static void divide(int a, int b) {
        try {
            // Kod, który może rzucić wyjątek
            int result = a / b;
            System.out.println("Wynik: " + result);
        } catch (ArithmeticException e) {
            // Reakcja na konkretny błąd (np. dzielenie przez zero)
            System.err.println("Błąd: Nie wolno dzielić przez zero!");
        } finally {
            // Wykona się ZAWSZE, niezależnie czy był błąd, czy nie 
            // (używane do zamykania zasobów, np. połączenia z bazą)
            System.out.println("Zakończono próbę obliczeń.");
        }
    }
    
    // Własny wyjątek (tworzysz go tworząc klasę dziedziczącą po Exception)
    public static void checkAge(int age) throws Exception {
        if (age < 18) {
            throw new Exception("Osoba jest niepełnoletnia!");
        }
    }
}
