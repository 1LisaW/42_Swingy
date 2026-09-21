package com.swingy.view.gui;

import java.util.List;

import com.swingy.view.View;
import com.swingy.model.Hero;
import com.swingy.model.HeroCredentials;
import com.swingy.model.Villain;
import com.swingy.model.GameMap;
import com.swingy.model.BattleSimulator;
import com.swingy.model.Artifact;
import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.view.ViewManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import java.awt.KeyEventDispatcher;


public class GuiView extends View {
    private MainFrame frame;
    private KeyEventDispatcher globalKeyDispatcher;


    public GuiView(GameController controller, ViewManager viewManager) {
        super(controller, viewManager);
        frame = new MainFrame(controller);
        frame.getSelectHeroFromListPanel().updateHeroList(controller.getHeroes());
        setupGlobalKeyBindings();
    }

    @Override
    public void start() {
        this.frame.setVisible(true);
    }

    @Override
    public void displayHeroStats(Hero hero) {

    }

    @Override
    public void displayMap(GameMap gameMap) {

    }

    @Override
    public void displayBattleParticipants(BattleSimulator battleSimulator) {

    }

    @Override
    public int promptBattleFightOrRun() {
        return 0;
    }

    @Override
    public void displayBattleLog(List<String> log) {

    }

    @Override
    public void displayMainMenu() {
        this.frame.showPanel("MENU");

    }

    @Override
    public int promptMainMenu() {
        return 1;
    }

    @Override
    public void displayMainMenuStatus(int choice) {

    }

    @Override
    public String getUserInput( String prompt) {
        return "";
    }

    @Override
    public void displayOnIncorrectInput() {

    }

    @Override
    public void promptChooseHeroClass() {

    }

    @Override
    public void promptChooseFromHeroList(List<Hero> heroes) {

    }

    // Choose hero from Repo
    @Override
    public void displayChooseHeroFromList() {
        List<Hero> heroes = this.controller.getHeroes();
        this.frame.getSelectHeroFromListPanel().updateHeroList(heroes);
    }

    @Override
    public int promptChooseHeroFromList(int maxNum) {
        return 0;
    }

    @Override
    public void displayChooseHeroFromListStatus(Hero hero) {

    }

    @Override
    public String promptHeroMove() {
        return "";
    }

    @Override
    public void displayUseArtifact(Artifact artifact) {

    }

    @Override
    public int promptUseArtifact() {
        return 0;
    }

    @Override
    public void displayOnHeroRun(boolean isSuccessful) {

    }

    @Override
    public void displayLevelUp(Hero hero) {

    }

    @Override
    public void displayGameResult(boolean isWin) {

    }

    @Override
    public void mainMenu() {
        // this.frame.showPanel("MENU");
    }

    @Override
    public void startGame(Hero hero) {
        this.controller.startGame(hero);

    }

    @Override
    protected HeroCredentials createHeroCredentials() {
        HeroCredentials heroCredentials = new HeroCredentials();
        return heroCredentials;
    }

    private void setupGlobalKeyBindings() {
        globalKeyDispatcher = e -> {
            if (e.getID() != KeyEvent.KEY_PRESSED
                    || e.getKeyCode() != KeyEvent.VK_C
                    || (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) {
                return false;
            }

            Component focused = e.getComponent();

            if (focused instanceof JTextComponent) {
                return false;
            }

            viewManager.switchToConsole();
            return true;
        };

        KeyboardFocusManager
            .getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(globalKeyDispatcher);
    }

    KeyEventDispatcher getGlobalKeyDispatcherForTest() {
        return globalKeyDispatcher;
    }


    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {

            Phases phase = this.controller.getGamePhase();

            if (phase == null) {
                frame.showPanel("MENU");
            } else {
                switch(this.controller.getGamePhase()) {
                    case MAIN_MENU:
                        frame.showPanel("MENU");
                        break;
                    case HERO_CREATION:
                        frame.showPanel("CREATE");
                        break;
                    case HERO_SELECTION:
                        frame.showPanel("SELECT");
                        break;
                    case GAMEPLAY:
                    case BATTLE_RUN_OR_FIGHT:
                    case BATTLE_RUN_RESULT:
                    case BATTLE_RESULT:
                    case BATTLE_ARTIFACT:
                    case HERO_LEVEL_UP:
                        // Call onHide when switching to GAMEPLAY
                        frame.showPanel("GAME");
                        // frame.showCurrentPopup();
                        break;
                    case GAME_OVER:
                        if (this.controller.levelCleared())
                            frame.showPanel("GAME_OVER_WON");
                        else
                            frame.showPanel("GAME_OVER_LOST");
                        break;
                    default:
                        frame.showPanel("MENU");
                }
            }
            frame.setVisible(true);
            frame.requestFocus();
            frame.showCurrentPopup();
        });
    }

    @Override
    public void hide() {
        SwingUtilities.invokeLater(() -> {
            frame.hideActivePopup();
            frame.setVisible(false);
        });
    }
}
