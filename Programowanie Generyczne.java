// Klasa generyczna - "T" to placeholder na dowolny typ obiektowy (np. String, Integer)
public class Box<T> {
    private T item;

    public void put(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }
}

// Użycie:
// Box<String> stringBox = new Box<>();
// stringBox.put("Jakaś wiadomość");
// String msg = stringBox.get();
//
// Box<Integer> intBox = new Box<>();
// intBox.put(100);
