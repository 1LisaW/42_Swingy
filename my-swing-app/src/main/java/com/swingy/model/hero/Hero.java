package com.swingy.model;

public class Hero {
    private String name;
    private String archetype;
    private int level;
    private int experience;
    private int maxLevelExperience;
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
        this.setMaxExperience();
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public String getArchetype() {
        return this.archetype;
    }

    public int getLevel() {
        return level;
    }

    private void levelUp() {
        this.level += 1;

        this.hitPoints += 3;
        this.attack += 1;
        this.defense += 1;
        this.setMaxExperience();
    }

    public int getExperience() {
        return experience;
    }

    public int getMaxExperience() {
        return maxLevelExperience;
    }

    private void setMaxExperience() {
        this.maxLevelExperience = this.level * 1000 + (int)Math.pow(this.level - 1, 2) * 450;
    }

    public void setExperience(int experience) {
        this.experience += experience;
    }

    public boolean checkLevelUp(){
        if (this.experience >= this.maxLevelExperience) {
            this.experience -= this.maxLevelExperience;
            this.levelUp();
            return true;
        }
        return false;
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

    public int getBaseAttack() {
        if (this.attackArtifact != null)
            return this.attack - this.attackArtifact.getAttackBonus();
        return attack;
    }

     public int getBonusAttack() {
        if (this.attackArtifact != null)
            return this.attackArtifact.getAttackBonus();
        return 0;
    }

     public int getBaseDefense() {
        if (this.defenseArtifact != null)
            return this.defense - this.defenseArtifact.getDefenseBonus();
        return this.defense;
    }

     public int getBonusDefense() {
        if (this.defenseArtifact != null)
            return this.defenseArtifact.getDefenseBonus();
        return 0;
    }

    public int getBaseHitPoints() {
        if (this.hitPointsArtifact != null)
            return this.hitPoints - this.hitPointsArtifact.getHitPointsBonus();
        return this.hitPoints;
    }
     public int getBonusHitPoints() {
        if (this.hitPointsArtifact != null)
            return this.hitPointsArtifact.getHitPointsBonus();
        return 0;
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
