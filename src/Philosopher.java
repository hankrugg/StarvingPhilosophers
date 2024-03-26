package src;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Aidan Scott
 * Philosopher is class that is able to start and create and manage many workers which all do a different task in the
 * Orange processing pipeline
 */
public class Philosopher implements Runnable {
    private final AtomicBoolean dining = new AtomicBoolean();
    private Handed handed;
    private TSChopstick rightChop;
    private TSChopstick leftChop;

    Philosopher(Handed handed, TSChopstick rightChop, TSChopstick leftChop) {
        dining.set(true);
        this.handed = handed;
        this.rightChop = rightChop;
        this.leftChop = leftChop;
    }
    @Override
    public void run() {
    // the philosophers must feed
    }

    // TODO: return statistics from philosophers:
    // avg wait time?
    // times eating rice
    // whatever else you think
    public void stopPhilosopher() {
        dining.set(false);
    }
    // this is where we can implement the metric gathering
    /*
    public int getProcessedOranges() {
        return queueMap.get("processedQueue").getLength();
    }

    public int getBottles() {
        return getProcessedOranges() / ORANGES_PER_BOTTLE;
    }

    public int getWaste() {
        return getProcessedOranges() % ORANGES_PER_BOTTLE;
    }

     */
}
