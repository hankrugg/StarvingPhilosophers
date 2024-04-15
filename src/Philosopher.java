/**
 * @author Aidan Scott and Hank Rugg
 * Philosopher is class, once started, eats rice (uses two chopstics and waits) then releases both chopstics
 * then thinks, then eats again.
 * This is our solution to the starving philosopher problem by solving hold and wait
 */
public class Philosopher implements Runnable {
    private final String name;
    private final TSChopstick rightChop;
    private final TSChopstick leftChop;
    private final int TIME_IT_TAKES_TO_EAT = 500; // time in ms that the philosopher will be eating
    private final Thread philThread;
    private volatile boolean attending = true; // if the philosopher is attending, then keeping dining
    private volatile boolean isEating = false; // true if the philosopher is currently eating
    private volatile int servings; // number of times philosopher has eaten

    /**
     * Philosopher sets the name, and each chopstick
     * Threading is managed internally to decrease complexity
     *
     * @param name
     * @param rightChop TSChopstick
     * @param leftChop  TSChopstick
     */
    Philosopher(String name, TSChopstick rightChop, TSChopstick leftChop) {
        this.name = name;
        this.rightChop = rightChop;
        this.leftChop = leftChop;
        philThread = new Thread(this); // create thread for philosopher

    }

    /**
     * run starts the philosopher eating loop
     * the philosopher must feed
     */
    @Override
    public void run() {
        while (attending) { // if attending, then try to eat
            try {
                tryToEat();
            } catch (InterruptedException e) {
                // notify if philosopher was interrupted when attempting to eat
                System.err.println("Philosopher " + name + " was interrupted.");
            }
        }
    }


    /**
     * tryToEat contains the logic for eating
     * By attempting to acquire the right chopstick, and then left and if false, then release provides a solution
     * to the hold and wait requirement for deadlocks. We use this system in tryToEat.
     * Once acquired both chopsticks, the philosopher waits for the TIME_IT_TAKES_TO_EAT then released both then
     * waits for a random period of time from 0 to TIME_IT_TAKES_TO_EAT to think, then attempts to eat again
     *
     * @throws InterruptedException
     */
    private synchronized void tryToEat() throws InterruptedException {
        if (rightChop.acquire()) { // try to acquire first chopstick
            if (leftChop.acquire()) { // attempt to acquire second chopstick, if success, then eat
                isEating = true;
                Thread.sleep(TIME_IT_TAKES_TO_EAT); // philosopher consumes
                isEating = false; // stop eating
                // release both chopsticks
                rightChop.release();
                leftChop.release();
                servings++; // increment metric
                // The philosopher will think for a random amount of time
                Thread.sleep((long) (Math.random() * TIME_IT_TAKES_TO_EAT));
            } else {
                rightChop.release(); // release the first chopstick if the second couldn't be acquired
            }
        }
    }

    /**
     * Get the name of the philosopher
     *
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * Get eating status
     *
     * @return isEating bool
     */
    public boolean isEating() {
        return isEating;
    }

    /**
     * Get left chopstick
     *
     * @return leftChop TSChopstick
     */
    public TSChopstick getLeftChop() {
        return leftChop;
    }

    /**
     * Stops the philosopher
     */
    public void stopPhil() {
        try {
            attending = false; // stops look of philosopher eating
            philThread.join(); // stops the phil thread
        } catch (InterruptedException e) {
            System.err.println("Philosopher " + name + " join thread interrupted");
        }
    }

    /**
     * Starts philosopher
     */
    public void startPhil() {
        philThread.start();
    }

    /**
     * Gets the amount of servings
     */
    public int getServings(){
        return servings;
    }

    // TODO: return statistics from philosophers:
    // avg wait time?
    // times eating rice
    // whatever else you think
    // this is where we can implement the metric gathering

}
