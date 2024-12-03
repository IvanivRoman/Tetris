import java.awt.*;
import java.util.Random;

public class Tetrimino {
    private final int[][][] SHAPES = {
            {{1, 1, 1, 1}}, // I
            {{1, 1}, {1, 1}}, // O
            {{0, 1, 0}, {1, 1, 1}}, // T
            {{1, 1, 0}, {0, 1, 1}}, // S
            {{0, 1, 1}, {1, 1, 0}}, // Z
            {{1, 1, 1}, {1, 0, 0}}, // L
            {{1, 1, 1}, {0, 0, 1}}  // J
    };

    private int[][] shape;
    private int x, y;

    public Tetrimino(int startX, int startY) {
        Random random = new Random();
        shape = SHAPES[random.nextInt(SHAPES.length)];
        x = startX;
        y = startY;
    }

    public boolean canPlace(int[][] field) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    int newX = x + col;
                    int newY = y + row;
                    if (newX < 0 || newX >= field[0].length || newY >= field.length || field[newY][newX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean move(int dx, int dy, int[][] field) {
        x += dx;
        y += dy;
        if (!canPlace(field)) {
            x -= dx;
            y -= dy;
            return false;
        }
        return true;
    }

    public void rotate(int[][] field) {
        int[][] rotated = new int[shape[0].length][shape.length];
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                rotated[col][shape.length - 1 - row] = shape[row][col];
            }
        }

        int[][] oldShape = shape;
        shape = rotated;
        if (!canPlace(field)) {
            shape = oldShape;
        }
    }

    public void mergeToField(int[][] field) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    field[y + row][x + col] = 1;
                }
            }
        }
    }

    public void draw(Graphics g, int cellSize) {
        g.setColor(Color.RED);
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    g.fillRect((x + col) * cellSize, (y + row) * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect((x + col) * cellSize, (y + row) * cellSize, cellSize, cellSize);
                    g.setColor(Color.RED);
                }
            }
        }
    }
}
