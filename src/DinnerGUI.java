package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.List;

public class DinnerGUI extends JPanel {
    private final int NUM_PHILOSOPHERS = 5;
    private final int TABLE_RADIUS = 150;
    private final int PHILOSOPHER_RADIUS = 20;
    private final int PHILOSOPHER_GAP_ANGLE = 360 / NUM_PHILOSOPHERS;
    private final int UPDATE_INTERVAL = 100; // 1 second
    private volatile List<Philosopher> philosophers;
    private Timer timer;
    private Symposium dinner = new Symposium();

    public DinnerGUI() {
        this.philosophers = dinner.getPhilosophers();
        this.timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                repaint(); // Repaint the panel every second
            }
        });
        this.timer.start(); // Start the timer
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Set anti-aliasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate table center
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Draw table
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(centerX - TABLE_RADIUS, centerY - TABLE_RADIUS, 2 * TABLE_RADIUS, 2 * TABLE_RADIUS);

        // Draw philosophers and chopsticks (Chat GPT did this math)
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Philosopher philosopher = philosophers.get(i);
            TSChopstick leftChopstick = philosopher.getLeftChop();

            // Calculate philosopher position
            int phiX = (int) (centerX + TABLE_RADIUS * Math.cos(Math.toRadians(i * PHILOSOPHER_GAP_ANGLE)));
            int phiY = (int) (centerY + TABLE_RADIUS * Math.sin(Math.toRadians(i * PHILOSOPHER_GAP_ANGLE)));

            // Calculate chopstick positions between current and next philosopher
            int nextPhiX = (int) (centerX + TABLE_RADIUS * Math.cos(Math.toRadians((i + 1) * PHILOSOPHER_GAP_ANGLE)));
            int nextPhiY = (int) (centerY + TABLE_RADIUS * Math.sin(Math.toRadians((i + 1) * PHILOSOPHER_GAP_ANGLE)));

            int chopX1Left = (phiX + nextPhiX) / 2;
            int chopY1Left = (phiY + nextPhiY) / 2;

            int chopX2Left = (int) (centerX + (TABLE_RADIUS + PHILOSOPHER_RADIUS) * Math.cos(Math.toRadians((i + 0.5) * PHILOSOPHER_GAP_ANGLE)));
            int chopY2Left = (int) (centerY + (TABLE_RADIUS + PHILOSOPHER_RADIUS) * Math.sin(Math.toRadians((i + 0.5) * PHILOSOPHER_GAP_ANGLE)));

            // Draw philosopher
            if (dinner.checkWhosEating().contains(philosopher)) {
                drawPhilosopher(g2d, phiX, phiY, philosopher.getName(), Color.GREEN);
            } else {
                drawPhilosopher(g2d, phiX, phiY, philosopher.getName(), Color.GRAY);
            }

            // Draw chopsticks if they are not being used
            if (!dinner.checkUsedChopsticks().contains(leftChopstick)) {
                drawChopstick(g2d, chopX1Left, chopY1Left, chopX2Left, chopY2Left);
            }
        }
    }

    private void drawChopstick(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2)); // Set chopstick thickness
        g2d.draw(new Line2D.Float(x1, y1, x2, y2));
    }

    private void drawPhilosopher(Graphics2D g2d, int x, int y, String name, Color color) {
        g2d.setColor(color);
        g2d.fillOval(x - PHILOSOPHER_RADIUS, y - PHILOSOPHER_RADIUS, 2 * PHILOSOPHER_RADIUS, 2 * PHILOSOPHER_RADIUS);
        g2d.setColor(Color.BLACK);
        g2d.drawString(name, x - PHILOSOPHER_RADIUS / 2, y + PHILOSOPHER_RADIUS + 10);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create GUI and frame
            DinnerGUI panel = new DinnerGUI();
            panel.dinner.invitePhilosophers();

            JFrame frame = new JFrame("Dinner Table");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.getContentPane().add(panel);
            frame.setVisible(true);
        });
    }
}
