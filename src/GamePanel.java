import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener {
    final int screenWidth = 600;
    final int screenHeight = 600;
    final int unit = 25;
    final int totalUnits = screenWidth * screenHeight / unit;
    final int Delay = 70;
    boolean gameRunning = true;
    final int x[] = new int[totalUnits];
    final int y[] = new int[totalUnits];
    char direction = 'R';
    int bodyParts = 10;
    int foodX;
    int foodY;
    int score;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeys());
        startGame();
    }

    public void startGame() {
        newFood();
        gameRunning = true;
        timer = new Timer(Delay, this);
        timer.start();

    }

    void draw(Graphics g) {
        if (gameRunning) {


//            for (int i = 0; i < screenWidth / unit; i++) {
//                g.drawLine(i * unit, 0, i * unit, screenHeight);
//                g.drawLine(0, i * unit, screenWidth, i * unit);
//
//            }
            g.setColor(Color.white);
            g.fillOval(foodX, foodY, unit, unit);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillOval(x[i], y[i], unit, unit);
                } else {
                    g.setColor(new Color(150, 13, 250));
                    g.fillRect(x[i], y[i], unit, unit);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("Score : " + score, 220, 30);
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("Game Over", 200, 250);
            g.drawString("Made by Fahad", 200, 285);

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void newFood() {
        foodX = random.nextInt(screenWidth / unit) * unit;
        foodY = random.nextInt(screenHeight / unit) * unit;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'R':
                x[0] = x[0] + unit;
                break;
            case 'L':
                x[0] = x[0] - unit;
                break;
            case 'U':
                y[0] = y[0] - unit;
                break;
            case 'D':
                y[0] = y[0] + unit;
                break;
        }
    }

    public void eatFood() {
        if (x[0] == foodX && y[0] == foodY) {
            bodyParts++;
            score++;
            newFood();
        }
    }

    public void checkCollisions() {
        for (int i = 1; i < bodyParts; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                gameRunning = false;
            }
        }
        if (x[0] < 0 || x[0] > screenWidth || y[0] < 0 || y[0] > screenHeight) {
            gameRunning = false;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            move();
            checkCollisions();
            eatFood();
        }

        repaint();

    }

    public class MyKeys extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    ;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    ;
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    ;
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    ;
                    break;
            }
        }
    }
}
