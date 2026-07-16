package com.swingy.model;

import java.util.Random;

public class ArtifactFactory {
    private static ArtifactFactory instance = new ArtifactFactory(Villain.MIN_VILLAIN_STRENGTH, Villain.MAX_VILLAIN_STRENGTH);
    private int minVillainStrength;
    private int maxVillainStrength;

    private ArtifactFactory(int minVillainStrength, int maxVillainStrength) {
        this.minVillainStrength = minVillainStrength;
        this.maxVillainStrength = maxVillainStrength;
    }

    public static ArtifactFactory getInstance() {
        return instance;
    }

    public Artifact createArtifact(int villainsStrength) {
        ArtifactTier tier = determineArtifactTier(villainsStrength);
        boolean shouldCreateArtifact = calculateRandomProbability() <= 70; // 50% chance to create an artifact
        if (!shouldCreateArtifact)
            return null;
        int randomArtifactType = random.nextInt(2) + 1; // Currently only creating Weapon and Armor artifacts, can be extended for other types
        switch (randomArtifactType) {
            case 1:
                return new Weapon(tier);
            case 2:
                // Future implementation for creating other artifact types (e.g., Armor, Accessory)
                return new Armor(tier);
            default:
                return null;
        }
    }

    private int calculateRandomProbability() {
        return random.nextInt(100) + 1; // Returns a random number between 1 and 100
    }

    private ArtifactTier determineArtifactTier(int villainsStrength) {
        // Implementation for determining artifact tier based on villain strength and other factors
        int strengthRange = maxVillainStrength - minVillainStrength;
        int relativeStrength = villainsStrength - minVillainStrength;
        double downgradeChance = random.nextDouble(1); // Random chance for downgrading the tier
        double strengthPercentage = Math.min(downgradeChance, (double) relativeStrength / strengthRange);


        if (strengthPercentage < 0.2) {
            return ArtifactTier.COMMON;
        } else if (strengthPercentage < 0.4) {
            return ArtifactTier.UNCOMMON;
        } else if (strengthPercentage < 0.6) {
            return ArtifactTier.RARE;
        } else if (strengthPercentage < 0.8) {
            return ArtifactTier.EPIC;
        } else {
            return ArtifactTier.LEGENDARY;
        }
    }
    private static final Random random = new Random();
}
