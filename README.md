# Ruawatrain

**Roster**: Benjamin Belotser, Josiah Moltz, David Deng

This project is a recreation of PacMan. If finished before deadline, we plan to add a gamemode `Hunted` where the player attempts to hunt the Pacman, which is an AI.

### How to Run
- Open Terminal

```
//Clone Our Project
git clone https://github.com/benb05/ruawatrain.git

//Navigate to directory and compile 'Woo.java'. Then, run Woo with map you wish to play on.
cd ruawatrain/Woo
javac Woo.java
java Woo l1.map
```

### About

**Pacman**: \
Pacman is controlled by *you*! It essentially takes an input from the terminal, which corresponds to a direction, and then will adjust to turn in that direction.

**Ghosts**: \
The Ghosts have 5 difficulty settings. The harder the difficulty setting, the more often the ghost will use an algorithm to find pacman. \
The algorithm is based off of recursive backsolving, but it is optimized to make a first move in the direction of pacman. \
When ghosts are not using htis algorithm they are using a random move algorithm, that forces the ghost to continue until it hits an intersection, and then make a random turn.
