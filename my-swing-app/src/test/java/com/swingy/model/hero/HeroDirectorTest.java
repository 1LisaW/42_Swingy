package com.swingy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroDirectorTest {

    private HeroDirector heroDirector;

    @BeforeEach
    void setUp() {
        HeroBuilder heroBuilder = new HeroBuilder();
        heroDirector = new HeroDirector(heroBuilder);
    }

    @Test
    void shouldConstructWizard() {
        Hero hero = heroDirector.constructNewHero(
                "Gandalf",
                HeroArchetype.WIZARD
        );

        assertEquals("Gandalf", hero.getName());
        assertEquals("wizard", hero.getArchetype());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExperience());
        assertEquals(5, hero.getHitPoints());
        assertEquals(5, hero.getAttack());
        assertEquals(0, hero.getDefense());
    }

    @Test
    void shouldConstructWarrior() {
        Hero hero = heroDirector.constructNewHero(
                "Aragorn",
                HeroArchetype.WARRIOR
        );

        assertEquals("Aragorn", hero.getName());
        assertEquals("warrior", hero.getArchetype());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExperience());
        assertEquals(4, hero.getHitPoints());
        assertEquals(3, hero.getAttack());
        assertEquals(3, hero.getDefense());
    }

    @Test
    void shouldConstructBarbarian() {
        Hero hero = heroDirector.constructNewHero(
                "Conan",
                HeroArchetype.BARBARIAN
        );

        assertEquals("Conan", hero.getName());
        assertEquals("barbarian", hero.getArchetype());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExperience());
        assertEquals(4, hero.getHitPoints());
        assertEquals(6, hero.getAttack());
        assertEquals(0, hero.getDefense());
    }

    @Test
    void shouldConstructHeroFromRepo() {
        Hero hero = heroDirector.constructHeroFromRepo(
                "Gandalf",
                "wizard",
                5,
                1200,
                150,
                30,
                25
        );

        assertEquals("Gandalf", hero.getName());
        assertEquals("wizard", hero.getArchetype());
        assertEquals(5, hero.getLevel());
        assertEquals(1200, hero.getExperience());
        assertEquals(150, hero.getHitPoints());
        assertEquals(30, hero.getAttack());
        assertEquals(25, hero.getDefense());
    }

    @Test
    void shouldThrowExceptionForNullArchetype() {
        assertThrows(
                NullPointerException.class,
                () -> heroDirector.constructNewHero("Hero", null)
        );
    }
}
