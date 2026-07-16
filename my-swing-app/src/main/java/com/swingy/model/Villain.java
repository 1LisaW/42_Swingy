package com.swingy.model;

public class Villain {
    public static final int MIN_VILLAIN_LEVEL = 1;
    public static final int MAX_VILLAIN_LEVEL = 10;
    public static final int MIN_VILLAIN_STRENGTH = 10;
    public static final int MAX_VILLAIN_STRENGTH = 100;
    private int level;
    private int hitPoints;
    private int attack;
    private int defense;

    public Villain(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
