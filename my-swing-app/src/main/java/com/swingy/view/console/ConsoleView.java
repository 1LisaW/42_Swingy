package com.swingy.view.console;

import java.util.Scanner;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;

import com.swingy.view.View;
import com.swingy.model.Hero;
import com.swingy.model.HeroCredentials;
import com.swingy.model.Villain;
import com.swingy.model.GameMap;
import com.swingy.model.BattleSimulator;
import com.swingy.model.BattleResult;
import com.swingy.model.Artifact;
import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.view.ViewManager;


public class ConsoleView extends View {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";

    private Thread consoleThread;
    private int textDelay = 10;

    private Scanner scanner;
    private volatile boolean isRunning = false;


    public ConsoleView(GameController controller, ViewManager viewManager) {
        super(controller, viewManager);
        scanner = new Scanner(System.in);
    }

    ConsoleView (
        GameController controller,
        ViewManager viewManager,
        InputStream input) {
        super(controller, viewManager);
        this.scanner = new Scanner(input);
    }

    private boolean checkOnSwitchToGui(String input) {
        if (input.equalsIgnoreCase("gui")) {
            viewManager.switchToSwing();
            return true;
        }
        return false;
    }

    void setTextDelay(int delay) {
        this.textDelay = delay;
    }

    @Override
    public void start() {}

    @Override
    public void displayHeroStats(Hero hero) {
        String heroClass = Character.toUpperCase(hero.getArchetype().charAt(0)) + hero.getArchetype().substring(1);
        System.out.println("╔════ HERO ═════════════════════╗");
        System.out.printf ("║ %-8s (%-7s) Lv.%-2d %n", hero.getName(), heroClass, hero.getLevel());
        System.out.printf ("║ HP: %3d+%-2d    EXP: %3d/%-4d %n",
                hero.getBaseHitPoints(), hero.getBonusHitPoints(), hero.getExperience(), hero.getMaxExperience());
        System.out.printf ("║ ATK: %2d+%-2d    DEF: %2d+%-2d      %n",
                hero.getBaseAttack(), hero.getBonusAttack(), hero.getBaseDefense(), hero.getBonusDefense());
        System.out.println("╚═══════════════════════════════╝");
    }

