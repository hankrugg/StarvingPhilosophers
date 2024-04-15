### Starving Philosophers
#### Programmed by Hank Rugg and Aidan Scott
This program is a solution to the starving philosophers problem.
By first understanding what problems the starving philosophers problem creates, race conditions, starvation and deadlocks
we were able to solve each problem methodically:
Thread Safe chopstick to protect against philosophers from accessing chopsticks at the same time.
Starvation solved by random "Philosophising" times giving a chance for other philosophers to eat.
Using a busy loop to check chopstick availability to solve hold and wait (deadlock condition).

### Installation
You will need java 17 to run this program

clone the repository into a project directory
```shell script
git clone https://github.com/hankrugg/StarvingPhilosophers
```

Move into the newly created directory
```shell script
cd StarvingPhilosophers
```

Run the program by typing:
```shell script
ant run
```

This program will also run with two arguments:
```
-Dnumber=<number_of_philosophers>
```
and 
```
-Dtime=<time_of_dinner>
```

The number of philosophers must be between 3 and 10 and the time of dinner must be between 3 and 30 (seconds).

To use these arguments, run 

```
ant run -Dnumber=7 -Dtime=20
```

### Challenges
The aforementioned problems, race conditions, starvation and deadlocks were our challenges.
Out of these problems, the major challenge was solving deadlocks.
There are four conditions for deadlocks to occur, and 
solving one of them gets rid of deadlocks, so we had many options for solutions.
Solving the hold and yield condition was opted to be solved by acquiring one chopstick
then the other, and if the second couldn't be acquired, the first would be released.
You can see the specific implementation within the philosopher class.

#### thank you
I hope you find our solution interesting, thank you for checking this out!

