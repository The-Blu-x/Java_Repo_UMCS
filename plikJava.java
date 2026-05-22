/*
ENKAPSULACJA I KONSTRUKTORY (Encapsulation and Constructors)

Enkapsulacja (Ukrywanie danych): Polega na ukrywaniu pól klasy przed bezpośrednim
dostępem z zewnątrz i udostępnianiu ich wyłącznie przez metody (gettery i settery).

Modyfikatory dostępu:

    private: Dostępne tylko wewnątrz tej samej klasy.

    default (brak): Dostępne tylko w tym samym pakiecie.

    protected: Dostępne w tym samym pakiecie oraz w klasach dziedziczących.

    public: Dostępne z każdej innej klasy.

Konstruktor: Specjalna metoda wywoływana podczas tworzenia obiektu (ma taką samą
nazwę jak klasa, brak określonego typu zwracanego). Służy do inicjalizacji obiektu.

Przykładowy kod:
Java
*/
public class KontoBankowe {
    // Enkapsulacja: pola są prywatne
    private String numerKonta;
    private double saldo;

    // Konstruktor parametryczny
    public KontoBankowe(String numerKonta, double saldoPoczatkowe) {
        this.numerKonta = numerKonta;
        if (saldoPoczatkowe >= 0) {
            this.saldo = saldoPoczatkowe;
        }
    }

    // Konstruktor domyślny (bezargumentowy)
    public KontoBankowe() {
        this.numerKonta = "Nieznany";
        this.saldo = 0.0;
    }

    // Getter (pobieranie wartości)
    public double getSaldo() {
        return this.saldo;
    }

    // Setter (modyfikacja wartości z walidacją)
    public void wplacGotowke(double kwota) {
        if (kwota > 0) {
            this.saldo += kwota;
        }
    }
}
/*
    DZIEDZICZENIE (Inheritance)

Mechanizm pozwalający klasie podrzędnej przejąć pola i metody klasy nadrzędnej.
Służy do tego słowo kluczowe 'extends'.

    Java obsługuje tylko pojedyncze dziedziczenie klas.

    Słowo 'super' odnosi się do klasy nadrzędnej.

Przykładowy kod:
Java
*/
// Klasa nadrzędna (Rodzic)
public class Pojazd {
    protected String marka;

    public Pojazd(String marka) {
        this.marka = marka;
    }

    public void trab() {
        System.out.println("Beep beep!");
    }
}

// Klasa podrzędna (Dziecko)
public class Samochod extends Pojazd {
    private int iloscDrzwi;

    public Samochod(String marka, int iloscDrzwi) {
        super(marka); // Wywołanie konstruktora rodzica (Musi być pierwsze!)
        this.iloscDrzwi = iloscDrzwi;
    }

    // Polimorfizm i nadpisywanie metod
    @Override
    public void trab() {
        System.out.println("Samochód " + marka + " robi: BRRRUUM!");
    }
}
/*
    ABSTRAKCJA (Abstraction)

Ukrywanie szczegółów implementacji, a pokazywanie tylko istotnych cech i funkcji.

Klasa Abstrakcyjna ('abstract'):

    Nie można utworzyć jej obiektu bezpośrednio.

    Może zawierać metody z ciałem i metody abstrakcyjne (bez ciała).

Interfejs ('interface'):

    Szablon zachowań. Klasa może implementować WIELE interfejsów ('implements').

    Od Java 8 mogą mieć metody domyślne ('default').

Przykładowy kod:
Java
*/
// Klasa abstrakcyjna
public abstract class Zwierze {
    public abstract void wydajDzwiek(); // Metoda abstrakcyjna
}

// Interfejs
public interface Domowe {
    void badzMilutki(); // Metoda abstrakcyjna
    
    default void przywitaj() { // Metoda domyślna
        System.out.println("Mruczy...");
    }
}

// Klasa implementująca i dziedzicząca
public class Kot extends Zwierze implements Domowe {
    @Override
    public void wydajDzwiek() { System.out.println("Miau!"); }

