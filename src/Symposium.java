import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Symposium {
    private final long DINNER_TIME = 20 * 1000;
    private List<Philosopher> phils;
    private List<TSChopstick> chopsticks;
    private List<Thread> philThreads;
    private final int NUM_PHILOSOPHERS = 5;
    private final String[] philosopherNames = { "Hank", "Aidan", "Nate", "Ryan", "Dustin"};
    private final String[] chopstickNames = { "First", "Second", "Third", "Fourth", "Fifth"};

    public Symposium(){
        makeChopsticks();
        makePhilosophers();
    }

    private void makeChopsticks() {
        chopsticks = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < NUM_PHILOSOPHERS; i++){
            TSChopstick chopstick = new TSChopstick(chopstickNames[i]);
            chopsticks.add(chopstick);
        }
    }

    private void makePhilosophers() {
        phils = Collections.synchronizedList(new ArrayList<>());
        philThreads = Collections.synchronizedList(new ArrayList<>());
        int index = 1;
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            if (index > NUM_PHILOSOPHERS - 1) {
                index = 0;
            }
            Philosopher philosopher = new Philosopher(philosopherNames[i], chopsticks.get(i), chopsticks.get(index));
            Thread philosopherThread = new Thread(philosopher);
            phils.add(philosopher);
            philThreads.add(philosopherThread);
            index++;

        }
    }

    public synchronized void invitePhilosophers() {
        for (Thread p : philThreads){
            p.start(); // Start the thread
        }
    }

    public List<Philosopher> checkWhosEating(){
        List<Philosopher> eaters = new ArrayList<>();
        for(Philosopher p: getPhilosophers()){
            if(p.isEating()){
                eaters.add(p);
            }
        }
        return eaters;
    }

    public List<TSChopstick> checkUsedChopsticks(){
        List<TSChopstick> used = new ArrayList<>();
        for(TSChopstick c: getChopsticks()){
            if(c.isBeingUsed()){
                used.add(c);
            }
        }
        return used;
    }

    private void letThemEat() {
        long sleepTime = Math.max(1, DINNER_TIME);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println("Error");
        }
    }

    private void endDinner() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++){
            phils.get(i).leaveDinner();
            try {
                philThreads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<TSChopstick> getChopsticks(){
        return chopsticks;
    }

    public List<Philosopher> getPhilosophers() {
        return phils;
    }

    public static void main(String[] args) {
        Symposium s = new Symposium();
        s.makeChopsticks();
        s.invitePhilosophers();
        s.letThemEat();
        s.endDinner();

        for (Philosopher p : s.phils){
            System.out.println(p.getName() + " ate " + p.servings + " times");

        }
    }
}
