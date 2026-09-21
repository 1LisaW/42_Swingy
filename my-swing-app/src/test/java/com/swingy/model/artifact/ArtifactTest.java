package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArtifactTest {

    /*
     * Simple concrete implementation used only for testing
     * the abstract Artifact class.
     */
    private static class TestArtifact extends Artifact {

        public TestArtifact(String artifactType, ArtifactTier tier) {
            super(artifactType, tier);
        }

        public TestArtifact(
                String name,
                String artifactType,
                ArtifactTier tier,
                int attackBonus,
                int defenseBonus,
                int hitPointsBonus) {
            super(
                name,
                artifactType,
                tier,
                attackBonus,
                defenseBonus,
                hitPointsBonus
            );
        }

        @Override
        protected String generateRandomArtifactType() {
            return "TestArtifact";
        }

        @Override
        protected int calculateDefenseBonus(ArtifactTier tier) {
            return 10;
        }

        @Override
        protected int calculateAttackBonus(ArtifactTier tier) {
            return 20;
        }

        @Override
        protected int calculateHitPointsBonus(ArtifactTier tier) {
            return 30;
        }
    }

    @Test
    void constructor_shouldInitializeArtifact() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.COMMON);

        assertEquals("weapon", artifact.getArtifactType());
        assertEquals("Common TestArtifact", artifact.getName());
        assertEquals(20, artifact.getAttackBonus());
        assertEquals(10, artifact.getDefenseBonus());
        assertEquals(30, artifact.getHitPointsBonus());
    }

    @Test
    void constructorWithValues_shouldPreserveGivenValues() {
        Artifact artifact = new TestArtifact(
            "Magic Sword",
            "weapon",
            ArtifactTier.RARE,
            50,
            40,
            30
        );

        assertEquals("Magic Sword", artifact.getName());
        assertEquals("weapon", artifact.getArtifactType());
        assertEquals(50, artifact.getAttackBonus());
        assertEquals(40, artifact.getDefenseBonus());
        assertEquals(30, artifact.getHitPointsBonus());
    }

    @Test
    void setName_shouldChangeName() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.COMMON);

        artifact.setName("New Name");

        assertEquals("New Name", artifact.getName());
    }

    @Test
    void setAttackBonus_shouldChangeAttackBonus() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.COMMON);

        artifact.setAttackBonus(100);

        assertEquals(100, artifact.getAttackBonus());
    }

    @Test
    void setDefenseBonus_shouldChangeDefenseBonus() {
        Artifact artifact =
            new TestArtifact("armor", ArtifactTier.COMMON);

        artifact.setDefenseBonus(100);

        assertEquals(100, artifact.getDefenseBonus());
    }

    @Test
    void setHitPointsBonus_shouldChangeHitPointsBonus() {
        Artifact artifact =
            new TestArtifact("armor", ArtifactTier.COMMON);

        artifact.setHitPointsBonus(100);

        assertEquals(100, artifact.getHitPointsBonus());
    }

    @Test
    void commonTier_shouldGenerateCorrectName() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.COMMON);

        assertEquals("Common TestArtifact", artifact.getName());
    }

    @Test
    void uncommonTier_shouldGenerateCorrectName() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.UNCOMMON);

        assertEquals("Uncommon TestArtifact", artifact.getName());
    }

    @Test
    void rareTier_shouldGenerateCorrectName() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.RARE);

        assertEquals("Rare TestArtifact", artifact.getName());
    }

    @Test
    void epicTier_shouldGenerateCorrectName() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.EPIC);

        assertEquals("Epic TestArtifact", artifact.getName());
    }

    @Test
    void legendaryTier_shouldGenerateCorrectName() {
        Artifact artifact =
            new TestArtifact("weapon", ArtifactTier.LEGENDARY);

        assertEquals("Legendary TestArtifact", artifact.getName());
    }

    @Test
    void toString_shouldReturnRepositoryFormat() {
        Artifact artifact = new TestArtifact(
            "Magic Sword",
            "weapon",
            ArtifactTier.RARE,
            50,
            40,
            30
        );

        String result = artifact.toString();

        assertEquals(
            "artifact|Magic Sword|weapon|Rare|30|50|40\n",
            result
        );
    }

    @Test
    void toString_shouldUseCommonTierText() {
        Artifact artifact = new TestArtifact(
            "Sword",
            "weapon",
            ArtifactTier.COMMON,
            1,
            2,
            3
        );

        assertEquals(
            "artifact|Sword|weapon|Common|3|1|2\n",
            artifact.toString()
        );
    }

    @Test
    void toString_shouldUseUncommonTierText() {
        Artifact artifact = new TestArtifact(
            "Sword",
            "weapon",
            ArtifactTier.UNCOMMON,
            1,
            2,
            3
        );

        assertEquals(
            "artifact|Sword|weapon|Uncommon|3|1|2\n",
            artifact.toString()
        );
    }

    @Test
    void toString_shouldUseEpicTierText() {
        Artifact artifact = new TestArtifact(
            "Sword",
            "weapon",
            ArtifactTier.EPIC,
            1,
            2,
            3
        );

        assertEquals(
            "artifact|Sword|weapon|Epic|3|1|2\n",
            artifact.toString()
        );
    }

    @Test
    void toString_shouldUseLegendaryTierText() {
        Artifact artifact = new TestArtifact(
            "Sword",
            "weapon",
            ArtifactTier.LEGENDARY,
            1,
            2,
            3
        );

        assertEquals(
            "artifact|Sword|weapon|Legendary|3|1|2\n",
            artifact.toString()
        );
    }
}
