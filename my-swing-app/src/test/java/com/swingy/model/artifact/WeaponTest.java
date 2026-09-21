package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WeaponTest {

    @Test
    void constructor_shouldCreateWeaponWithGivenTier() {
        Weapon weapon = new Weapon(ArtifactTier.COMMON);

        assertNotNull(weapon);
        assertEquals("weapon", weapon.getArtifactType());
    }

    @Test
    void commonWeapon_shouldGiveFiveAttack() {
        Weapon weapon = new Weapon(ArtifactTier.COMMON);

        assertEquals(5, weapon.getAttackBonus());
    }

    @Test
    void uncommonWeapon_shouldGiveTenAttack() {
        Weapon weapon = new Weapon(ArtifactTier.UNCOMMON);

        assertEquals(10, weapon.getAttackBonus());
    }

    @Test
    void rareWeapon_shouldGiveFifteenAttack() {
        Weapon weapon = new Weapon(ArtifactTier.RARE);

        assertEquals(15, weapon.getAttackBonus());
    }

    @Test
    void epicWeapon_shouldGiveTwentyAttack() {
        Weapon weapon = new Weapon(ArtifactTier.EPIC);

        assertEquals(20, weapon.getAttackBonus());
    }

    @Test
    void legendaryWeapon_shouldGiveTwentyFiveAttack() {
        Weapon weapon = new Weapon(ArtifactTier.LEGENDARY);

        assertEquals(25, weapon.getAttackBonus());
    }

    @Test
    void weapon_shouldGiveNoHitPointsBonus() {
        for (ArtifactTier tier : ArtifactTier.values()) {
            Weapon weapon = new Weapon(tier);

            assertEquals(
                0,
                weapon.getHitPointsBonus(),
                "Weapon should not provide hit points bonus for " + tier
            );
        }
    }

    @Test
    void weapon_shouldGiveNoDefenseBonus() {
        for (ArtifactTier tier : ArtifactTier.values()) {
            Weapon weapon = new Weapon(tier);

            assertEquals(
                0,
                weapon.getDefenseBonus(),
                "Weapon should not provide defense bonus for " + tier
            );
        }
    }

    @Test
    void weapon_shouldHaveWeaponArtifactType() {
        Weapon weapon = new Weapon(ArtifactTier.RARE);

        assertEquals("weapon", weapon.getArtifactType());
    }

    @Test
    void weapon_shouldGenerateOneOfValidWeaponTypes() {
        Weapon weapon = new Weapon(ArtifactTier.COMMON);

        String name = weapon.toString();

        assertNotNull(name);

        boolean validType =
            name.contains("Sword")
            || name.contains("Axe")
            || name.contains("Bow")
            || name.contains("Dagger")
            || name.contains("Mace");

        assertTrue(validType);
    }

    @Test
    void namedWeaponConstructor_shouldPreserveGivenValues() {
        Weapon weapon = new Weapon(
            "Dragon Bow",
            ArtifactTier.LEGENDARY,
            0,
            100,
            50
        );

        assertEquals("Dragon Bow", weapon.getName());
        assertEquals("weapon", weapon.getArtifactType());
        assertEquals(0, weapon.getAttackBonus());
        assertEquals(100, weapon.getDefenseBonus());
        assertEquals(50, weapon.getHitPointsBonus());
    }
}