    @Override
    public void badzMilutki() { System.out.println("Kot się łasi."); }
}
/*
    KONTENERY / KOLEKCJE (Collections)

A. LISTY (ArrayList, LinkedList) - kolejność, pozwalają na duplikaty.
Java
*/
List<String> lista = new ArrayList<>();
lista.add("Java");
String pierwszy = lista.get(0);
/*
B. ZBIORY (HashSet, TreeSet) - brak duplikatów.
Java
*/
Set<Integer> zbior = new HashSet<>();
zbior.add(10);
zbior.add(10); // Ignorowane
/*
C. MAPY (HashMap, TreeMap) - pary Klucz-Wartość.
Java
*/
Map<String, Integer> oceny = new HashMap<>();
oceny.put("Mat", 5);
int ocena = oceny.get("Mat");
/*
    PLIKI I WYJĄTKI (Files and Exceptions)

Wyjątki: Checked (wymagają obsłużenia) i Unchecked (RuntimeExceptions).

Przykładowy kod:
Java
*/
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class Pliki {
    public static void zapisz(String sciezka, String tekst) {
        // Try-with-resources automatycznie zamyka plik
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sciezka))) {
            writer.write(tekst);
        } catch (IOException e) {
            System.err.println("Błąd zapisu: " + e.getMessage());
        } finally {
            System.out.println("Zawsze się wykona.");
        }
    }
}
/*
    PROGRAMOWANIE FUNKCYJNE (Functional Programming)

Opiera się na lambdach i Stream API (od Java 8).

Przykładowy kod:
Java
*/
List<String> imiona = Arrays.asList("Ania", "Bartek", "Andrzej");

List<String> wynik = imiona.stream()
    .filter(imie -> imie.startsWith("A")) // Lambda
    .map(String::toUpperCase)             // Referencja do metody
    .sorted()
    .collect(Collectors.toList());
/*
    PROGRAMOWANIE GENERYCZNE (Generics)

Zapewnia bezpieczeństwo typów.

Przykładowy kod:
Java
*/
public class Pudeleko<T> {
    private T zawartosc;
    public void wloz(T cos) { this.zawartosc = cos; }
    public T wyjmij() { return this.zawartosc; }
}
// Użycie: Pudeleko<String> p = new Pudeleko<>();
/*
    PRZYDATNE GOTOWCE (Pętle, Sortowanie)

Java
*/
// Pętla for-each
for (String element : lista) { System.out.println(element); }

// Sortowanie tablicy
int[] liczby = {5, 2, 9};
Arrays.sort(liczby); 

// Sortowanie z komparatorem
List<Osoba> ludzie = new ArrayList<>();
ludzie.sort(Comparator.comparingInt(Osoba::getWiek));
/*
\n\n\n

                  JAVA CHEAT SHEET - ENGLISH TRANSLATION

================================================================================

    CLASSES AND OBJECTS

A class is a blueprint (template) used to create objects.
An object is a specific instance of a class, having state (fields) and behavior (methods).

Example code:
Java
*/
// Class definition
public class Person {
    // Class fields (state)
    String name;
    int age;

    // Class method (behavior)
    void introduceYourself() {
        // The 'this' keyword refers to the current object
        System.out.println("Hi, I am " + this.name + " and I am " + this.age + " years old.");
    }
}

// Usage (e.g., in the main method):
Person p = new Person(); // Creating an object using 'new'
p.name = "John";         // Assigning a value to a field
p.age = 25;
p.introduceYourself();   // Calling a method -> "Hi, I am John and I am 25 years old."
/*
    ENCAPSULATION AND CONSTRUCTORS

Encapsulation (Data Hiding): Hiding class fields from direct outside access
and exposing them only through methods (getters and setters).

Access modifiers:

    private: Accessible only within the same class.

    default (none): Accessible only within the same package.

    protected: Accessible in the same package and in subclasses.

    public: Accessible from any other class.

Constructor: A special method called when creating an object (same name as the class,
no return type). Used to initialize the object.

Example code:
Java
*/
public class BankAccount {
    // Encapsulation: fields are private
    private String accountNumber;
    private double balance;

