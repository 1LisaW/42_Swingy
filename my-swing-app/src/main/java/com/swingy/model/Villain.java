package com.swingy.model;

public class Villain {
    public static final int MIN_VILLAIN_LEVEL = 1;
    public static final int MAX_VILLAIN_LEVEL = 10;
    public static final int MIN_VILLAIN_STRENGTH = 1;
    public static final int MAX_VILLAIN_STRENGTH = 100;
    private int level;
    private int hitPoints;
    private int attack;
    private int defense;

    public Villain(int level) {
        this.level = level;
        this.setRandomStats();
    }

    public int getLevel() {
        return level;
    }

    private void setRandomStats() {

        int maxPoints = 5 + this.level * 5;
        int points = (int)(Math.random() * maxPoints + 5);
        int randNum = (int)(Math.random() * (points - 3) + 1);
        this.hitPoints = randNum;
        points -= randNum;
        randNum = (int)(Math.random() * (points - 1) + 1);
        this.attack = randNum;
        points -= randNum;
        this.defense = Math.max(0, points);
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

}
