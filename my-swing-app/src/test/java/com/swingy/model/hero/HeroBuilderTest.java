package com.swingy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroBuilderTest {

    @Test
    void shouldBuildHeroWithAllProvidedValues() {
        Hero hero = new HeroBuilder()
                .setName("Aragorn")
                .setArchetype("Warrior")
                .setLevel(5)
                .setExperience(1200)
                .setHitPoints(150)
                .setAttack(30)
                .setDefense(25)
                .build();

        assertEquals("Aragorn", hero.getName());
        assertEquals("Warrior", hero.getArchetype());
        assertEquals(5, hero.getLevel());
        assertEquals(1200, hero.getExperience());
        assertEquals(150, hero.getHitPoints());
        assertEquals(30, hero.getAttack());
        assertEquals(25, hero.getDefense());
    }
}
