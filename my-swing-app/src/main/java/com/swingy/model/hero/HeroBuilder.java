package com.swingy.model;

public class HeroBuilder {
    private String name;
    private String archetype;
    private int level;
    private int experience;
    private int hitPoints;
    private int attack;
    private int defense;

    public HeroBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public HeroBuilder setArchetype(String archetype) {
        this.archetype = archetype;
        return this;
    }

    public HeroBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    public HeroBuilder setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public HeroBuilder setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
        return this;
    }

    public HeroBuilder setAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public HeroBuilder setDefense(int defense) {
        this.defense = defense;
        return this;
    }

    public Hero build() {
        return new Hero(name, archetype, level, experience, hitPoints, attack, defense);
    }
}
