package com.swingy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleSimulatorTest {

    private Hero hero;
    private Villain villain;
    private BattleSimulator battleSimulator;

    @BeforeEach
    void setUp() {
        hero = new HeroBuilder()
                .setName("Hero")
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(100)
                .setAttack(20)
                .setDefense(10)
                .build();

        villain = new Villain(
                1
        );

        battleSimulator = new BattleSimulator(hero, villain);
    }

    @Test
    void shouldInitializeBattleCorrectly() {
        assertSame(hero, battleSimulator.getHero());
        assertSame(villain, battleSimulator.getVillain());
        assertEquals(BattleResult.NOT_STARTED,
                battleSimulator.getBattleResult());
        assertNotNull(battleSimulator.getLog());
        assertTrue(battleSimulator.getLog().isEmpty());
        assertNull(battleSimulator.getArtifact());
    }

    @Test
    void shouldCalculateExperienceFromVillainLevel() {
        assertEquals(300, battleSimulator.getExperience());
    }

    @Test
    void shouldCalculateExperienceForHigherLevelVillain() {
        Villain highLevelVillain = new Villain(
                5
        );

        BattleSimulator simulator =
                new BattleSimulator(hero, highLevelVillain);

        assertEquals(1500, simulator.getExperience());
    }

    @Test
    void shouldReturnHeroAndVillain() {
        assertSame(hero, battleSimulator.getHero());
        assertSame(villain, battleSimulator.getVillain());
    }

    @Test
    void shouldStartWithEmptyLog() {
        assertNotNull(battleSimulator.getLog());
        assertTrue(battleSimulator.getLog().isEmpty());
    }

    @Test
    void shouldDrawWhenNeitherCanDamageTheOther() {
        Hero weakHero = new HeroBuilder()
                .setName("Weak Hero")
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(100)
                .setAttack(5)
                .setDefense(20)
                .build();

        Villain strongDefenseVillain = new Villain(
                1
        );
        strongDefenseVillain.setStats(1, 1, 20);

        BattleSimulator simulator =
                new BattleSimulator(weakHero, strongDefenseVillain);

        int result = simulator.fight();

        assertEquals(0, result);
        assertEquals(
                BattleResult.DRAW,
                simulator.getBattleResult()
        );
        assertEquals(1, simulator.getLog().size());
        assertEquals(
                "This villain is too tough for you. Just as you are for him. Come back when you are stronger.",
                simulator.getLog().get(0)
        );
    }

    @Test
    void shouldWinWhenHeroCanDefeatVillain() {
        Hero strongHero = new HeroBuilder()
                .setName("Strong Hero")
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(1000)
                .setAttack(1000)
                .setDefense(1000)
                .build();

        Villain weakVillain = new Villain(
                1
        );

        BattleSimulator simulator =
                new BattleSimulator(strongHero, weakVillain);

        int result = simulator.fight();

        assertEquals(1, result);
        assertEquals(
                BattleResult.WIN,
                simulator.getBattleResult()
        );
        assertTrue(
                simulator.getLog().contains("Hero won the battle!")
        );
    }

    @Test
    void shouldLoseWhenVillainCanDefeatHero() {
        Hero weakHero = new HeroBuilder()
                .setName("Weak Hero")
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(1)
                .setAttack(1)
                .setDefense(0)
                .build();

        Villain strongVillain = new Villain(
                1
        );
        strongVillain.setStats(10, 10, 10);

        BattleSimulator simulator =
                new BattleSimulator(weakHero, strongVillain);

        int result = simulator.fight();

        assertEquals(-1, result);
        assertEquals(
                BattleResult.LOSE,
                simulator.getBattleResult()
        );
        assertTrue(
                simulator.getLog().contains("Hero lost the battle.")
        );
    }

    @Test
    void shouldCollectBattleExperience() {
        Hero testHero = new HeroBuilder()
                .setName("Hero")
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(100)
                .setAttack(20)
                .setDefense(10)
                .build();

        BattleSimulator simulator =
                new BattleSimulator(testHero, villain);

        simulator.collectBattleExperience();

        assertEquals(300, testHero.getExperience());
    }

    @Test
    void shouldInitiallyHaveNoArtifact() {
        assertNull(battleSimulator.getArtifact());
    }

    @Test
    void shouldNotUpdateHeroArtifactWhenArtifactDoesNotExist() {
        assertDoesNotThrow(
                () -> battleSimulator.updateHeroArtifact()
        );

        assertNull(battleSimulator.getArtifact());
    }

    @Test
    void shouldGenerateArtifact() {
        Artifact artifact = null;
        while (artifact == null)
            artifact = battleSimulator.generateArtifact();

        assertNotNull(artifact);
        assertSame(artifact, battleSimulator.getArtifact());
    }

    @Test
    void shouldUpdateHeroWithGeneratedArtifact() {
        Artifact artifact = null;
        while (artifact == null)
            artifact = battleSimulator.generateArtifact();

        assertNotNull(artifact);

        assertDoesNotThrow(
                () -> battleSimulator.updateHeroArtifact()
        );
    }

    @Test
    void runShouldReturnValidResult() {
        int result = battleSimulator.run();

        assertTrue(result == 0 || result == 1);

        if (result == 1) {
            assertEquals(
                    BattleResult.RUN_AWAY,
                    battleSimulator.getBattleResult()
            );
        } else {
            assertEquals(
                    BattleResult.FAIL_TO_RUN,
                    battleSimulator.getBattleResult()
            );
        }
    }

    @Test
    void shouldReturnSameResultWhenRunIsCalledAgain() {
        int firstResult = battleSimulator.run();
        int secondResult = battleSimulator.run();

        assertEquals(firstResult, secondResult);
    }
}
