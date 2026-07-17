package com.swingy.view.console;

import java.util.Scanner;
import java.util.List;

import com.swingy.view.View;
import com.swingy.model.Hero;
import com.swingy.model.Villain;

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
    @Override
    public void promptMainMenu() {
        displayTextAsTyped("WELCOME TO SWINGY!", 50, ANSI_BLUE);
        System.out.println();
        displayTextAsTyped("Please choose an option:", 50, ANSI_BLUE);
        displayTextAsTyped("    1. Create Hero", 50, ANSI_YELLOW);
        displayTextAsTyped("    2. Load Hero", 50, ANSI_YELLOW);
        displayTextAsTyped("    3. Exit", 50, ANSI_YELLOW);
        // System.out.println("Welcome to Swingy!");
        // System.out.println("1. Create Hero");
        // System.out.println("2. Load Hero");
        // System.out.println("3. Exit");
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
        // return scanner.nextInt();
        System.out.print(prompt+": ");
        return scanner.nextLine(); // Placeholder return value
    }

    public void promptOnIncorrectInput() {
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
}
