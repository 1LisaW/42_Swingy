# Swingy

Swingy is a turn-based Java role-playing game. You create a hero, cross a map, fight villains, pick up artifacts, and level up. The same session can be played in the terminal or in a Swing window.

The code is split into MVC pieces. `GameController` runs the flow. The model holds heroes, maps, artifacts, and battles. `ConsoleView` and `GuiView` draw the game and collect input.

## Requirements

- Java 17 or newer
- Maven 3

## Build and run

Run these commands from `my-swing-app`. Heroes are loaded from and saved to `src/main/resources/save.txt` relative to that directory.

```bash
cd my-swing-app
mvn clean package
java -cp target/my-swing-app-1.0-SNAPSHOT.jar com.swingy.App console
java -cp target/my-swing-app-1.0-SNAPSHOT.jar com.swingy.App gui
```

With no argument, the program prints a short usage message and exits. `console` starts the terminal game. Any other argument, including `gui`, starts the Swing window.

### Docker

The repository includes a Maven image for building without a local Maven install:

```bash
docker build -t swing-builder .
./build.sh
```

`build.sh` mounts `my-swing-app` into the container and runs `mvn clean package`.

## How to play

### Main menu

1. Create a hero
2. Load a saved hero
3. Exit

A new hero needs a non-empty name and a class: wizard, warrior, or barbarian. Loading a hero starts a fresh map for that hero's current level.

### Classes

Every new hero starts at level 1 with 0 experience.

| Class | Hit points | Attack | Defense |
| --- | --- | --- | --- |
| Wizard | 5 | 5 | 0 |
| Warrior | 4 | 3 | 3 |
| Barbarian | 4 | 6 | 0 |

A level-up adds 3 hit points, 1 attack, and 1 defense. Experience needed for the next level is `level * 1000 + (level - 1)² * 450`. A defeated villain grants `villain level * 300` experience.

### Map

Map size is `(level - 1) * 5 + 10 - (level % 2)`. The hero starts in the center. Reaching any edge cell while still alive clears the map. Villains are placed at random, and their stats grow with the hero's level.

In the console, the map uses these symbols:

| Symbol | Meaning |
| --- | --- |
| `H` | Hero |
| `V` | Villain. Color marks strength: green, blue, yellow, then red |
| `W` | Villain above level 4 |
| `.` | Empty tile |

The Swing map uses hero and villain images (wizard, warrior, barbarian, goblin, orc, golem).

### Movement

Both views use the same keys:

| Key | Direction |
| --- | --- |
| `W` | Up |
| `A` | Left |
| `S` | Down |
| `D` | Right |

In the Swing window, the game panel needs keyboard focus for those keys to register.

### Encounters

Moving onto a villain opens a fight-or-run choice.

- **Fight.** Turns alternate until one side falls. Damage is attack minus the opponent's defense. About one strike in ten is a critical hit (double damage), and about one in ten misses. If neither side can deal damage, the fight is a draw and the hero steps back.
- **Run.** About 65% of the time the hero returns to the previous tile. Otherwise the fight starts anyway.
- **Loot.** A win can drop a weapon (+attack), armor (+defense), or helm (+hit points). Tiers run from Common to Legendary. Equipping an item replaces whatever is already in that slot. You can also leave the drop behind.
- **Defeat.** The run ends. You can replay from the last saved hero or return to the main menu.
- **Escape.** Clearing the map offers a replay, a new map at the current level, saving the hero and continuing, or a return to the main menu.

### Switching views

- At a console prompt, type `gui` to open the Swing window.
- In the Swing window, press `Ctrl+C` while a text field is not focused to return to the console.

## Save file

`my-swing-app/src/main/resources/save.txt` stores the roster. Each hero is a pipe-separated line, optionally followed by artifact lines for helm, weapon, and armor:

```text
0|hero|Rincewind|wizard|1|910|5|21|35
0|artifact|Common Sword|weapon|Common|0|2|0
```

The file is read when the application starts. It is written when you save a hero after clearing a map, and when a run ends in defeat.

## Tests

```bash
cd my-swing-app
mvn test
```

Tests use JUnit 5 and Mockito. The JaCoCo plugin writes a coverage report as part of the test run.

## Project structure

```text
my-swing-app/
  pom.xml
  src/main/java/com/swingy/
    App.java                 # entry point; console or gui argument
    controller/              # game flow and phase handling
    model/                   # hero, map, villains, artifacts, battle
    persistence/             # load and save heroes
    view/console/            # terminal interface
    view/gui/                # Swing window, panels, and popups
  src/main/resources/
    save.txt
    images/
  src/test/java/             # unit tests
Dockerfile
build.sh
```

## License

MIT. See [LICENSE](LICENSE).
