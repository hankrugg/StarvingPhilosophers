### Starving Philosophers
Programmed by Hank Rugg and Aidan Scott
This program is a solution to the starving philosophers problem.
By first understanding what problems the starving philosophers problem creates, race conditions, starvation and deadlocks
we were able to solve each problem methodically:
Thread Safe chopstick to protect against philosophers from accessing chopsticks at the same time
Starvation solved by random "Philosophising" times giving a chance for other philosophers to eat
Using a busy look to check chopstick availability to solve hold and wait.

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

I hope you find our solution interesting, thank you for checking this out!

