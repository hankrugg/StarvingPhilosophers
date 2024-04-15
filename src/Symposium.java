package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aidan Scott and Hank Rugg
 * Symposium contains the logic to create chopsticks and philosophers
 */
public class Symposium {
    private final int NUM_PHILOSOPHERS; // the number of philosophers and chopsticks
    private List<Philosopher> phils; // List of philosophers
    private List<TSChopstick> chopsticks;

    /**
     * Symposium makes the chopsticks then the philosophers
     */
    public Symposium(int num_philosophers) {
        NUM_PHILOSOPHERS = num_philosophers;
        makeChopsticks();
        makePhilosophers();
    }

    /**
     * MakeChopsticks creates a list and fills it with chopsticks of quantity of number of philosophers
     */
    private void makeChopsticks() {
        chopsticks = Collections.synchronizedList(new ArrayList<>()); // create list
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            TSChopstick chopstick = new TSChopstick("Chopstick " + i); // create chopstick of name i
            chopsticks.add(chopstick);
        }
    }

    /**
     * makePhilosophers creates a list and fills it with philosophers with corresponding chopsticks
     * Each philosopher has two specific chopsticks for their left and right hands.
     * Each philosopher will share their chopsticks with the philosophers that sit beside them ( 0 1 2 ) 0 and 2 beside 1
     */
    private void makePhilosophers() {
        phils = Collections.synchronizedList(new ArrayList<>()); // create list
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            // Create philosopher with specific chopsticks and adds them to the list
            phils.add(new Philosopher("Phil " + i, chopsticks.get(i), chopsticks.get((i + 1) % (NUM_PHILOSOPHERS - 1))));
        }
    }

    /**
     * invitePhilosophers stars the philosophers eating (philosophizing)
     */
    public synchronized void invitePhilosophers() {
        for (Philosopher p : phils) {
            p.startPhil(); // Start the thread
        }
    }

    /**
     * checkWhosEating returns list of all the currently eating philosophers
     *
     * @return eaters ArrayList<Philospher>
     */
    public List<Philosopher> checkWhosEating() {
        List<Philosopher> eaters = new ArrayList<>();
        for (Philosopher p : getPhilosophers()) {
            if (p.isEating()) {
                eaters.add(p); // if philosopher is eating, add them to the list
            }
        }
        return eaters;
    }

    /**
     * checkUsedChopsticks returns all currently used chopsticks
     *
     * @return usedChopsticks ArrayList<TSChopstick>
     */
    public List<TSChopstick> checkUsedChopsticks() {
        List<TSChopstick> used = new ArrayList<>();
        for (TSChopstick c : getChopsticks()) {
            if (c.isUsed()) {
                used.add(c); // if chopstick is being used, then add it to the list
            }
        }
        return used;
    }

    /**
     * getChopsticks returns all chopsticks
     *
     * @return chopsticks List<TSChopsticks>
     */
    public List<TSChopstick> getChopsticks() {
        return chopsticks;
    }

    /**
     * getPhilosophers returns all philosophers
     *
     * @return philosophers List<Philosophers>
     */
    public List<Philosopher> getPhilosophers() {
        return phils;
    }

    /**
     * Stops all the philosopher threads
     */
    public void endDinner(){
        for(Philosopher phil: phils){
            phil.stopPhil();
        }
    }

    /**
     * @return number of philosophers int
     */
    public int getNumPhilosophers() {
        return NUM_PHILOSOPHERS;
    }
}
