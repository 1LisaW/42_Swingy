package com.swingy.model;

public class Villain {
    public static final int MIN_VILLAIN_LEVEL = 1;
    public static final int MAX_VILLAIN_LEVEL = 10;
    public static final int MIN_VILLAIN_STRENGTH = 10;
    public static final int MAX_VILLAIN_STRENGTH = 100;
    private int level;
    private int health;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
