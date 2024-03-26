package src;

public class TSChopstick {
/*
    public synchronized Orange pop() {
        while (queue.isEmpty()) { // If the queue is empty do not try to pop
            try {
                this.wait();// wait if empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Orange orange = queue.remove(0); // Remove orange from queue
        this.notifyAll(); // Tell all threads that they can stop waiting
        return orange;

    }

    public synchronized void push(Orange orange) {
        queue.add(orange); // Add orange to list
        this.notifyAll(); // Threads can stop waiting.
    }

    public synchronized int getLength() {
        return queue.size();
    }
*/
}


