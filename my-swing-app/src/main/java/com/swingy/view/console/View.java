package com.swingy.view.console;

import com.swingy.model.Hero;
import com.swingy.model.Villain;

public class View {
    public void displayHeroStats(Hero hero) {
        System.out.println("Hero Stats:");
        System.out.println("Name: " + hero.getName());
        System.out.println("Level: " + hero.getLevel());
        System.out.println("Experience: " + hero.getExperience());
        System.out.println("Health: " + hero.getHealth());
        System.out.println("Attack: " + hero.getAttack());
        System.out.println("Defense: " + hero.getDefense());
        // if (hero.getArtifact() != null) {
        //     System.out.println("Artifact: " + hero.getArtifact().getName());
        //     System.out.println("Artifact Tier: " + hero.getArtifact().getTier());
        //     System.out.println("Attack Bonus: " + hero.getArtifact().getAttackBonus());
        //     System.out.println("Defense Bonus: " + hero.getArtifact().getDefenseBonus());
        //     System.out.println("Hit Points Bonus: " + hero.getArtifact().getHitPointsBonus());
        // } else {
        //     System.out.println("No artifact equipped.");
        // }
    }

    public void displayVillainStats(Villain villain) {
        System.out.println("Villain Stats:");
        System.out.println("Level: " + villain.getLevel());
        System.out.println("Health: " + villain.getHealth());
        System.out.println("Attack: " + villain.getAttack());
        System.out.println("Defense: " + villain.getDefense());
    }
    public void promptHeroCreation() {
        System.out.println("Enter hero name:");
    }
    public void promptMainMenu() {
        System.out.println("Welcome to Swingy!");
        System.out.println("1. Create Hero");
        System.out.println("2. Load Hero");
        System.out.println("3. Exit");
    }
}
