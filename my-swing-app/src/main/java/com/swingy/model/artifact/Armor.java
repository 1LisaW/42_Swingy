package com.swingy.model;

import java.util.Arrays;
import java.util.List;

public class Armor extends Artifact {
    private static final List<String> armorTypes = Arrays.asList(
            "Gauntlets",
            "Chestplate",
            "Leggings",
            "Boots",
            "Pauldrons",
            "Sabatons",
            "Shield"
    );

    public Armor(ArtifactTier tier) {
        super("armor", tier);
    }

    public Armor(String name, ArtifactTier tier, int attackBonus, int defenseBonus, int hitPointsBonus) {
        super(name, "armor", tier, attackBonus, defenseBonus, hitPointsBonus);
    }

    @Override
    protected String generateRandomArtifactType() {
        return getRandomArmorType();
    }

    private String getRandomArmorType() {
        int index = RANDOM.nextInt(armorTypes.size());
        return armorTypes.get(index);
    }

    @Override
    protected int calculateAttackBonus(ArtifactTier tier) {
        return 0; // Armor does not provide an attack bonus
    }
    @Override
    protected int calculateHitPointsBonus(ArtifactTier tier) {
        return 0; // Armor does not provide a hit points bonus
    }
    @Override
    protected int calculateDefenseBonus(ArtifactTier tier) {
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
