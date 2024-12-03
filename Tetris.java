import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tetris extends JFrame {

    private final int WIDTH = 10;  // Ширина поля
    private final int HEIGHT = 20; // Висота поля
    private final int CELL_SIZE = 30; // Розмір клітинки
    private final int TIMER_DELAY = 500; // Затримка таймера

    private GamePanel gamePanel;
    private Timer timer;

    public Tetris() {
        setTitle("Tetris");
        setSize(WIDTH * CELL_SIZE + 15, HEIGHT * CELL_SIZE + 40);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel(WIDTH, HEIGHT, CELL_SIZE);
        add(gamePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gamePanel.handleKeyPress(e.getKeyCode());
            }
        });

        // Таймер для оновлення гри
        timer = new Timer(TIMER_DELAY, e -> {
            if (!gamePanel.updateGame()) {
                timer.stop(); // Зупиняємо гру
                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Гра завершена! Ваш рахунок: " + gamePanel.getScore() + "\nРозпочати нову гру?",
                        "Кінець гри",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    restartGame(); // Перезапустити гру
                } else {
                    System.exit(0);
                }
            }
            gamePanel.repaint();
        });

        timer.start();
    }

    // Метод для перезапуску гри
    private void restartGame() {
        remove(gamePanel);
        gamePanel = new GamePanel(WIDTH, HEIGHT, CELL_SIZE);
        add(gamePanel);
        revalidate();
        repaint();
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }
}
