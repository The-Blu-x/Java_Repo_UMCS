public class StringTemplates {

    public static void stringManipulations() {
        String text = "Witaj świecie!";

        // 1. Zamiana konkretnego znaku lub ciągu znaków
        String replacedChar = text.replace('i', 'o'); // "Wotaj śwococe!"
        String replacedWord = text.replace("świecie", "Javo"); // "Witaj Javo!"

        // 2. Usuwanie białych znaków (spacji) z początku i końca
        String messyText = "   Za dużo spacji   ";
        String cleanText = messyText.trim(); // "Za dużo spacji"
        
        // W Javie 11+ dodano strip(), które lepiej radzi sobie ze znakami Unicode
        String superClean = messyText.strip(); 

        // 3. Odwracanie Stringa (wymaga StringBuilder, bo String nie ma reverse())
        String original = "Kajak";
        String reversed = new StringBuilder(original).reverse().toString();

        // 4. Rozdzielanie tekstu na tablicę po konkretnym znaku (np. przecinku)
        String csv = "Jabłko,Banan,Pomarańcza";
        String[] fruits = csv.split(","); // ["Jabłko", "Banan", "Pomarańcza"]
    }
}
