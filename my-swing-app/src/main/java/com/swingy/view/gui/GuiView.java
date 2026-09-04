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



public class GuiView extends View {
    private MainFrame frame;
    // private JPanel mainPanel;
    // private CardLayout cardLayout;


    public GuiView(GameController controller, ViewManager viewManager) {
        super(controller, viewManager);
        frame = new MainFrame(controller);
        frame.getSelectHeroFromListPanel().updateHeroList(controller.getHeroes());
        // setupKeyBindings();
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
        // System.out.println(heroes);
        // this.frame.showPanel("CREATE");
        System.out.println("Opening hero selection");
        this.frame.getSelectHeroFromListPanel().updateHeroList(heroes);
        // this.frame.showPanel("SELECT");
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

    //     @Override
    // public void onCreateHero() {

    // }

    // @Override
    // public void onChooseHero() {

    // }
    @Override
    protected HeroCredentials createHeroCredentials() {
        HeroCredentials heroCredentials = new HeroCredentials();
        return heroCredentials;
    }

    // private void setupKeyBindings() {
    //     JRootPane root = frame.getRootPane();

    //     KeyStroke ctrlC = KeyStroke.getKeyStroke(
    //             KeyEvent.VK_C,
    //             InputEvent.CTRL_DOWN_MASK
    //     );

    //     root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
    //         .put(ctrlC, "switchToConsole");

    //     root.getActionMap()
    //         .put("switchToConsole", new AbstractAction() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 viewManager.switchToConsole();
    //             }
    //         });
    // }
    private void setupGlobalKeyBindings() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(e -> {
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
        });
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
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
                    frame.showPanel("GAME");
                    break;
                case BATTLE_RUN_OR_FIGHT:
                    frame.showPanel("GAME");
                    break;
                case BATTLE_RUN_RESULT:
                    frame.showPanel("GAME");
                    frame.showGamePanelPopup();
                    break;
                case BATTLE_RESULT:
                    frame.showPanel("GAME");
                    break;
                case BATTLE_ARTIFACT:
                    frame.showPanel("GAME");
                    frame.showGamePanelPopup();
                    break;
                // case GAME_OVER:
                //     frame.showPanel("GAME_OVER");
                //     break;
                default:
                    frame.showPanel("MENU");
            }
            frame.setVisible(true);
            frame.requestFocus();
        });
    }

    @Override
    public void hide() {
        SwingUtilities.invokeLater(() -> frame.setVisible(false));
    }
}
