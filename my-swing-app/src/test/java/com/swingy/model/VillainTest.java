package com.swingy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VillainTest {

    @Test
    void shouldCreateVillainWithCorrectLevel() {
        Villain villain = new Villain(5);

        assertEquals(5, villain.getLevel());
    }

    @Test
    void shouldGeneratePositiveHitPoints() {
        Villain villain = new Villain(5);

        assertTrue(villain.getHitPoints() > 0);
    }

    @Test
    void shouldGenerateNonNegativeAttack() {
        Villain villain = new Villain(5);

        assertTrue(villain.getAttack() >= 0);
    }

    @Test
    void shouldGenerateNonNegativeDefense() {
        Villain villain = new Villain(5);

        assertTrue(villain.getDefense() >= 0);
    }

    @Test
    void shouldGenerateStatsWithinExpectedRange() {
        Villain villain = new Villain(5);

        int totalStats =
                villain.getHitPoints()
                + villain.getAttack()
                + villain.getDefense();

        assertTrue(villain.getHitPoints() >= 1);
        assertTrue(villain.getAttack() >= 1);
        assertTrue(villain.getDefense() >= 0);

        /*
         * For level 5:
         * maxPoints = 5 + 5 * 5 = 30
         * points = random value between 5 and 34
         */
        assertTrue(totalStats >= 5);
        assertTrue(totalStats <= 34);
    }

    @Test
    void shouldNotHaveHitPointsBelowZeroAfterDamage() {
        Villain villain = new Villain(1);

        villain.applyDamage(1000);

        assertEquals(0, villain.getHitPoints());
    }

    @Test
    void shouldReduceHitPointsWhenDamageIsApplied() {
        Villain villain = new Villain(1);

        int initialHitPoints = villain.getHitPoints();

        villain.applyDamage(1);

        assertEquals(
                initialHitPoints - 1,
                villain.getHitPoints()
        );
    }

    @Test
    void shouldApplyMultipleDamage() {
        Villain villain = new Villain(1);

        int initialHitPoints = villain.getHitPoints();

        villain.applyDamage(1);
        villain.applyDamage(1);

        assertEquals(
                Math.max(0, initialHitPoints - 2),
                villain.getHitPoints()
        );
    }

    @Test
    void shouldNotChangeHitPointsWhenZeroDamageIsApplied() {
        Villain villain = new Villain(5);

        int initialHitPoints = villain.getHitPoints();

        villain.applyDamage(0);

        assertEquals(
                initialHitPoints,
                villain.getHitPoints()
        );
    }

    @Test
    void shouldNotIncreaseHitPointsWhenNegativeDamageIsApplied() {
        Villain villain = new Villain(5);

        int initialHitPoints = villain.getHitPoints();

        villain.applyDamage(-10);

        assertEquals(
                initialHitPoints,
                villain.getHitPoints()
        );
    }

    @Test
    void shouldReturnCorrectFormattedString() {
        Villain villain = new Villain(5);

        String result = villain.toFormattedString("|");

        String expected =
                "villain"
                + "|level: " + villain.getLevel()
                + "|HP: " + villain.getHitPoints()
                + "|ATK: " + villain.getAttack()
                + "|DEF: " + villain.getDefense()
                + "\n";

        assertEquals(expected, result);
    }

    @Test
    void shouldUseProvidedDelimiter() {
        Villain villain = new Villain(3);

        String result = villain.toFormattedString(";");

        assertTrue(result.contains(";level: "));
        assertTrue(result.contains(";HP: "));
        assertTrue(result.contains(";ATK: "));
        assertTrue(result.contains(";DEF: "));
    }

    @Test
    void shouldExposeCorrectConstants() {
        assertEquals(1, Villain.MIN_VILLAIN_LEVEL);
        assertEquals(10, Villain.MAX_VILLAIN_LEVEL);
        assertEquals(1, Villain.MIN_VILLAIN_STRENGTH);
        assertEquals(100, Villain.MAX_VILLAIN_STRENGTH);
    }
}
