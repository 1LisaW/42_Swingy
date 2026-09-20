package com.swingy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private Hero hero;
    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        hero = new HeroBuilder()
                .setName("Aragorn")
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(100)
                .setHitPoints(100)
                .setAttack(20)
                .setDefense(10)
                .build();

        gameModel = new GameModel(hero);
    }

    @Test
    void shouldCreateGameWithHero() {
        assertNotNull(gameModel.getHero());

        assertEquals("Aragorn", gameModel.getHero().getName());
        assertEquals("warrior", gameModel.getHero().getArchetype());
        assertEquals(1, gameModel.getHero().getLevel());
        assertEquals(100, gameModel.getHero().getExperience());
        assertEquals(100, gameModel.getHero().getHitPoints());
        assertEquals(20, gameModel.getHero().getAttack());
        assertEquals(10, gameModel.getHero().getDefense());
    }

    @Test
    void shouldCreateGameMap() {
        assertNotNull(gameModel.getMap());
    }

    @Test
    void shouldCreateCopyOfHero() {
        assertNotSame(hero, gameModel.getHero());

        assertEquals(
                hero.getName(),
                gameModel.getHero().getName()
        );
    }

    @Test
    void shouldStartNewGameWithHeroLevel() {
        assertNotNull(gameModel.getMap());

        assertEquals(
                9,
                gameModel.getMap().getSize()
        );
    }

    @Test
    void shouldRestartGameUsingLastSavedHero() {
        gameModel.getHero().setExperience(999);
        gameModel.getHero().setHitPoints(10);

        gameModel.restartGame();

        assertEquals(
                "Aragorn",
                gameModel.getHero().getName()
        );
        assertEquals(
                100,
                gameModel.getHero().getExperience()
        );
        assertEquals(
                100,
                gameModel.getHero().getHitPoints()
        );
    }

    @Test
    void shouldCreateNewHeroWhenRestarting() {
        Hero originalGameHero = gameModel.getHero();

        gameModel.restartGame();

        assertNotSame(
                originalGameHero,
                gameModel.getHero()
        );
    }

    @Test
    void shouldSaveCurrentHeroAndRestartGame() {
        gameModel.getHero().setExperience(500);
        gameModel.getHero().setHitPoints(80);

        gameModel.saveAndStartNewGame();

        assertEquals(
                600,
                gameModel.getHero().getExperience()
        );
        assertEquals(
                80,
                gameModel.getHero().getHitPoints()
        );
    }

    @Test
    void shouldStartNewMapWhenStartingNewGame() {
        GameMap oldMap = gameModel.getMap();

        gameModel.startNewGame();

        assertNotSame(
                oldMap,
                gameModel.getMap()
        );
    }

    @Test
    void shouldMoveHero() {
        int initialPosition = gameModel.getMap().getHeroPosition();

        gameModel.moveHero("right");

        assertEquals(
                initialPosition + 1,
                gameModel.getMap().getHeroPosition()
        );
    }

    @Test
    void shouldMoveHeroUp() {
        int initialPosition = gameModel.getMap().getHeroPosition();

        gameModel.moveHero("up");

        assertEquals(
                initialPosition - gameModel.getMap().getSize(),
                gameModel.getMap().getHeroPosition()
        );
    }

    @Test
    void shouldMoveHeroDown() {
        int initialPosition = gameModel.getMap().getHeroPosition();

        gameModel.moveHero("down");

        assertEquals(
                initialPosition + gameModel.getMap().getSize(),
                gameModel.getMap().getHeroPosition()
        );
    }

    @Test
    void shouldMoveHeroLeft() {
        int initialPosition = gameModel.getMap().getHeroPosition();

        gameModel.moveHero("left");

        assertEquals(
                initialPosition - 1,
                gameModel.getMap().getHeroPosition()
        );
    }

    @Test
    void shouldReturnNullWhenThereIsNoOpponent() {
        assertNull(gameModel.getOpponent());
    }

    @Test
    void shouldRetreatHeroToPreviousPosition() {
        int initialPosition = gameModel.getMap().getHeroPosition();

        gameModel.moveHero("right");

        assertNotEquals(
                initialPosition,
                gameModel.getMap().getHeroPosition()
        );

        gameModel.retreatHero();

        assertEquals(
                initialPosition,
                gameModel.getMap().getHeroPosition()
        );
    }

    @Test
    void shouldNotBeGameOverWhenHeroIsAliveAndInsideMap() {
        assertTrue(gameModel.getHero().getHitPoints() > 0);

        assertFalse(gameModel.isGameOver());
    }

    @Test
    void shouldBeGameOverWhenHeroHasNoHitPoints() {
        gameModel.getHero().setHitPoints(0);

        assertTrue(gameModel.isGameOver());
    }

    @Test
    void shouldBeGameOverWhenHeroHasNegativeHitPoints() {
        gameModel.getHero().setHitPoints(-10);

        assertTrue(gameModel.isGameOver());
    }

    @Test
    void shouldNotHaveLevelClearedWhenHeroIsAliveButHasNotEscaped() {
        assertTrue(gameModel.getHero().isAlive());

        assertFalse(gameModel.levelCleared());
    }

    @Test
    void shouldReturnSameHeroLevelAfterRestart() {
        int level = gameModel.getHero().getLevel();

        gameModel.restartGame();

        assertEquals(
                level,
                gameModel.getHero().getLevel()
        );
    }
}