    @Override
    public void displayBattleParticipants(BattleSimulator battleSimulator) {
        Hero hero = battleSimulator.getHero();
        Villain villain = battleSimulator.getVillain();
        System.out.println("╔════════════ HERO vs Villain ═══════════╗");
        System.out.printf ("║ HP:  %3s+%-2s    %3s %n",
                ANSI_GREEN + hero.getBaseHitPoints(), hero.getBonusHitPoints() + ANSI_RESET, ANSI_RED + villain.getHitPoints() + ANSI_RESET);
        System.out.printf ("║ ATK: %2s+%-2s    %3s %n",
                ANSI_GREEN + hero.getBaseAttack(), hero.getBonusAttack() + ANSI_RESET, ANSI_RED + villain.getAttack() + ANSI_RESET);
        System.out.printf ("║ DEF: %2s+%-2s    %3s %n",
                ANSI_GREEN + hero.getBaseDefense(), hero.getBonusDefense() + ANSI_RESET, ANSI_RED + villain.getDefense() + ANSI_RESET);
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    public int promptBattleFightOrRun(){
        displayTextAsTyped("Choose action from a list :", ANSI_BLUE);
        displayTextAsTyped("    1. Fight", ANSI_YELLOW);
        displayTextAsTyped("    2. Run", ANSI_YELLOW);
        return getUserIntInputInRange(2);
    }

    @Override
    public void displayBattleLog(List<String> log) {
        displayTextAsTyped("BATTLE LOG :", ANSI_GREEN);
        for (String record:log) {
            displayTextAsTyped("    "  + record, ANSI_GREEN);
        }
    }

    @Override
    public void displayMap(GameMap gameMap) {
        displayTextAsTyped("Current map state:", ANSI_GREEN);
        int heroPosition = gameMap.getHeroPosition();
        int size = gameMap.getSize();
        for (int i = 0; i < size * size; i++) {
            if (i > 0 && i % size == 0)
                System.out.println();
            int villainLevel = gameMap.getVillainAtPos(i);
            String ch = " . ";
            if (villainLevel > 0) {
                switch (villainLevel) {
                    case 1:
                        ch = ANSI_GREEN + " V " + ANSI_RESET;
                        break;
                    case 2:
                        ch = ANSI_BLUE + " V " + ANSI_RESET;
                        break;
                    case 3:
                        ch = ANSI_YELLOW + " V " + ANSI_RESET;
                        break;
                    case 4:
                        ch = ANSI_RED + " V " + ANSI_RESET;
                        break;
                    default:
                        ch = ANSI_RED + " W " + ANSI_RESET;
                        break;
                }
            }
            if (i == heroPosition)
                ch =" H ";
            System.out.print(ch);
        }
        System.out.println();
    }

    //  MainMenu
    @Override
    public void displayMainMenu() {
        if (!isRunning)
            return;
        displayTextAsTyped("WELCOME TO SWINGY!", ANSI_BLUE);
        System.out.println();
        displayTextAsTyped("Please choose an option:", ANSI_BLUE);
        displayTextAsTyped("    1. Create Hero", ANSI_YELLOW);
        displayTextAsTyped("    2. Load Hero", ANSI_YELLOW);
        displayTextAsTyped("    3. Exit", ANSI_YELLOW);
    }


    @Override
    public int promptMainMenu() {
        return getUserIntInputInRange(3);
    }

    @Override
    public void displayMainMenuStatus(int choice) {
        if (!isRunning)
            return;
        switch (choice) {
            case 1:
                displayTextAsTyped("Creating new hero ...", ANSI_GREEN);
                break;
            case 2:
                displayTextAsTyped("Loading list of heroes...", ANSI_GREEN);
                break;
            case 3:
                displayTextAsTyped("It was nice to see you. Have a nice day!", ANSI_GREEN);
                break;
        }
    }


    public void promptChooseHeroClass() {
        displayTextAsTyped("Choose a hero class :", ANSI_BLUE);
        displayTextAsTyped("    1. Wizard", ANSI_YELLOW);
        displayTextAsTyped("    2. Warrior", ANSI_YELLOW);
        displayTextAsTyped("    3. Barbarian", ANSI_YELLOW);
    }

    public String getUserInput(String prompt) {
        // Implement logic to get user input from the console
        if (!prompt.isEmpty())
            System.out.println(prompt);
        return scanner.nextLine(); // Placeholder return value
    }


    public int getUserIntInputInRange(int maxNum) {
        // Scanner scanner = new Scanner(System.in);
        while (isRunning) {
            System.out.print("Please enter your choice: ");
            String input = scanner.nextLine();
            while (!input.matches("\\d+") ) {
                if (checkOnSwitchToGui(input.trim()))
                    return -1;
                input = scanner.nextLine();
                this.displayOnIncorrectInput();
            }
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= maxNum)
                return choice;
            this.displayOnIncorrectInput();
        }
        return -1; // Return -1 if the loop is exited
    }


    public void displayOnIncorrectInput() {
        displayTextAsTyped("Invalid input. Please try again.", ANSI_RED);
    }

