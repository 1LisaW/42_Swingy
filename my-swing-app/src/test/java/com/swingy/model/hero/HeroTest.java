package com.swingy.model;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

import com.swingy.model.Hero;

class HeroTest {
    private Hero hero;

    @BeforeEach
    void setUp() {
        hero = new Hero(
            "HeroName",
            "warrior",
            1,
            0,
            3,
            4,
            5
        );
    }

    @Test
    void constructor_shouldCreateArmorWithGivenNameAndType() {
        assertNotNull(hero);
        assertEquals("HeroName", hero.getName());
        assertEquals("warrior", hero.getArchetype());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExperience());
        assertEquals(3, hero.getHitPoints());
        assertEquals(4, hero.getAttack());
        assertEquals(5, hero.getDefense());
    }

    @Test
    void hero_shouldGetOriginalId() {
        assertEquals(-1, hero.getOriginalId());
    }

    @Test
    void hero_shouldSetDefenseArtifact() {
        Armor armor = new Armor(ArtifactTier.COMMON);
        assertEquals(5, hero.getDefense());
        hero.addArtifact(armor);
        assertEquals(10, hero.getDefense());
    }

    @Test
    void hero_shouldSetAttackArtifact() {
        Weapon weapon = new Weapon(ArtifactTier.COMMON);
        assertEquals(4, hero.getAttack());
        hero.addArtifact(weapon);
        assertEquals(9, hero.getAttack());
    }

    @Test
    void hero_shouldSetHitPointsArtifact() {
        Helm helm = new Helm(ArtifactTier.COMMON);
        assertEquals(3, hero.getHitPoints());
        hero.addArtifact(helm);
        assertEquals(8, hero.getHitPoints());
    }

    @Test
    void hero_shouldApplyDamage() {
        assertEquals(3, hero.getHitPoints());
        hero.applyDamage(2);
        assertEquals(1, hero.getHitPoints());
    }

    @Test
    void hero_shouldProvideFormattedString() {
        assertEquals("hero|HeroName|warrior|1|0|3|4|5\n", hero.toFormattedString());
    }

    @Test
    void hero_shouldProvideRepoFormat() {
        assertEquals("0|hero|HeroName|warrior|1|0|3|4|5\n", hero.toRepoFormat(new AtomicInteger(0)));
    }

}
