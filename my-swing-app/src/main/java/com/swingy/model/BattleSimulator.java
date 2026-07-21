package com.swingy.model;

import java.util.List;
import java.util.ArrayList;

import com.swingy.model.Hero;
import com.swingy.model.Villain;

import com.swingy.model.Artifact;
import com.swingy.model.ArtifactFactory;

public class BattleSimulator {
    private Hero hero;
    private Villain villain;
    private List<String> log = new ArrayList<>();

    public BattleSimulator(Hero hero, Villain villain) {
        this.hero = hero;
        this.villain = villain;
    }

    private int getProbability() {
        if (Math.random() < 0.1)
            return 0;
        if (Math.random() < 0.1)
            return 1;
        return 2;
    }

    private String getDamageType(int probability) {
        if (probability == -1)
            return "No damage. Opponent is too strong.";
        if (probability == 0)
            return "Critical hit (x2)";
        if (probability == 1)
            return "A shot in the blue";
        return "Damage";
    }

    private int calcDamage(int probability, int damage) {
        if (damage <= 0)
            return 0;
        // Critical hit
        if (probability == 0)
            return (damage * 2);
        // Miss
        if (probability == 1)
            return (0);
        return damage;
    }

    public int fight() {
        int damageFromHero = this.hero.getAttack() - this.villain.getDefense();
        int damageFromVillain = this.villain.getAttack() - this.hero.getDefense();
        if (damageFromVillain <= 0 && damageFromHero <= 0) {
            this.log.add("This villain is too tough for you. Just as you are for him. Come back when you are stronger.");
            return 0;
        }
        while(this.hero.getHitPoints() > 0 && this.villain.getHitPoints() > 0) {
            int heroHitProbability = this.getProbability();
            int currentDamageFromHero = this.calcDamage(heroHitProbability, damageFromHero);
            int villainHitProbability = this.getProbability();
            int currentDamageFromVillain = this.calcDamage(villainHitProbability, damageFromVillain);
            this.villain.applyDamage(currentDamageFromHero);
            this.log.add("Hero attacks:    " + this.getDamageType(heroHitProbability) + " (" + damageFromHero + ").");
            if (this.villain.getHitPoints() > 0) {
                this.log.add("Villain attacks: " + this.getDamageType(villainHitProbability) + " (" + damageFromVillain + ").");
                this.hero.applyDamage(currentDamageFromVillain);
            }
        }
        if (this.hero.getHitPoints() <= 0) {
            this.log.add("Hero lost the battle.");
            return -1;
        }
        this.log.add("Hero won the battle!");
        return 1;
    }

    public int run() {
        if (Math.random() <= 0.65)
            return 1;
        return 0;
    }

    public int getExperience() {
        return this.villain.getLevel() * 300;
    }

    public Artifact generateArtifact() {
        ArtifactFactory artifactFactory = ArtifactFactory.getInstance();
        return (artifactFactory.createArtifact(this.villain.getAttack()));
    }

    public Hero getHero() {
        return this.hero;
    }

    public Villain getVillain() {
        return this.villain;
    }
    public List<String> getLog() {
        return this.log;
    }
}
