package com.swingy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtifactFactoryTest {

    private final ArtifactFactory factory = ArtifactFactory.getInstance();

    @Test
    void getInstance_returnsSameInstance() {
        ArtifactFactory first = ArtifactFactory.getInstance();
        ArtifactFactory second = ArtifactFactory.getInstance();

        assertSame(first, second);
    }

    @Test
    void createArtifactFromRepo_createsWeapon() {
        Artifact artifact = factory.createArtifactFromRepo(
            "Sword",
            "weapon",
            ArtifactTier.RARE,
            10,
            0,
            5
        );

        assertNotNull(artifact);
        assertInstanceOf(Weapon.class, artifact);
    }

    @Test
    void createArtifactFromRepo_createsArmor() {
        Artifact artifact = factory.createArtifactFromRepo(
            "Steel Armor",
            "armor",
            ArtifactTier.EPIC,
            0,
            15,
            10
        );

        assertNotNull(artifact);
        assertInstanceOf(Armor.class, artifact);
    }

    @Test
    void createArtifactFromRepo_createsHelm() {
        Artifact artifact = factory.createArtifactFromRepo(
            "Iron Helm",
            "helm",
            ArtifactTier.COMMON,
            0,
            5,
            10
        );

        assertNotNull(artifact);
        assertInstanceOf(Helm.class, artifact);
    }

    @Test
    void createArtifactFromRepo_isCaseInsensitive() {
        Artifact artifact = factory.createArtifactFromRepo(
            "Sword",
            "WEAPON",
            ArtifactTier.RARE,
            10,
            0,
            5
        );

        assertNotNull(artifact);
        assertInstanceOf(Weapon.class, artifact);
    }

    @Test
    void createArtifactFromRepo_invalidType_throwsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> factory.createArtifactFromRepo(
                "Unknown",
                "potion",
                ArtifactTier.COMMON,
                0,
                0,
                0
            )
        );

        assertEquals(
            "Invalid artifact type: potion",
            exception.getMessage()
        );
    }

}
