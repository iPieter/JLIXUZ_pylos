# Artificial intelligence: Pylos
This is a Heuristic player for the game Pylos. It uses a rule engine, Drools, to match human insights in the game to a valid move by the player.

## Purpose
This was developed for the lab associated with a course on artificial intelligence.

## Strategy
The strategy is defined by two sets of files:

- A `weights.txt` file with the weight for each rule. This file is located in the `java` folder.
- A set of `.drl` files, containing the rules writen by us. These are located in the resources folder of the student project.

These rules are used in Java by launching a KieSession, which is a general 'knowledge' framework used by Drools. 
Once all rules have fired, they will have resulted in a list of moves and a weight, based on the `weights.txt` file.
The highest move is executed.

## Weights
To optimize the weights, a genetic algoritm is used. This is implented in `PylosMain.java`

## Results
When matched with a `BestFitPlayer`, it achieves scores ranging from 90% up to 98% over 100 games.