    // Parameterized constructor
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        }
    }

    // Default constructor (no-args)
    public BankAccount() {
        this.accountNumber = "Unknown";
        this.balance = 0.0;
    }

    // Getter (retrieving value)
    public double getBalance() {
        return this.balance;
    }

    // Setter (modifying value with validation)
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
}
/*
    INHERITANCE

A mechanism allowing a subclass (child) to inherit fields and methods from a superclass (parent).
The 'extends' keyword is used.

    Java supports only single class inheritance.

    The 'super' keyword refers to the superclass.

Example code:
Java
*/
// Superclass (Parent)
public class Vehicle {
    protected String brand;

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public void honk() {
        System.out.println("Beep beep!");
    }
}

// Subclass (Child)
public class Car extends Vehicle {
    private int doorsCount;

    public Car(String brand, int doorsCount) {
        super(brand); // Calling parent constructor (Must be first!)
        this.doorsCount = doorsCount;
    }

    // Polymorphism and Method Overriding
    @Override
    public void honk() {
        System.out.println("Car " + brand + " goes: VROOM!");
    }
}
/*
    ABSTRACTION

Hiding implementation details and showing only essential features and functions.

Abstract Class ('abstract'):

    Cannot be instantiated directly.

    Can contain both regular methods (with a body) and abstract methods (no body).

Interface ('interface'):

    A behavioral blueprint. A class can implement MULTIPLE interfaces ('implements').

    Since Java 8, they can have 'default' methods.

Example code:
Java
*/
// Abstract class
public abstract class Animal {
    public abstract void makeSound(); // Abstract method
}

// Interface
public interface Pet {
    void beCute(); // Abstract method
    
    default void greet() { // Default method
        System.out.println("Purring...");
    }
}

// Implementing and inheriting class
public class Cat extends Animal implements Pet {
    @Override
    public void makeSound() { System.out.println("Meow!"); }

    @Override
    public void beCute() { System.out.println("Cat is cuddling."); }
}
/*
    CONTAINERS / COLLECTIONS

A. LISTS (ArrayList, LinkedList) - ordered, allow duplicates.
Java

List<String> list = new ArrayList<>();
list.add("Java");
String first = list.get(0);

B. SETS (HashSet, TreeSet) - no duplicates allowed.
Java

Set<Integer> set = new HashSet<>();
set.add(10);
set.add(10); // Ignored

C. MAPS (HashMap, TreeMap) - Key-Value pairs.
Java

Map<String, Integer> grades = new HashMap<>();
grades.put("Math", 5);
int grade = grades.get("Math");

    FILES AND EXCEPTIONS

Exceptions: Checked (must be handled) and Unchecked (RuntimeExceptions).

Example code:
Java
*/
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class FileHandler {
    public static void save(String path, String text) {
        // Try-with-resources automatically closes the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        } finally {
            System.out.println("This will always execute.");
        }
    }
}
/*
    FUNCTIONAL PROGRAMMING

Based on lambdas and Stream API (since Java 8).

Example code:
Java
*/
List<String> names = Arrays.asList("Anna", "Bob", "Alice");

List<String> result = names.stream()
    .filter(name -> name.startsWith("A")) // Lambda
    .map(String::toUpperCase)             // Method reference
    .sorted()
    .collect(Collectors.toList());
/*
    GENERICS

Provides compile-time type safety.

Example code:
Java
*/
public class Box<T> {
    private T content;
    public void put(T item) { this.content = item; }
    public T get() { return this.content; }
}
// Usage: Box<String> b = new Box<>();
/*
    USEFUL SNIPPETS (Loops, Sorting)

Java
*/
// For-each loop
for (String item : list) { System.out.println(item); }

// Sorting an array
int[] numbers = {5, 2, 9};
Arrays.sort(numbers); 

// Sorting with a comparator
List<Person> people = new ArrayList<>();
people.sort(Comparator.comparingInt(Person::getAge));
