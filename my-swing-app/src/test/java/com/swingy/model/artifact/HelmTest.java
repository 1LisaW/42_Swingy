package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HelmTest {

    @Test
    void constructor_shouldCreateHelmWithGivenTier() {
        Helm helm = new Helm(ArtifactTier.COMMON);

        assertNotNull(helm);
        assertEquals("helm", helm.getArtifactType());
    }

    @Test
    void commonHelm_shouldGiveFiveHitPoints() {
        Helm helm = new Helm(ArtifactTier.COMMON);

        assertEquals(5, helm.getHitPointsBonus());
    }

    @Test
    void uncommonHelm_shouldGiveTenHitPoints() {
        Helm helm = new Helm(ArtifactTier.UNCOMMON);

        assertEquals(10, helm.getHitPointsBonus());
    }

    @Test
    void rareHelm_shouldGiveFifteenHitPoints() {
        Helm helm = new Helm(ArtifactTier.RARE);

        assertEquals(15, helm.getHitPointsBonus());
    }

    @Test
    void epicHelm_shouldGiveTwentyHitPoints() {
        Helm helm = new Helm(ArtifactTier.EPIC);

        assertEquals(20, helm.getHitPointsBonus());
    }

    @Test
    void legendaryHelm_shouldGiveTwentyFiveHitPoints() {
        Helm helm = new Helm(ArtifactTier.LEGENDARY);

        assertEquals(25, helm.getHitPointsBonus());
    }

    @Test
    void helm_shouldGiveNoAttackBonus() {
        for (ArtifactTier tier : ArtifactTier.values()) {
            Helm helm = new Helm(tier);

            assertEquals(
                0,
                helm.getAttackBonus(),
                "Helm should not provide attack bonus for " + tier
            );
        }
    }

    @Test
    void helm_shouldGiveNoDefenseBonus() {
        for (ArtifactTier tier : ArtifactTier.values()) {
            Helm helm = new Helm(tier);

            assertEquals(
                0,
                helm.getDefenseBonus(),
                "Helm should not provide defense bonus for " + tier
            );
        }
    }

    @Test
    void helm_shouldHaveHelmArtifactType() {
        Helm helm = new Helm(ArtifactTier.RARE);

        assertEquals("helm", helm.getArtifactType());
    }

    @Test
    void helm_shouldGenerateOneOfValidHelmTypes() {
        Helm helm = new Helm(ArtifactTier.COMMON);

        String name = helm.toString();

        assertNotNull(name);

        boolean validType =
            name.contains("Leather Cap")
            || name.contains("Chain Helm")
            || name.contains("Plate Helm")
            || name.contains("Magic Hat");

        assertTrue(validType);
    }

    @Test
    void namedHelmConstructor_shouldPreserveGivenValues() {
        Helm helm = new Helm(
            "Dragon Helm",
            ArtifactTier.LEGENDARY,
            0,
            100,
            50
        );

        assertEquals("Dragon Helm", helm.getName());
        assertEquals("helm", helm.getArtifactType());
        assertEquals(0, helm.getAttackBonus());
        assertEquals(100, helm.getDefenseBonus());
        assertEquals(50, helm.getHitPointsBonus());
    }
}

