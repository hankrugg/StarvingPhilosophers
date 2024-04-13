package src;

public class TSChopstick {

    private volatile boolean beingUsed = false;
    private final String name;

    public TSChopstick(String name){
        this.name = name;
    }

    public synchronized boolean acquire() {
        // if it's not being used, change it to being used and return true
        if (this.beingUsed) {
            return false;
        }
        this.beingUsed = true;
        return true;
    }

    public synchronized void release() {
        //change being used to false when released
        this.beingUsed = false;
    }

    public boolean isBeingUsed(){
        return beingUsed;
    }

    public String toString(){
        return name;
    }
}
