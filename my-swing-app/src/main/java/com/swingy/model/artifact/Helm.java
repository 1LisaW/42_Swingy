package com.swingy.model;

import java.util.Arrays;
import java.util.List;

public class Helm extends Artifact {
    private static final List<String> helmTypes = Arrays.asList(
            "Leather Cap",
            "Chain Helm",
            "Plate Helm",
            "Magic Hat"
    );

    public Helm(ArtifactTier tier) {
        super("helm", tier);
    }

    public Helm(String name, ArtifactTier tier, int attackBonus, int defenseBonus, int hitPointsBonus) {
        super(name, "helm", tier, attackBonus, defenseBonus, hitPointsBonus);
    }

    @Override
    protected String generateRandomArtifactType() {
        return getRandomHelmType();
    }

    private String getRandomHelmType() {
        int index = RANDOM.nextInt(helmTypes.size());
        return helmTypes.get(index);
    }

    @Override
    protected int calculateHitPointsBonus(ArtifactTier tier) {
        switch (tier) {
            case COMMON:
                return 5;
            case UNCOMMON:
                return 10;
            case RARE:
                return 15;
            case EPIC:
                return 20;
            case LEGENDARY:
                return 25;
            default:
                return 0;
        }
    }
    @Override
    protected int calculateAttackBonus(ArtifactTier tier) {
        return 0; // Helm does not provide an attack bonus
    }

    @Override
    protected int calculateDefenseBonus(ArtifactTier tier) {
        return 0; // Helm does not provide a defense bonus
    }
}
