package com.swingy.view.console;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.Hero;
import com.swingy.model.HeroArchetype;
import com.swingy.model.HeroCredentials;
import com.swingy.view.ViewManager;

class ConsoleViewTest {

    private GameController controller;
    private ViewManager viewManager;
    private ConsoleView view;

    private ByteArrayOutputStream output;
    private PrintStream originalOut;
    private java.io.InputStream originalIn;

    @BeforeEach
    void setUp() {
        controller = mock(GameController.class);
        viewManager = mock(ViewManager.class);

        view = new ConsoleView(controller, viewManager);
        view.setTextDelay(0);

        originalOut = System.out;
        originalIn = System.in;

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private String consoleOutput() {
        System.out.flush();
        return output.toString();
    }

    private String cleanOutput() {
        return consoleOutput()
            .replaceAll("\\u001B\\[[;\\d]*m", "");
    }

    private void setRunning(boolean value) throws Exception {
        Field field = ConsoleView.class.getDeclaredField("isRunning");
        field.setAccessible(true);
        field.set(view, value);
    }

    private void setConsoleThread(Thread thread) throws Exception {
        Field field = ConsoleView.class.getDeclaredField("consoleThread");
        field.setAccessible(true);
        field.set(view, thread);
    }

    @Test
    void displayBattleLog_emptyList_printsHeader() {
        view.displayBattleLog(List.of());

        assertTrue(cleanOutput().contains("BATTLE LOG"));
    }

    @Test
    void displayBattleLog_printsEveryRecord() {
        view.displayBattleLog(List.of(
                "Hero attacks",
                "Villain attacks",
                "Hero wins"
        ));

        String result = cleanOutput();

        assertTrue(result.contains("BATTLE LOG"));
        assertTrue(result.contains("Hero attacks"));
        assertTrue(result.contains("Villain attacks"));
        assertTrue(result.contains("Hero wins"));
    }

    @Test
    void displayGameResult_win_printsWinMessage() {
        view.displayGameResult(true);

        assertTrue(
                cleanOutput().contains(
                        "CONGRATS! HERO SUCCESSFULLY ESCAPED MAP."
                )
        );
    }

    @Test
    void displayGameResult_loss_printsLossMessage() {
        view.displayGameResult(false);

        assertTrue(cleanOutput().contains("YOU HAVE DIED..."));
    }

    @Test
    void displayOnHeroRun_success_printsSuccessMessage() {
        view.displayOnHeroRun(true);

        assertTrue(
                cleanOutput().contains(
                        "Hero successfully ran out of danger."
                )
        );
    }

    @Test
    void displayOnHeroRun_failure_printsFailureMessage() {
        view.displayOnHeroRun(false);

        assertTrue(
                cleanOutput().contains(
                        "Hero couldn't ran away. Prepare for a fight!"
                )
        );
    }

    @Test
    void displayOnIncorrectInput_printsError() {
        view.displayOnIncorrectInput();

        assertTrue(
                cleanOutput().contains(
                        "Invalid input. Please try again."
                )
        );
    }

    @Test
    void displayUseArtifact_printsArtifactAndOptions() {
        var artifact = mock(com.swingy.model.Artifact.class);

        when(artifact.getName()).thenReturn("Sword");
        when(artifact.getAttackBonus()).thenReturn(10);
        when(artifact.getDefenseBonus()).thenReturn(5);
        when(artifact.getHitPointsBonus()).thenReturn(20);

        view.displayUseArtifact(artifact);

        String result = cleanOutput();

        assertTrue(result.contains("Congrats! You got a new Artifact!"));
        assertTrue(result.contains("Sword"));
        assertTrue(result.contains("ATK"));
        assertTrue(result.contains("DEF"));
        assertTrue(result.contains("HP"));
        assertTrue(result.contains("1. use"));
        assertTrue(result.contains("2. drop"));
    }

    @Test
    void displayMainMenu_whenRunning_printsMenu() throws Exception {
        setRunning(true);

        view.displayMainMenu();

        String result = cleanOutput();

        assertTrue(result.contains("WELCOME TO SWINGY!"));
        assertTrue(result.contains("1. Create Hero"));
        assertTrue(result.contains("2. Load Hero"));
        assertTrue(result.contains("3. Exit"));
    }

    @Test
    void displayMainMenu_whenNotRunning_printsNothing() throws Exception {
        setRunning(false);

        view.displayMainMenu();

        assertEquals("", consoleOutput());
    }

    @Test
    void displayMainMenuStatus_whenRunning_printsCreationMessage()
            throws Exception {

        setRunning(true);

        view.displayMainMenuStatus(1);

        assertTrue(
                cleanOutput().contains(
                        "Creating new hero ..."
                )
        );
    }

    @Test
    void displayMainMenuStatus_loadHero_printsLoadingMessage()
            throws Exception {

        setRunning(true);

        view.displayMainMenuStatus(2);

        assertTrue(
                cleanOutput().contains(
                        "Loading list of heroes..."
                )
        );
    }

    @Test
    void displayMainMenuStatus_exit_printsGoodbyeMessage()
            throws Exception {

        setRunning(true);

        view.displayMainMenuStatus(3);

        assertTrue(
                cleanOutput().contains(
                        "It was nice to see you. Have a nice day!"
                )
        );
    }

    @Test
    void displayLevelUp_printsMessageAndHero() {
        Hero hero = mock(Hero.class);

        when(hero.getName()).thenReturn("Arthur");
        when(hero.getArchetype()).thenReturn("warrior");
        when(hero.getLevel()).thenReturn(3);
        when(hero.getBaseHitPoints()).thenReturn(100);
        when(hero.getBonusHitPoints()).thenReturn(10);
        when(hero.getExperience()).thenReturn(50);
        when(hero.getMaxExperience()).thenReturn(100);
        when(hero.getBaseAttack()).thenReturn(20);
        when(hero.getBonusAttack()).thenReturn(5);
        when(hero.getBaseDefense()).thenReturn(15);
        when(hero.getBonusDefense()).thenReturn(3);

        view.displayLevelUp(hero);

        String result = cleanOutput();

        assertTrue(result.contains("HERO LEVELED UP!"));
        assertTrue(result.contains("Arthur"));
        assertTrue(result.contains("Warrior"));
    }

    @Test
    void promptChooseFromHeroList_printsHeroes() {
        Hero hero = mock(Hero.class);

        when(hero.getName()).thenReturn("Arthur");
        when(hero.getArchetype()).thenReturn("warrior");
        when(hero.getLevel()).thenReturn(2);
        when(hero.getBaseHitPoints()).thenReturn(100);
        when(hero.getBonusHitPoints()).thenReturn(0);
        when(hero.getExperience()).thenReturn(10);
        when(hero.getMaxExperience()).thenReturn(100);
        when(hero.getBaseAttack()).thenReturn(20);
        when(hero.getBonusAttack()).thenReturn(0);
        when(hero.getBaseDefense()).thenReturn(10);
        when(hero.getBonusDefense()).thenReturn(0);

        view.promptChooseFromHeroList(List.of(hero));

        String result = cleanOutput();

        assertTrue(result.contains("Choose a hero from list"));
        assertTrue(result.contains("Arthur"));
    }

    @Test
    void displayMap_printsHeroAndVillains() {
        var map = mock(com.swingy.model.GameMap.class);

        when(map.getSize()).thenReturn(2);
        when(map.getHeroPosition()).thenReturn(0);
        when(map.getVillainAtPos(0)).thenReturn(0);
        when(map.getVillainAtPos(1)).thenReturn(1);
        when(map.getVillainAtPos(2)).thenReturn(2);
        when(map.getVillainAtPos(3)).thenReturn(0);

        view.displayMap(map);

        String result = cleanOutput();

        assertTrue(result.contains("Current map state:"));
        assertTrue(result.contains(" H "));
        assertTrue(result.contains(" V "));
    }

    @Test
    void getUserIntInputInRange_acceptsValidInput()
            throws Exception {

        setRunning(true);

        System.setIn(
                new ByteArrayInputStream(
                        "2\n".getBytes()
                )
        );

        int result = view.getUserIntInputInRange(3);

        assertEquals(2, result);
    }

    @Test
    void getUserIntInputInRange_rejectsInvalidThenAcceptsValid()
            throws Exception {

        setRunning(true);

        System.setIn(
                new ByteArrayInputStream(
                        "abc\n2\n".getBytes()
                )
        );

        int result = view.getUserIntInputInRange(3);

        assertEquals(2, result);

        assertTrue(
                cleanOutput().contains(
                        "Invalid input. Please try again."
                )
        );
    }

   @Test
    void createHeroCredentials_createsWizard() {
        ConsoleView spyView = spy(view);

        doReturn("Gandalf")
                .doReturn("1")
                .when(spyView)
                .getUserInput(anyString());

        HeroCredentials credentials = spyView.createHeroCredentials();

        assertEquals("Gandalf", credentials.getName());
        assertEquals(HeroArchetype.WIZARD, credentials.getHeroType());

        verify(spyView).getUserInput("Enter hero name");
        verify(spyView).getUserInput("Choose an option ");
    }

    @Test
    void createHeroCredentials_createsWarrior() {
        ConsoleView spyView = spy(view);

        doReturn("Aragorn")
                .doReturn("2")
                .when(spyView)
                .getUserInput(anyString());

        HeroCredentials credentials = spyView.createHeroCredentials();

        assertEquals("Aragorn", credentials.getName());
        assertEquals(HeroArchetype.WARRIOR, credentials.getHeroType());

        verify(spyView).getUserInput("Enter hero name");
        verify(spyView).getUserInput("Choose an option ");
    }

    @Test
    void createHeroCredentials_createsBarbarian() {
        ConsoleView spyView = spy(view);

        doReturn("Conan")
                .doReturn("3")
                .when(spyView)
                .getUserInput(anyString());

        HeroCredentials credentials = spyView.createHeroCredentials();

        assertEquals("Conan", credentials.getName());
        assertEquals(HeroArchetype.BARBARIAN, credentials.getHeroType());

        verify(spyView).getUserInput("Enter hero name");
        verify(spyView).getUserInput("Choose an option ");
    }

    @Test
    void hide_setsRunningToFalse() throws Exception {
        setRunning(true);

        Thread thread = mock(Thread.class);
        setConsoleThread(thread);

        view.hide();

        Field field =
                ConsoleView.class.getDeclaredField("isRunning");

        field.setAccessible(true);

        assertFalse(field.getBoolean(view));

        verify(thread).interrupt();
    }
}
