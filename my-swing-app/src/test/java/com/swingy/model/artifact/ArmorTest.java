package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArmorTest {

    @Test
    void constructor_shouldCreateArmorWithGivenTier() {
        Armor armor = new Armor(ArtifactTier.COMMON);

        assertNotNull(armor);
        assertEquals("armor", armor.getArtifactType());
    }

    @Test
    void commonArmor_shouldGiveFiveDefense() {
        Armor armor = new Armor(ArtifactTier.COMMON);

        assertEquals(5, armor.getDefenseBonus());
    }

    @Test
    void uncommonArmor_shouldGiveTenDefense() {
        Armor armor = new Armor(ArtifactTier.UNCOMMON);

        assertEquals(10, armor.getDefenseBonus());
    }

    @Test
    void rareArmor_shouldGiveFifteenDefense() {
        Armor armor = new Armor(ArtifactTier.RARE);

        assertEquals(15, armor.getDefenseBonus());
    }

    @Test
    void epicArmor_shouldGiveTwentyDefense() {
        Armor armor = new Armor(ArtifactTier.EPIC);

        assertEquals(20, armor.getDefenseBonus());
    }

    @Test
    void legendaryArmor_shouldGiveTwentyFiveDefense() {
        Armor armor = new Armor(ArtifactTier.LEGENDARY);

        assertEquals(25, armor.getDefenseBonus());
    }

    @Test
    void armor_shouldGiveNoAttackBonus() {
        for (ArtifactTier tier : ArtifactTier.values()) {
            Armor armor = new Armor(tier);

            assertEquals(
                0,
                armor.getAttackBonus(),
                "Armor should not provide attack bonus for " + tier
            );
        }
    }

    @Test
    void armor_shouldGiveNoHitPointsBonus() {
        for (ArtifactTier tier : ArtifactTier.values()) {
            Armor armor = new Armor(tier);

            assertEquals(
                0,
                armor.getHitPointsBonus(),
                "Armor should not provide HP bonus for " + tier
            );
        }
    }

    @Test
    void armor_shouldHaveArmorArtifactType() {
        Armor armor = new Armor(ArtifactTier.RARE);

        assertEquals("armor", armor.getArtifactType());
    }

    @Test
    void armor_shouldGenerateOneOfValidArmorTypes() {
        Armor armor = new Armor(ArtifactTier.COMMON);

        String name = armor.toString();

        assertNotNull(name);

        boolean validType =
            name.contains("Gauntlets")
            || name.contains("Chestplate")
            || name.contains("Leggings")
            || name.contains("Boots")
            || name.contains("Pauldrons")
            || name.contains("Sabatons")
            || name.contains("Shield");

        assertTrue(validType);
    }

    @Test
    void namedArmorConstructor_shouldPreserveGivenValues() {
        Armor armor = new Armor(
            "Dragon Shield",
            ArtifactTier.LEGENDARY,
            0,
            100,
            50
        );

        assertEquals("Dragon Shield", armor.getName());
        assertEquals("armor", armor.getArtifactType());
        assertEquals(0, armor.getAttackBonus());
        assertEquals(100, armor.getDefenseBonus());
        assertEquals(50, armor.getHitPointsBonus());
    }
}

