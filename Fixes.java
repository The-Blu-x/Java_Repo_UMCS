public static Vec2 fromPolar(double r, double angle) {
    // Zamiana stopni na radiany!
    double radians = Math.toRadians(angle);
    return new Vec2(r * Math.cos(radians), r * Math.sin(radians));
}

public Vec2 mul(double d) {
    // Zwracamy NOWY wektor, nie psujemy starego
    return new Vec2(this.x * d, this.y * d); 
}

public class Branch {
    private final Vec2 direction; // final = niemutowalne
    private final double thickness; // final = niemutowalne
    
    // Zamiast hasParent i parent_row:
    protected Branch parent; 
    protected double t; // Pozycja na gałęzi rodzica
    
    // Zamiast hasChild:
    protected List<Branch> children = new ArrayList<>(); 

    // ... konstruktory ...

    // Rozwiązanie KROKU 5 i 6:
    public void grow(double t, Branch child) {
        // 1. Sprawdzamy grubość i rzucamy wyjątek (Krok 6)
        if (child.getThickness() > this.thickness) {
            throw new RuntimeException("Dziecko nie może być grubsze od rodzica!"); // Tu wstaw własny wyjątek BranchThicknessException
        }
        // 2. Łączymy obiekty w drzewo (Krok 5)
        this.children.add(child); // Dodajemy dziecko do listy dzieci tego rodzica
        child.parent = this;      // Mówimy dziecku, kto jest jego rodzicem
        child.t = t;              // Zapisujemy pozycję
    }
}

// Metoda statyczna, zwraca zbudowane drzewo
public static Tree fromCsvFile(String filepath) {
    // Tworzymy listę, żeby obiekty nam "nie uciekły" z pamięci
    List<Branch> allBranches = new ArrayList<>();
    Tree root = null;

    try (BufferedReader br = Files.newBufferedReader(Path.of(filepath))) {
        String linia;
        br.readLine(); // Pomijamy pierwszy wiersz (nagłówki) na sucho

        while ((linia = br.readLine()) != null) {
            String[] col = linia.split(",");
            
            if (allBranches.isEmpty()) {
                // Pierwsza linijka z danymi to zawsze PIEŃ
                root = new Tree(Double.parseDouble(col[0]), Double.parseDouble(col[1]), Double.parseDouble(col[2]));
                allBranches.add(root);
            } else {
                // Kolejne linijki to zwykłe gałęzie
                Branch child = new Branch(Double.parseDouble(col[0]), Double.parseDouble(col[1]), Double.parseDouble(col[2]));
                
                int parentRow = Integer.parseInt(col[3]); // np. 1
                double t = Double.parseDouble(col[4]);
                
                // Magia łączenia: szukamy rodzica w naszej liście. 
                // Skoro parentRow jest indeksowane od 1 (np. pień to wiersz 1), to w naszej liście (indeksowanej od 0) ma indeks parentRow - 1.
                Branch parent = allBranches.get(parentRow - 1);
                
                // Wywołujemy naszą funkcję grow!
                parent.grow(t, child);
                
                allBranches.add(child);
            }
        }
    } catch (IOException e) {
        System.out.println("Something went wrong");
    }
    return root; // Zwracamy połączone drzewo
}

public boolean isYoung() {
    // Jeśli nie mam dzieci, to jestem młody (zgodnie z poleceniem)
    if (this.children.isEmpty()) {
        return true;
    }
    // Jeśli mam dzieci, sprawdzam czy którekolwiek z nich ma swoje własne dzieci
    for (Branch child : this.children) {
        if (!child.children.isEmpty()) {
            return false; // Znalazłem wnuka, więc nie jestem już młody!
        }
    }
    return true; // Przeszukałem wszystkie dzieci, żadne nie ma potomków
}

protected List<Branch> flatten() {
    List<Branch> result = new ArrayList<>();
    result.add(this); // 1. Dodaję samego siebie

    // 2. Proszę każde dziecko, by spłaszczyło swoje gałęzie
    for (Branch child : this.children) {
        result.addAll(child.flatten()); // Magia rekurencji!
    }
    return result;
}

@Override
public List<Branch> flatten() {
    return super.flatten();
}

public Vec2 absolutePosition() {
    // Pień nie ma rodzica, więc jego punkt startowy to (0,0)
    if (this.parent == null) {
        return new Vec2(0, 0); 
    }
    // Gdzie w przestrzeni zaczyna się mój rodzic?
    Vec2 parentPos = this.parent.absolutePosition();
    
    // W którym miejscu na ciele rodzica wyrastam? (t * kierunek rodzica)
    Vec2 offset = this.parent.getDirection().mul(this.t);
    
    // Moja pozycja to start rodzica + moje przesunięcie na nim
    return parentPos.add(offset);
}

// Zmieniamy deklarację klasy, żeby implementowała interfejs Comparable (zgodnie z poleceniem z kroku 12)
public class Tree extends Branch implements Comparable<Tree> {
    
    public double height() {
        double maxHeight = 0;
        // Używamy spłaszczonej listy do przejrzenia każdej gałązki na drzewie
        for (Branch b : this.flatten()) {
            // Y czubka gałęzi to Y jej startu + Y jej kierunku
            double tipY = b.absolutePosition().y + b.getDirection().y;
            if (tipY > maxHeight) {
                maxHeight = tipY;
            }
        }
        return maxHeight;
    }

    // Wymagane przez implementację interfejsu Comparable - pozwala potem użyć Collections.sort(listaDrzew)
    @Override
    public int compareTo(Tree other) {
        return Double.compare(this.height(), other.height());
    }
}

import java.util.function.Predicate;

// Zmodyfikowana metoda toSvgFile
public void toSvgFile(String path, int width, int height, Predicate<Branch> predicate) {
    try (FileWriter writer = new FileWriter(path)) {
        // ... (tu wpisujesz nagłówki SVG ze swojego kodu)
        
        // Pętla po spłaszczonym drzewie, żeby narysować KAŻDĄ gałąź (Krok 13)
        for (Branch b : this.flatten()) {
            Vec2 start = b.absolutePosition();
            Vec2 end = start.add(b.getDirection()); // Koniec to start + wektor
            
            // Krok 14: Jeżeli predykat zwraca true, kolor to zielony, inaczej brązowy
            String color = predicate.test(b) ? "green" : "brown";
            
            String line = String.format(Locale.ROOT, 
                "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" stroke=\"%s\" stroke-width=\"%f\" />\n",
                start.x, start.y, end.x, end.y, color, b.getThickness());
            writer.write(line);
        }
        // ... (zamknięcie tagów)

// Krok 14 - Przekazanie metody isYoung jako predykatu
myTree.toSvgFile("drzewo_mlode.svg", 500, 500, b -> b.isYoung());

// Krok 15 - Przekazanie własnej lambdy. 
// "gałęzie, których końce znajdują się na lewo od miejsca, z którego wyrasta drzewo"
// Miejsce z którego wyrasta drzewo to oś x=0. Więc szukamy końców, gdzie X < 0.
myTree.toSvgFile("drzewo_lewe.svg", 500, 500, b -> {
    Vec2 end = b.absolutePosition().add(b.getDirection());
    return end.x < 0; // Zwróci true (więc zarysuje na zielono), jeśli jest na lewo
});
