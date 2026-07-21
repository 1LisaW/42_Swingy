package com.swingy.view.console;

import java.util.Scanner;
import java.util.List;

import com.swingy.view.View;
import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.model.GameMap;
import com.swingy.model.BattleSimulator;

import com.swingy.model.Artifact;

public class ConsoleView extends View {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";

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
        System.out.printf ("║ HP: %3s+%-2s    %3s %n",
                ANSI_GREEN + hero.getBaseHitPoints(), hero.getBonusHitPoints() + ANSI_RESET, ANSI_RED + villain.getHitPoints() + ANSI_RESET);
        System.out.printf ("║ ATK: %2s+%-2s    %3s %n",
                ANSI_GREEN + hero.getBaseAttack(), hero.getBonusAttack() + ANSI_RESET, ANSI_RED + villain.getAttack() + ANSI_RESET);
        System.out.printf ("║ DEF: %2s+%-2s    %3s %n",
                ANSI_GREEN + hero.getBaseDefense(), hero.getBonusDefense() + ANSI_RESET, ANSI_RED + villain.getDefense() + ANSI_RESET);
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    public int promptBattleFightOrRun(){
        displayTextAsTyped("Choose action from a list :", 50, ANSI_BLUE);
        displayTextAsTyped("    1. Fight", 50, ANSI_YELLOW);
        displayTextAsTyped("    2. Run", 50, ANSI_YELLOW);
        return getUserIntInputInRange(2);
    }

    @Override
    public void displayBattleLog(BattleSimulator battleSimulator) {
        List<String> log = battleSimulator.getLog();
        displayTextAsTyped("BATTLE LOG :", 50, ANSI_GREEN);
        for (String record:log) {
            displayTextAsTyped("    "  + record, 50, ANSI_GREEN);
        }
    }

    @Override
    public void displayMap(GameMap gameMap) {
        displayTextAsTyped("Current map state:", 50, ANSI_GREEN);
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

    // public void displayVillainStats(Villain villain) {
    //     System.out.println("Villain Stats:");
    //     System.out.println("Level: " + villain.getLevel());
    //     System.out.println("Hit Points: " + villain.getHitPoints());
    //     System.out.println("Attack: " + villain.getAttack());
    //     System.out.println("Defense: " + villain.getDefense());
    // }
    // public void promptHeroCreation() {
    //     System.out.println("Enter hero name:");
    // }

    //  MainMenu
    @Override
    public void displayMainMenu() {
        displayTextAsTyped("WELCOME TO SWINGY!", 50, ANSI_BLUE);
        System.out.println();
        displayTextAsTyped("Please choose an option:", 50, ANSI_BLUE);
        displayTextAsTyped("    1. Create Hero", 50, ANSI_YELLOW);
        displayTextAsTyped("    2. Load Hero", 50, ANSI_YELLOW);
        displayTextAsTyped("    3. Exit", 50, ANSI_YELLOW);
    }

    @Override
    public int promptMainMenu() {
        return getUserIntInputInRange(3);
    }

    @Override
    public void displayMainMenuStatus(int choice) {
        switch (choice) {
            case 1:
                displayTextAsTyped("Creating new hero ...", 50, ANSI_GREEN);
                break;
            case 2:
                displayTextAsTyped("Loading list of heroes...", 50, ANSI_GREEN);
                break;
            case 3:
                displayTextAsTyped("It was nice to see you. Have a nice day!", 50, ANSI_GREEN);
                break;
        }
    }


    public void promptChooseHeroClass() {
        displayTextAsTyped("Choose a hero class :", 50, ANSI_BLUE);
        displayTextAsTyped("    1. Wizard", 50, ANSI_YELLOW);
        displayTextAsTyped("    2. Warrior", 50, ANSI_YELLOW);
        displayTextAsTyped("    3. Barbarian", 50, ANSI_YELLOW);
    }

    public String getUserInput(String prompt) {
        // Implement logic to get user input from the console
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt+": ");
        return scanner.nextLine(); // Placeholder return value
    }


    public int getUserIntInputInRange(int maxNum) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your choice: ");
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                this.displayOnIncorrectInput();
            }
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= maxNum)
                return choice;
            this.displayOnIncorrectInput();
        }
    }


    public void displayOnIncorrectInput() {
        displayTextAsTyped("Invalid input. Please try again.", 50, ANSI_RED);
    }

    private void displayTextAsTyped(String text, int delay, String color) {
        for (char c : text.toCharArray()) {
            System.out.print(color+c+ANSI_RESET); // Print each character in the specified color
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
    @Override
    public void promptChooseFromHeroList(List<Hero> heroes) {
        displayTextAsTyped("Choose a hero from list :", 50, ANSI_BLUE);
        int i = 1;
        for (Hero hero: heroes) {
            System.out.println(i++);
            displayHeroStats(hero);
        }
    }

    @Override
    public void displayChooseHeroFromList(List<Hero> heroes) {
        displayTextAsTyped("Choose a hero from a list :", 50, ANSI_BLUE);
        int i = 1;
        for (Hero hero: heroes) {
            System.out.println(i++);
            displayHeroStats(hero);
        }
    }

    // Choose hero from Repo
    @Override
    public int promptChooseHeroFromList(int maxNum) {
        displayTextAsTyped("Choose a hero from list :", 50, ANSI_BLUE);
        return getUserIntInputInRange(maxNum);
    }

    @Override
    public void displayChooseHeroFromListStatus(Hero hero) {
        displayTextAsTyped("You choose a hero :", 50, ANSI_GREEN);
        displayHeroStats(hero);
    }

    @Override
    public String promptHeroMove() {
        Scanner scanner = new Scanner(System.in);
        displayTextAsTyped("Make a move (W,A,S,D) :", 50, ANSI_BLUE);
        while (true) {
            String move = scanner.nextLine().toLowerCase().trim();
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
        displayTextAsTyped("Congrats! You got a new Artifact!", 50, ANSI_BLUE);
        this.displayArtifact(artifact);
        displayTextAsTyped("What you can do with an artifact :", 50, ANSI_BLUE);
        displayTextAsTyped("    1. use", 50, ANSI_YELLOW);
        displayTextAsTyped("    2. drop", 50, ANSI_YELLOW);
    }

    @Override
    public int promptUseArtifact() {
        displayTextAsTyped("Choose an option from list :", 50, ANSI_BLUE);
        return getUserIntInputInRange(2);
    }

    @Override
    public void displayOnHeroRun(boolean isSuccessful)  {
        if (isSuccessful)
            displayTextAsTyped("Hero successfully ran out of danger.", 50, ANSI_GREEN);
        else
            displayTextAsTyped("Hero couldn't ran away. Prepare for a fight!", 50, ANSI_GREEN);

    }

    public void displayLevelUp(Hero hero) {
        displayTextAsTyped("HERO LEVELED UP!", 50, ANSI_YELLOW);
        displayHeroStats(hero);
    }
}
