package com.swingy.model;

import java.util.Arrays;
import java.util.List;

public class Weapon extends Artifact {
    private static final List<String> weaponTypes = Arrays.asList(
            "Sword",
            "Axe",
            "Bow",
            "Dagger",
            "Mace"
    );

    public Weapon(ArtifactTier tier) {
        super("weapon", tier);
    }

    public Weapon(String name, ArtifactTier tier, int attackBonus, int defenseBonus, int hitPointsBonus) {
        super(name, "weapon", tier, attackBonus, defenseBonus, hitPointsBonus);
    }

    @Override
    protected String generateRandomArtifactType() {
        return getRandomWeaponType();
    }

    private String getRandomWeaponType() {
        int index = RANDOM.nextInt(weaponTypes.size());
        return weaponTypes.get(index);
    }

    @Override
    protected int calculateHitPointsBonus(ArtifactTier tier) {
        return 0; // Attack bonus is calculated in the constructor based on the tier
    }
    @Override
    protected int calculateDefenseBonus(ArtifactTier tier) {
        return 0; // Weapon does not provide a defense bonus
    }
    @Override
    protected int calculateAttackBonus(ArtifactTier tier) {
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
}
