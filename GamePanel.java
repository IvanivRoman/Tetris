import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private final int width;
    private final int height;
    private final int cellSize;

    private int[][] field;
    private Tetrimino currentPiece;
    private int score;

    public GamePanel(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.field = new int[height][width];
        this.score = 0;
        spawnPiece();
    }

    public void handleKeyPress(int keyCode) {
        if (currentPiece == null) return;

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                currentPiece.move(-1, 0, field);
                break;
            case KeyEvent.VK_RIGHT:
                currentPiece.move(1, 0, field);
                break;
            case KeyEvent.VK_DOWN:
                currentPiece.move(0, 1, field);
                break;
            case KeyEvent.VK_UP:
                currentPiece.rotate(field);
                break;
        }
        repaint();
    }

    public boolean updateGame() {
        if (currentPiece != null) {
            if (!currentPiece.move(0, 1, field)) {
                currentPiece.mergeToField(field);
                clearLines();
                spawnPiece();

                // Перевірка завершення гри
                if (!currentPiece.canPlace(field)) {
                    currentPiece = null; // Зупиняємо гру
                    return false;
                }
            }
        }
        return true;
    }

    private void spawnPiece() {
        currentPiece = new Tetrimino(width / 2 - 1, 0);
    }

    private void clearLines() {
        for (int y = 0; y < height; y++) {
            boolean fullLine = true;
            for (int x = 0; x < width; x++) {
                if (field[y][x] == 0) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int yy = y; yy > 0; yy--) {
                    System.arraycopy(field[yy - 1], 0, field[yy], 0, width);
                }
                score += 100;
            }
        }
    }

    public int getScore() {
        return score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Очищення фону
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Малювання поля
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x] != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }

        // Малювання поточної фігури
        if (currentPiece != null) {
            currentPiece.draw(g, cellSize);
        }

        // Малювання рахунку
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
    }
}
