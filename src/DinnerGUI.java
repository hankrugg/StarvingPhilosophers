package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.List;

/**
 * @author Hank Rugg and Aidan Scott
 * This is the main class that runs the Dinner GUI to display the philosophers while they are eating their dinner.
 * It uses a GUI to convey the information of which philosophers are eating and which are hungry.
 * The GUI will run for the amount of time signified in the DINNER_TIME variable while displaying who is eating and how
 * many times they have eaten.
 */
public class DinnerGUI extends JPanel {
    private final int DINNER_TIME;
    private final int TABLE_RADIUS = 150;
    private final int PHILOSOPHER_RADIUS = 20;
    private final List<Philosopher> philosophers;
    private final Symposium dinner;
    private final int NUM_PHILOSOPHERS ;
    private final int PHILOSOPHER_GAP_ANGLE ;

    /**
     * Gui constructor contains a timer that calls the repaint method when the timer is up.
     * This is used to maintain the correct display of eating or hungry philosophers and chopsticks.
     */
    public DinnerGUI(Symposium _dinner, int runtime) {
        dinner = _dinner;
        this.philosophers = dinner.getPhilosophers();
        NUM_PHILOSOPHERS = dinner.getNumPhilosophers();
        PHILOSOPHER_GAP_ANGLE = 360 / NUM_PHILOSOPHERS;
        DINNER_TIME = runtime * 1000;
        // Repaint the panel
        int UPDATE_INTERVAL = 100;
        Timer timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
            int count = 0; // Counter to track the number of timer ticks

            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                repaint(); // Repaint the panel every 100 milliseconds
                count += UPDATE_INTERVAL; // Increment the counter
                if (count >= DINNER_TIME) {
                    dinner.endDinner(); // Stops the dinner, stops all philosophers
                    JOptionPane.showMessageDialog(null, "Dinner ended.");
                    ((Timer) e.getSource()).stop(); // Stop the timer
                    System.out.println("Dinner ended, safe to close");
                    System.exit(0);
                }
            }
        });
        timer.start(); // Start the timer
    }


    /**
     * Main method that runs the dinner gui and all functions associated with it.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if(validateArgs(args)){
            SwingUtilities.invokeLater(() -> {
                int philNumber = Integer.parseInt(args[0]);
                int runtime = Integer.parseInt(args[1]);
                // Create GUI and frame
                Symposium dinner = new Symposium(philNumber);
                DinnerGUI panel = new DinnerGUI(dinner, runtime);
                panel.dinner.invitePhilosophers();


                JFrame frame = new JFrame("Dinner Table");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 500);
                frame.getContentPane().add(panel);
                frame.setVisible(true);

            });
        }else{
            System.err.println("Please pass in valid arguments. Two arguments for number of philosophers and runtime in seconds");
        }

    }

    /**
     * ValidateArgs makes sure that the input args are the correct length and able to be parsed into ints
     * @param args args passed in within the main function
     * @return true if valid and false if invalid
     */
    public static boolean validateArgs(String[] args){
        if (args.length == 2) {
            try{ // If args[0] cant be parsed, then return error
                Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                System.err.println("Invalid philosopher quantity, must be an integer");
                return false;
            }
            try{ // If args[1] cant be parsed, then return error
                Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                System.err.println("Invalid runtime, must be an integer");
                return false;
            }
            if(Integer.parseInt(args[0]) < 3){ // The dinner party must have more than 2 attendees
                System.err.println("2 isn't a party! You must have 3 or more philosophers attend the dinner.");
                return false;
            }
            if(Integer.parseInt(args[0]) > 10){ // The dinner party must have more than 2 attendees
                System.err.println("We didn't make enough rice for that many philosophers! You must have less than 10 philosophers attend the dinner.");
                return false;
            }
            if(Integer.parseInt(args[1]) > 30){ // The dinner party must have more than 2 attendees
                System.err.println("That's too long to eat, the philosophers get full after a 30 second dinner! Make the time less than 30 seconds.");
                return false;
            }
            if(Integer.parseInt(args[1]) < 3){ // The dinner party must have more than 2 attendees
                System.err.println("That's too short to eat, the philosophers need to eat for at least 3 second! Make the time more than 3 seconds.");
                return false;
            }
            return true;
        }else{ // if there are too few or too many arguments passed, then tell user what they need to do.
            System.err.println("Invalid number of arguments, you must pass 2 arguments; number of philosophers and runtime in seconds");
            return false;
        }
    }

    /**
     * Paints the current state of eating and hungry philosophers. The philosopher will be green if it is eating and
     * grey otherwise. Each chopstick will be displayed if it is not being used, otherwise it will not be painted.
     *
     * @param g the Graphics object to protect
     */
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
                drawPhilosopher(g2d, phiX, phiY, philosopher.getName(), philosopher.getServings(), Color.GREEN);
            } else {
                drawPhilosopher(g2d, phiX, phiY, philosopher.getName(), philosopher.getServings(), Color.GRAY);
            }

            // Draw chopsticks if they are not being used
            if (!dinner.checkUsedChopsticks().contains(leftChopstick)) {
                drawChopstick(g2d, chopX1Left, chopY1Left, chopX2Left, chopY2Left);
            }
        }
    }

    /**
     * Draw chopstick for each philosopher
     *
     * @param g2d 2Dimensional graphics object to draw
     * @param x1  x1 of chopstick
     * @param y1  y1 of chopstick
     * @param x2  x2 of chopstick
     * @param y2  y2 of chopstick
     */
    private void drawChopstick(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2)); // Set chopstick thickness
        g2d.draw(new Line2D.Float(x1, y1, x2, y2));
    }

    /**
     * Draw each philosopher
     *
     * @param g2d      2Dimensional graphics object to draw
     * @param x        x coordinate of philosopher
     * @param y        y coordinate of philosopher
     * @param name     name of philosopher
     * @param servings amount of times the philosopher has eaten
     * @param color    color of the philosopher, green if eating and grey if thinking/hungry
     */
    private void drawPhilosopher(Graphics2D g2d, int x, int y, String name, int servings, Color color) {
        g2d.setColor(color);
        g2d.fillOval(x - PHILOSOPHER_RADIUS, y - PHILOSOPHER_RADIUS, 2 * PHILOSOPHER_RADIUS, 2 * PHILOSOPHER_RADIUS);
        g2d.setColor(Color.BLACK);
        g2d.drawString(name + " ate " + servings + " times", x - PHILOSOPHER_RADIUS / 2 - 30, y + PHILOSOPHER_RADIUS + 10);
    }
}
