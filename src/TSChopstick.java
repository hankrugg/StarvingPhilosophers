package src;

public class TSChopstick {

    private volatile boolean Used = false;
    private final String name;

    public TSChopstick(String name){
        this.name = name;
    }

    public synchronized boolean acquire() {
        // if it's not being used, change it to being used and return true
        if (this.Used) {
            return false;
        }else{
            this.Used = true;
            return true;
        }
    }

    public synchronized void release() {
        //change being used to false when released
        if(Used) {
            this.Used = false;
        }else{
            throw(new RuntimeException("Chopstick " + name + " is already released"));
        }
    }

    public boolean isUsed(){
        return Used;
    }

    public String toString(){
        return name;
    }
}
