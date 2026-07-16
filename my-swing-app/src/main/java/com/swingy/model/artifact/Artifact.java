package com.swingy.model;

import java.util.Random;
import java.util.Arrays;

public abstract class Artifact {
    private String name;
    ArtifactTier tier;
    String artifactType;
    private int attackBonus;
    private int defenseBonus;
    private int hitPointsBonus;

    protected static final Random RANDOM = new Random();

    public Artifact(String artifactType, ArtifactTier tier) {
        this.artifactType = artifactType;
        this.tier = tier;
        this.name = generateRandomArtifactName();
        this.attackBonus = calculateAttackBonus(tier);
        this.defenseBonus = calculateDefenseBonus(tier);
        this.hitPointsBonus = calculateHitPointsBonus(tier);
    }

    public Artifact(String name, String artifactType, ArtifactTier tier, int attackBonus, int defenseBonus, int hitPointsBonus) {
        this.name = name;
        this.artifactType = artifactType;
        this.tier = tier;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.hitPointsBonus = hitPointsBonus;
    }

    protected abstract String generateRandomArtifactType();
    public String getArtifactType() {
        return artifactType;
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    protected abstract int calculateDefenseBonus(ArtifactTier tier);
    protected abstract int calculateAttackBonus(ArtifactTier tier);
    protected abstract int calculateHitPointsBonus(ArtifactTier tier);

    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }

    public int getHitPointsBonus() {
        return hitPointsBonus;
    }

    public void setHitPointsBonus(int hitPointsBonus) {
        this.hitPointsBonus = hitPointsBonus;
    }

    protected String generateRandomArtifactName() {
        String artifactType = generateRandomArtifactType();
        switch (tier) {
            case COMMON:
                return "Common " + artifactType;
            case UNCOMMON:
                return "Uncommon " + artifactType;
            case RARE:
                return "Rare " + artifactType;
            case EPIC:
                return "Epic " + artifactType;
            case LEGENDARY:
                return "Legendary " + artifactType;
            default:
                return artifactType;
        }
    }
}