    private void displayTextAsTyped(String text, String color) {
        for (char c : text.toCharArray()) {
            System.out.print(color+c+ANSI_RESET); // Print each character in the specified color
            try {
                Thread.sleep(textDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    @Override
    public void promptChooseFromHeroList(List<Hero> heroes) {
        displayTextAsTyped("Choose a hero from list :", ANSI_BLUE);
        int i = 1;
        for (Hero hero: heroes) {
            System.out.println(i++);
            displayHeroStats(hero);
        }
    }

    @Override
    public void displayChooseHeroFromList() {
        List<Hero> heroes = this.controller.getHeroes();
        displayTextAsTyped("Choose a hero from a list :", ANSI_BLUE);
        int i = 1;
        for (Hero hero: heroes) {
            System.out.println(i++);
            displayHeroStats(hero);
        }
    }

    // Choose hero from Repo
    @Override
    public int promptChooseHeroFromList(int maxNum) {
        displayTextAsTyped("Choose a hero from list :", ANSI_BLUE);
        return getUserIntInputInRange(maxNum);
    }

    @Override
    public void displayChooseHeroFromListStatus(Hero hero) {
        displayTextAsTyped("You choose a hero :", ANSI_GREEN);
        displayHeroStats(hero);
    }

    @Override
    public String promptHeroMove() {
        // Scanner scanner = new Scanner(System.in);
        displayTextAsTyped("Make a move (W,A,S,D) :", ANSI_BLUE);
        while (isRunning) {
            String move = scanner.nextLine().toLowerCase().trim();
            if (checkOnSwitchToGui(move))
                return null;
            switch (move) {
                case "w":
                    return "up";
                case "s":
                    return "down";
                case "a":
                    return "left";
                case "d":
                    return "right";
            }
            this.displayOnIncorrectInput();
        }
        return null; // Return null if the loop is exited
    }

    public void displayArtifact(Artifact artifact) {
        System.out.println("╔═════════ Artifact ═════════════════════╗");
        System.out.printf ("║ %-15s %n", artifact.getName());

        System.out.printf ("║ ATK: %3d  DEF: %3d  HP: %3d  %n",
                artifact.getAttackBonus(), artifact.getDefenseBonus() ,artifact.getHitPointsBonus());
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    public void displayUseArtifact(Artifact artifact) {
        displayTextAsTyped("Congrats! You got a new Artifact!", ANSI_BLUE);
        this.displayArtifact(artifact);
        displayTextAsTyped("What you can do with an artifact :", ANSI_BLUE);
        displayTextAsTyped("    1. use", ANSI_YELLOW);
        displayTextAsTyped("    2. drop", ANSI_YELLOW);
    }

    @Override
    public int promptUseArtifact() {
        displayTextAsTyped("Choose an option from list :", ANSI_BLUE);
        return getUserIntInputInRange(2);
    }

    @Override
    public void displayOnHeroRun(boolean isSuccessful)  {
        if (isSuccessful)
            displayTextAsTyped("Hero successfully ran out of danger.", ANSI_GREEN);
        else
            displayTextAsTyped("Hero couldn't ran away. Prepare for a fight!", ANSI_RED);

    }

    public void displayLevelUp(Hero hero) {
        displayTextAsTyped("HERO LEVELED UP!", ANSI_YELLOW);
        displayHeroStats(hero);
    }

    @Override
    public void displayGameResult(boolean isWin) {
        if (isWin) {
            displayTextAsTyped("CONGRATS! HERO SUCCESSFULLY ESCAPED MAP.", ANSI_GREEN);
            // onLevelCleared();
        }
        else
            displayTextAsTyped("YOU HAVE DIED...", ANSI_RED);

    }

    private void toHeroCreation() {
        System.out.println("ConsoleView toHeroCreation");
        Hero currentHero = this.controller.createHero(createHeroCredentials());
        displayHeroStats(currentHero);
        controller.setGamePhase(Phases.GAMEPLAY);
        startGame(currentHero);
    }

    private void toHeroSelection() {
        List<Hero> heroes = this.controller.getHeroes();
        displayChooseHeroFromList();
        int choice = promptChooseHeroFromList(heroes.size());
        if (!isRunning)
            return;
        Hero currentHero = heroes.get(choice - 1);
        displayChooseHeroFromListStatus(currentHero);
        controller.setGamePhase(Phases.GAMEPLAY);
        startGame(currentHero);
    }

    @Override
    public void mainMenu() {
        this.controller.setGamePhase(Phases.MAIN_MENU);
        displayMainMenu();
        int chosenOption = promptMainMenu();
        if (!isRunning)
            return;
        displayMainMenuStatus(chosenOption);
        Hero currentHero = null;
        switch (chosenOption) {
            case 1:
                this.controller.setGamePhase(Phases.HERO_CREATION);
                toHeroCreation();
                // Logic to create a new hero
                break;
            case 2:
                this.controller.setGamePhase(Phases.HERO_SELECTION);
                toHeroSelection();
                break;
            case 3:
                isRunning = false;
                viewManager.exit();
                // hide();
                break;
            default:
                displayOnIncorrectInput();
                mainMenu(); // Recursively call toMainMenu() for invalid input
                break;
        }
    }

    @Override
    public void startGame(Hero hero) {
        this.controller.startGame(hero);
        GameLoop();
    }

    private void toBattleResult() {
        BattleResult battleResult = controller.getBattleResult();
        if (battleResult == BattleResult.DRAW) {
            this.controller.setGamePhase(Phases.GAMEPLAY);
            displayTextAsTyped("THE BATTLE WAS WITHDRAWN. DRAW", ANSI_GREEN);
            toGamePhase();
            return ;
        }
        displayBattleLog(this.controller.getBattleLog());
        if (battleResult == BattleResult.LOSE) {
            this.controller.setGamePhase(Phases.GAME_OVER);
            return ;
        }
        if (battleResult == BattleResult.WIN) {
            Boolean hasArtifact = this.controller.isBattleProduceArtifact();
            if (hasArtifact) {
                this.controller.setGamePhase(Phases.BATTLE_ARTIFACT);
                toGamePhase();
            } else if (this.controller.willLevelUp()) {

                this.controller.setGamePhase(Phases.HERO_LEVEL_UP);
                toGamePhase();
            } else if (this.controller.isGameOver()) {
                this.controller.setGamePhase(Phases.GAME_OVER);
                toGameOver();
            } else {
                this.controller.setGamePhase(Phases.GAMEPLAY);
            }
        }
    }

    private void toLevelUp() {
        displayLevelUp(this.controller.getHero());
        this.controller.setGamePhase(Phases.GAMEPLAY);
        toGamePhase();
    }


    private void toArtifactPhase() {
            Artifact artifact = this.controller.getBattleArtifact();
            displayUseArtifact(artifact);
            if (promptUseArtifact() == 1) {
                this.controller.updateHeroArtifact();
            }
            if (this.controller.willLevelUp()) {
                this.controller.setGamePhase(Phases.HERO_LEVEL_UP);
                toGamePhase();
            }
            // }
            if (this.controller.isGameOver()) {
                this.controller.setGamePhase(Phases.GAME_OVER);
                toGameOver();
            } else if( isRunning) {
                this.controller.setGamePhase(Phases.GAMEPLAY);
                toGamePhase();
            }
        // }
    }

    private void toGameOver() {
        if (this.controller.getGamePhase() != Phases.GAME_OVER)
            return;
        Boolean isWin = this.controller.levelCleared();
        onGameOver(isWin);
        // toGameOver(isWin);
    }

    private void toBattleRunOrFight() {
        displayBattleParticipants(this.controller.getCurrentBattleSimulator());
        int choice = promptBattleFightOrRun();
        switch (choice) {
            case 1:
                this.controller.runBattle();
                this.controller.setGamePhase(Phases.BATTLE_RESULT);
                toGamePhase();
                break;
            case 2:
                this.controller.setGamePhase(Phases.BATTLE_RUN_RESULT);
                this.controller.runFromBattle();
                toGamePhase();
                break;
        }
        // displayBattleParticipants();
    }

    private void toBattleRunResult() {
        boolean isSuccessful = this.controller.runFromBattle() != 0;
        displayOnHeroRun(isSuccessful);
        if (isSuccessful == false) {
            controller.runBattle();
            this.controller.setGamePhase(Phases.BATTLE_RESULT);
            toGamePhase();
        } else if(isRunning) {
            this.controller.setGamePhase(Phases.GAMEPLAY);
            toGamePhase();
        }
    }

    private void moveHero(String move) {
        this.controller.moveHero(move);
        if (controller.isBattleTriggered()) {
            this.controller.setGamePhase(Phases.BATTLE_RUN_OR_FIGHT);
            toGamePhase();
        }
        if (this.controller.levelCleared()) {
            onGameOver(true);
        }
    }
    private void onGameOver(Boolean won) {
        displayGameResult(won);
        if (won)
            onLevelCleared();
        else
            onFail();
    }

    private void onFail() {
        if (!isRunning)
            return ;
        displayTextAsTyped("Choose action from a list :", ANSI_BLUE);
        displayTextAsTyped("    1. Restart game", ANSI_YELLOW);
        displayTextAsTyped("    2. To main menu", ANSI_YELLOW);
        int choice = getUserIntInputInRange(2);
        switch (choice) {
            case 1:
                this.controller.restartGame();
                GameLoop();
                break;
            case 2:
                mainMenu();
                break;
            case -1:
                break;
            default:
                displayOnIncorrectInput();
                onFail(); // Recursively call toMainMenu() for invalid input
                break;
        }
    }

    private void onLevelCleared() {
        if (!isRunning)
            return ;
        displayTextAsTyped("Choose action from a list :",  ANSI_BLUE);
        displayTextAsTyped("    1. Restart game", ANSI_YELLOW);
        displayTextAsTyped("    2. Proceed", ANSI_YELLOW);
        displayTextAsTyped("    3. Save hero and proceed", ANSI_YELLOW);
        displayTextAsTyped("    4. To main menu", ANSI_YELLOW);

        int choice = getUserIntInputInRange(4);
        switch (choice) {
            case 1:
                //this.controller.saveHero();
                this.controller.restartGame();
                GameLoop();
                break;
            case 2:
                this.controller.startNewGame();
                GameLoop();
                break;
            case 3:
                this.controller.saveAndStartNewGame();
                GameLoop();
                break;
            case 4:
                mainMenu();
                // hide();
                break;
            case -1:
                break;
            default:
                displayOnIncorrectInput();
                onLevelCleared(); // Recursively call toMainMenu() for invalid input
                break;
        }
        //this.controller.proceedToNextLevel();
    }

    private void GameLoop() {
        while (isRunning && this.controller.getGamePhase() != Phases.GAME_OVER) {
            toGamePhase();
         }
         if (isRunning && this.controller.getGamePhase() == Phases.GAME_OVER)
            toGameOver();
    }

    private void toGameplay() {
        // while (!this.controller.isGameOver()) {
        displayMap(this.controller.getGameMap());
        String move = promptHeroMove();
        if (!isRunning)
            return;
        moveHero(move);
        // }
    }

    private void toGamePhase() {
        Phases phase = this.controller.getGamePhase();
        switch (phase) {
            case GAMEPLAY:
                toGameplay();
                break;
            case BATTLE_RUN_OR_FIGHT:
                toBattleRunOrFight();
                break;
            case BATTLE_RUN_RESULT:
                toBattleRunResult();
                break;
            case BATTLE_RESULT:
                toBattleResult();
                break;
            case BATTLE_ARTIFACT:
                toArtifactPhase();
                break;
            case HERO_LEVEL_UP:
                toLevelUp();
                break;
        }
    }


    @Override
    protected HeroCredentials createHeroCredentials() {
        HeroCredentials heroCredentials = new HeroCredentials();
        heroCredentials.setName(getUserInput("Enter hero name:"));
        while (heroCredentials.getName().isEmpty()) {
            displayOnIncorrectInput();
            heroCredentials.setName(getUserInput("Enter hero name:"));
        }
        promptChooseHeroClass();
        String archetype = getUserInput("");
        while (!archetype.equals("1") && !archetype.equals("2") && !archetype.equals("3")) {
            displayOnIncorrectInput();
            promptChooseHeroClass();
            archetype = getUserInput("");
        }
        switch (archetype) {
            case "1":
                heroCredentials.setHeroArchetype("wizard");
                break;
             case "2":
                heroCredentials.setHeroArchetype("warrior");
                break;
             case "3":
                heroCredentials.setHeroArchetype("barbarian");
                break;
        }
        return heroCredentials;
    }

    private void runConsole() {
        Phases gamePhase = this.controller.getGamePhase();
        switch (gamePhase) {
            case MAIN_MENU:
                mainMenu();
                break;
             case HERO_CREATION:
                toHeroCreation();
                break;
            case HERO_SELECTION:
                toHeroSelection();
                break;
            case GAMEPLAY:
            case BATTLE_RUN_OR_FIGHT:
            case BATTLE_RUN_RESULT:
            case BATTLE_RESULT:
            case BATTLE_ARTIFACT:
            case HERO_LEVEL_UP:
                GameLoop();
                break;
            case GAME_OVER:
                toGameOver();
                // GameLoop();
                // toGameOver();
                break;
            default:
                mainMenu();
        }
    }

    private void clearPendingInput() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void show() {
        // Logic to show the console view
        isRunning = true;
        clearPendingInput();

        scanner = new Scanner(System.in);
        consoleThread = new Thread(() -> {
            while (isRunning) {
                runConsole();
            }
        }, "ConsoleView");

        consoleThread.start();
    }

    @Override
    public void hide() {
        // Logic to hide the console view
        System.out.println("Console view hidden.");
        isRunning = false;
        if (consoleThread != Thread.currentThread())
            consoleThread.interrupt();
    }

}
