package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Symposium {
    private static final int NUM_PHILOSOPHERS = 5;
    private List<Philosopher> phils;
    private List<TSChopstick> chopsticks;

    public Symposium() {
        makeChopsticks();
        makePhilosophers();
    }

    private void makeChopsticks() {
        chopsticks = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            TSChopstick chopstick = new TSChopstick("Chopstick " + i);
            chopsticks.add(chopstick);
        }
    }

    private void makePhilosophers() {
        phils = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Philosopher philosopher = new Philosopher("Phil " + i, chopsticks.get(i), chopsticks.get((i + 1) % (NUM_PHILOSOPHERS - 1)));
            phils.add(philosopher);
        }
    }

    public synchronized void invitePhilosophers() {
        for (Philosopher p : phils) {
            p.startPhil(); // Start the thread
        }
    }

    public List<Philosopher> checkWhosEating() {
        List<Philosopher> eaters = new ArrayList<>();
        for (Philosopher p : getPhilosophers()) {
            if (p.isEating()) {
                eaters.add(p);
            }
        }
        return eaters;
    }

    public List<TSChopstick> checkUsedChopsticks() {
        List<TSChopstick> used = new ArrayList<>();
        for (TSChopstick c : getChopsticks()) {
            if (c.isUsed()) {
                used.add(c);
            }
        }
        return used;
    }

    public List<TSChopstick> getChopsticks() {
        return chopsticks;
    }

    public List<Philosopher> getPhilosophers() {
        return phils;
    }

    public int getNumPhilosophers() {
        return NUM_PHILOSOPHERS;
    }
}
