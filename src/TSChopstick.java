public class TSChopstick {

    private volatile boolean beingUsed = false;
    private final String name;

    public TSChopstick(String name){
        this.name = name;
    }

    public synchronized boolean acquire(String person) {
        // if it's not being used, change it to being used and return true
        if (this.beingUsed) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            return false;
        }
        this.beingUsed = true;
        return true;
    }

    public synchronized void release(String person) {
        //change being used to false when released
        this.beingUsed = false;
//        notifyAll();
    }

    public boolean isBeingUsed(){
        return beingUsed;
    }

    public String toString(){
        return name;
    }
}
