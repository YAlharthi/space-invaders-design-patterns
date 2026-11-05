# Space Invaders: Design Patterns Refactoring

CCSW 413 Software Design Patterns course project, University of Jeddah.

This repository refactors [tatilattanzi/java-space-invaders](https://github.com/tatilattanzi/java-space-invaders) by applying design patterns one phase at a time. The game plays the same as the original, and the original commit history is kept.

## Patterns applied

| Phase | Tag | Category | Patterns | Section of the code |
|---|---|---|---|---|
| 1 | `phase-1` | Creational | Factory Method, Builder | Creating sprites and the alien wave |

## Run and verify

Needs JDK 11 or newer. No other libraries.

```
./scripts/run.sh      # play the game
./scripts/verify.sh   # compare behaviour against the pre-refactoring baseline
```

---

## Original project README

# Space Invaders
A simple Java 2D game clone.


# The game
Space Invaders is an arcade video game designed by Tomohiro Nishikado. It was first released in 1978. The player controls a cannon. He is about to save the Earth from invasion of evil space 
invaders.


# Controls
Go Left: Left Arrowkey;

Go Right: Right Arrowkey;

Shot: Spacebar;



# Have fun! ;)


# Game Screens

![Initial Screen](https://raw.githubusercontent.com/tatilattanzi/space-invaders/master/screens/space-invaders-initial-screen.png)

![Game Screen](https://raw.githubusercontent.com/tatilattanzi/space-invaders/master/screens/space-invaders-game-screen.png)

![GameOver](https://raw.githubusercontent.com/tatilattanzi/space-invaders/master/screens/space-invaders-gameover-screen.png)

![Won](https://raw.githubusercontent.com/tatilattanzi/space-invaders/master/screens/space-invaders-won-screen.png)
