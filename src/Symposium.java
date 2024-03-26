package src;

/**
 * @author Aidan Scott
 * Symposium is the class that creates, manages and stops all of the plants plus aggregating their product
 */
class Symposium {
    public static final long DINNER_TIME = 5 * 1000;
    static final int numberOfPhilosophers = 5;
    final TSChopstick[] chopsticks = new TSChopstick[numberOfPhilosophers];
    final Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
    final Thread[] philThreads = new Thread[numberOfPhilosophers];
    Symposium() {
        for (int i = 0; i < numberOfPhilosophers; i++){
            chopsticks[i] = new TSChopstick();
        }
        Handed handed = Handed.RIGHT;
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Philosopher(handed, chopsticks[i], chopsticks[i % numberOfPhilosophers]);
            philThreads[i] = new Thread(philosophers[i]);
            philThreads[i].start();
        }

        System.out.println("Waiting for the philosophers to finish their Symposium.");
        try {

            Thread.sleep(DINNER_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Customary interruption handling
        }

        System.out.println("Collecting the Philosopher's ponderings...");
        for (Philosopher i : philosophers) {

        }
        for (Thread i : philThreads) {
            try {
                i.join(); // If you aren't doing anything, stop the thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Customary interruption handling
            }
        }
    }

    // Entrypoint for the program
    public static void main(String[] args) {
        Symposium b = new Symposium();

    }
}