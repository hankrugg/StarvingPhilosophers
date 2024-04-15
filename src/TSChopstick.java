package src;

/**
 * @author Aidan Scott and Hank Rugg
 * TSChopstick is used to provide safe concurrent access to the chopstick object.
 * Implimentation is busy mutex
 */
public class TSChopstick {

    private final String name;
    private volatile boolean Used = false;

    /**
     * TSChopstick sets the name of the chopstick. Chopstick starts as not used
     * Acquire to use the chopstick
     * release to release the chopstick, if chopstick is not acquired, release will throw a runtime exception.
     * @param name
     */
    public TSChopstick(String name) {
        this.name = name;
    }
    /**
     * acquire will allow a user to acquire the chopstick.
     * it will not be allowed to be used by any other thread until it is released
     */
    public synchronized boolean acquire() {
        // if it's not being used, change it to being used and return true
        if (this.Used) {
            return false;
        } else {
            this.Used = true;
            return true;
        }
    }

    /**
     * release will allow the chopstick to be acquired again.
     * if chopstick is not acquired when released, then release will throw a Runtime Exception
     * @throws RuntimeException
     */
    public synchronized void release() {
        //change being used to false when released
        if (Used) {
            this.Used = false;
        } else {
            throw (new RuntimeException("Chopstick " + name + " is already released"));
        }
    }

    /**
     * isUsed returns the current status of the chopstick
     * @return status of chopstick
     */
    public boolean isUsed() {
        return Used;
    }
}
