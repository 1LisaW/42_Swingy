package com.swingy.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.swingy.model.Artifact;
import com.swingy.model.BattleResult;
import com.swingy.model.GameMap;
import com.swingy.model.Hero;
import com.swingy.model.HeroCredentials;
import com.swingy.model.Villain;

class GameControllerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        controller = new GameController();
    }

    @Test
    void newController_shouldStartInMainMenu() {
        assertEquals(Phases.MAIN_MENU, controller.getGamePhase());
    }

    @Test
    void setGamePhase_shouldChangeCurrentPhase() {
        controller.setGamePhase(Phases.GAMEPLAY);

        assertEquals(Phases.GAMEPLAY, controller.getGamePhase());
    }

    @Test
    void getHero_shouldReturnNull_whenGameHasNotStarted() {
        assertNull(controller.getHero());
    }

    @Test
    void getHeroLevel_shouldReturnZero_whenGameHasNotStarted() {
        assertEquals(0, controller.getHeroLevel());
    }

    @Test
    void isBattleTriggered_shouldReturnFalse_whenGameHasNotStarted() {
        assertFalse(controller.isBattleTriggered());
    }

    @Test
    void getBattleResult_shouldReturnNotStarted_whenThereIsNoBattle() {
        assertEquals(
            BattleResult.NOT_STARTED,
            controller.getBattleResult()
        );
    }

    @Test
    void getBattleLog_shouldReturnNull_whenThereIsNoBattle() {
        assertNull(controller.getBattleLog());
    }

    @Test
    void runFromBattle_shouldReturnMinusOne_whenThereIsNoBattle() {
        assertEquals(-1, controller.runFromBattle());
    }

    @Test
    void getBattleArtifact_shouldReturnNull_whenThereIsNoBattle() {
        assertNull(controller.getBattleArtifact());
    }

    @Test
    void getBattleArtifactType_shouldReturnNull_whenThereIsNoBattle() {
        assertNull(controller.getBattleArtifactType());
    }

    @Test
    void getBattleArtifactName_shouldReturnNull_whenThereIsNoBattle() {
        assertNull(controller.getBattleArtifactName());
    }

    @Test
    void isBattleProduceArtifact_shouldReturnFalse_whenThereIsNoBattle() {
        assertFalse(controller.isBattleProduceArtifact());
    }

    @Test
    void getBattleVillainData_shouldReturnEmptyString_whenThereIsNoBattle() {
        assertEquals("", controller.getBattleVillainData());
    }

    @Test
    void willLevelUp_shouldReturnFalse_whenThereIsNoBattle() {
        assertFalse(controller.willLevelUp());
    }

    @Test
    void willLevelUp_shouldReturnFalse_whenGameHasNotStarted() {
        assertFalse(controller.willLevelUp());
    }

    @Test
    void getCurrentBattleSimulator_shouldReturnNull_whenThereIsNoBattle() {
        assertNull(controller.getCurrentBattleSimulator());
    }

    @Test
    void getHeroes_shouldReturnHeroesFromRepository() {

        GameController controller = new GameController();
        controller.loadHeroesFromFile();

        assertNotNull(controller.getHeroes());
    }

    @Test
    void createHero_shouldCreateHeroFromCredentials() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setName("TestHero");
        credentials.setHeroArchetype("warrior");

        Hero hero = controller.createHero(credentials);

        assertNotNull(hero);
        assertEquals("TestHero", hero.getName());
    }
}
