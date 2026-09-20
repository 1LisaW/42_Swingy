package com.swingy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    @Test
    void shouldCalculateCorrectSizeForLevelOne() {
        GameMap map = new GameMap(1);

        assertEquals(9, map.getSize());
    }

    @Test
    void shouldCalculateCorrectSizeForLevelTwo() {
        GameMap map = new GameMap(2);

        assertEquals(15, map.getSize());
    }

    @Test
    void shouldCalculateCorrectSizeForLevelThree() {
        GameMap map = new GameMap(3);

        assertEquals(19, map.getSize());
    }

    @Test
    void shouldStartHeroInCenterOfMap() {
        GameMap map = new GameMap(1);

        int size = map.getSize();
        int expectedPosition =
                size * (size / 2) + size / 2;

        assertEquals(expectedPosition, map.getHeroPosition());
    }

    @Test
    void shouldStartWithoutOpponentAtHeroPosition() {
        GameMap map = new GameMap(1);

        assertEquals(0, map.getVillainAtPos(map.getHeroPosition()));
        assertNull(map.getOpponent());
    }

    @Test
    void shouldMoveHeroUp() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("up");

        assertEquals(
                initialPosition - map.getSize(),
                map.getHeroPosition()
        );
    }

    @Test
    void shouldMoveHeroDown() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("down");

        assertEquals(
                initialPosition + map.getSize(),
                map.getHeroPosition()
        );
    }

    @Test
    void shouldMoveHeroLeft() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("left");

        assertEquals(
                initialPosition - 1,
                map.getHeroPosition()
        );
    }

    @Test
    void shouldMoveHeroRight() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("right");

        assertEquals(
                initialPosition + 1,
                map.getHeroPosition()
        );
    }

    @Test
    void shouldRememberPreviousHeroPosition() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("right");
        map.retreatHero();

        assertEquals(
                initialPosition,
                map.getHeroPosition()
        );
    }

    @Test
    void shouldRetreatAfterMovingUp() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("up");

        assertNotEquals(
                initialPosition,
                map.getHeroPosition()
        );

        map.retreatHero();

        assertEquals(
                initialPosition,
                map.getHeroPosition()
        );
    }

    @Test
    void shouldRetreatAfterMovingDown() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("down");
        map.retreatHero();

        assertEquals(
                initialPosition,
                map.getHeroPosition()
        );
    }

    @Test
    void shouldReturnZeroForEmptyPosition() {
        GameMap map = new GameMap(1);

        int heroPosition = map.getHeroPosition();

        assertEquals(0, map.getVillainAtPos(heroPosition));
    }

    @Test
    void shouldNotBeEscapedAtStartingPosition() {
        GameMap map = new GameMap(1);

        assertFalse(map.isHeroEscaped());
    }

    @Test
    void shouldEscapeWhenMovingToTopEdge() {
        GameMap map = new GameMap(1);

        int moves = map.getSize() / 2;

        for (int i = 0; i < moves; i++) {
            map.moveHero("up");
        }

        /*
         * The hero should now be on the top row.
         *
         * Because villains are generated randomly, the test
         * checks the escape condition only if the destination
         * does not contain a villain.
         */
        if (map.getOpponent() == null) {
            assertTrue(map.isHeroEscaped());
        }
    }

    @Test
    void shouldNotChangePositionForUnknownMovement() {
        GameMap map = new GameMap(1);

        int initialPosition = map.getHeroPosition();

        map.moveHero("something");

        assertEquals(
                initialPosition,
                map.getHeroPosition()
        );
    }

    @Test
    void shouldReturnNullWhenThereIsNoOpponent() {
        GameMap map = new GameMap(1);

        assertNull(map.getOpponent());
    }

    @Test
    void shouldHavePositiveMapSize() {
        GameMap map = new GameMap(1);

        assertTrue(map.getSize() > 0);
    }
}
