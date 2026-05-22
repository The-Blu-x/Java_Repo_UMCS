// ABSTRAKCJA: Nie można stworzyć bezpośrednio obiektu tej klasy (new Animal())
public abstract class Animal {
    protected String species; // 'protected' pozwala na dostęp w klasach dziedziczących

    public Animal(String species) {
        this.species = species;
    }

    // Metoda abstrakcyjna - każda klasa dziedzicząca musi zdefiniować, jak to działa
    public abstract void makeSound();
    
    // Zwykła metoda - wspólna dla wszystkich zwierząt
    public void sleep() {
        System.out.println("Zzz...");
    }
}

// DZIEDZICZENIE: Klasa Dog rozszerza klasę Animal
public class Dog extends Animal {
    
    public Dog() {
        super("Pies"); // Wywołanie konstruktora klasy nadrzędnej (Animal)
    }

    // Nadpisanie metody abstrakcyjnej
    @Override
    public void makeSound() {
        System.out.println("Hau hau!");
    }
}
