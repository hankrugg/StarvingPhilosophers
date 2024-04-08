import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Aidan Scott
 * Philosopher is class that is able to start and create and manage many workers which all do a different task in the
 * Orange processing pipeline
 */
public class Philosopher implements Runnable {
    private volatile boolean attending = true;
    private volatile boolean isEating = false;
    private final String name;
    private final TSChopstick rightChop;
    private final TSChopstick leftChop;
    private final int TIME_IT_TAKES_TO_EAT = 500;
    public volatile int servings;

    Philosopher(String name, TSChopstick rightChop, TSChopstick leftChop) {
        this.name = name;
        this.rightChop = rightChop;
        this.leftChop = leftChop;
    }
    @Override
    public void run() {
    // the philosophers must feed
        while (attending){
            try {
                tryToEat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private synchronized boolean tryToEat() throws InterruptedException {
            if (rightChop.acquire(name)){
                if (leftChop.acquire(name)){
                    isEating = true;
                    System.out.println(name + " is eating with " + rightChop + " and " + leftChop);
                    Thread.sleep(TIME_IT_TAKES_TO_EAT);
                    System.out.println(name + " released " + rightChop + " and " + leftChop);
                    servings++;
                    isEating = false;
                    rightChop.release(name);
                    leftChop.release(name);
                    return true;
                }
                rightChop.release(name);
                return false;
            }
            return false;
    }

    public void leaveDinner(){
        this.attending = false;
    }

    public String getName(){
        return name;
    }

    public boolean isEating(){
        return isEating;
    }

    public TSChopstick getLeftChop() {
        return leftChop;
    }

    public TSChopstick getRightChop() {
        return rightChop;
    }

    // TODO: return statistics from philosophers:
    // avg wait time?
    // times eating rice
    // whatever else you think
    // this is where we can implement the metric gathering


    public String toString(){
        return "Philosoper " + name + ": Right chopstick- " + rightChop + ", Left chopstick- " + leftChop + ".";
    }
}
