import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SwingTemplate extends JPanel {
    // 1. ZMIENNE STANU
    private int x = 250;
    private int y = 250;

    public SwingTemplate() {
        // Ustawienia panelu
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        
        // 2. OBSŁUGA KLAWIATURY (KeyListener)
        setFocusable(true); // Musi być true, żeby panel "słuchał" klawiszy
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> x -= 10;
                    case KeyEvent.VK_RIGHT -> x += 10;
                    case KeyEvent.VK_UP -> y -= 10;
                    case KeyEvent.VK_DOWN -> y += 10;
                }
                // Po każdej zmianie współrzędnych musimy odświeżyć ekran
                repaint(); 
            }
        });
    }

    // 3. RYSOWANIE (Odpowiednik metody draw/render)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Czyści ekran
        Graphics2D g2d = (Graphics2D) g;

        // Tutaj rysujemy kształty
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x, y, 50, 50); // Rysuje kółko w pozycji x, y
        
        // Wymóg z zadania (np. napisanie pozycji w oknie)
        g2d.setColor(Color.BLACK);
        g2d.drawString("Pozycja: " + x + ", " + y, 10, 20);
    }

    // 4. MAIN (Uruchomienie okna)
    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing App Template");
        SwingTemplate panel = new SwingTemplate();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack(); // Dopasowuje rozmiar okna do panelu
        frame.setLocationRelativeTo(null); // Centruje okno na ekranie
        frame.setVisible(true);
    }
}
