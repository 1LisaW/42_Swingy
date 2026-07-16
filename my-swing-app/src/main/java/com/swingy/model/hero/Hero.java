package com.swingy.model;

public class Hero {
    private String name;
    private String archetype;
    private int level;
    private int experience;
    private int hitPoints;
    private int attack;
    private int defense;
    private Artifact defenseArtifact;
    private Artifact attackArtifact;
    private Artifact hitPointsArtifact;

    public Hero(String name, String archetype, int level, int experience, int hitPoints, int attack, int defense) {
        this.name = name;
        this.archetype = archetype;
        this.level = level;
        this.experience = experience;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.defenseArtifact = null;
        this.attackArtifact = null;
        this.hitPointsArtifact = null;
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public void setDefenseArtifact(Artifact artifact) {
        if (this.defenseArtifact != null) {
            this.defense -= this.defenseArtifact.getDefenseBonus();
        }
        this.defenseArtifact = artifact;
        this.defense += artifact.getDefenseBonus();
    }

    public void setAttackArtifact(Artifact artifact) {
        if (this.attackArtifact != null) {
            this.attack -= this.attackArtifact.getAttackBonus();
        }
        this.attackArtifact = artifact;
        this.attack += artifact.getAttackBonus();
    }

    public void setHitPointsArtifact(Artifact artifact) {
        if (this.hitPointsArtifact != null) {
            this.hitPoints -= this.hitPointsArtifact.getHitPointsBonus();
        }
        this.hitPointsArtifact = artifact;
        this.hitPoints += artifact.getHitPointsBonus();
    }

    public void addArtifact(Artifact artifact) {
        switch (artifact.getArtifactType()) {
            case "armor":
                setDefenseArtifact(artifact);
                break;
            case "weapon":
                setAttackArtifact(artifact);
                break;
            case "helmet":
                setHitPointsArtifact(artifact);
                break;
            default:
                throw new IllegalArgumentException("Unknown artifact type: " + artifact.getArtifactType());
        }
    }
}
