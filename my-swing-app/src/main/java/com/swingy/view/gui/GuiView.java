package com.swingy.view.gui;

import java.util.List;

import com.swingy.view.View;
import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.model.GameMap;
import com.swingy.model.BattleSimulator;

import com.swingy.model.Artifact;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.SwingConstants;


public class GuiView extends View {
    private MainFrame frame;
    // private JPanel mainPanel;
    // private CardLayout cardLayout;


    public GuiView() {
        frame = new MainFrame();
        // Initialize the view
        // this.frame = new JFrame("Swingy");
        // this.cardLayout = new CardLayout();
        // this.mainPanel = new JPanel(this.cardLayout);
        // this.mainPanel.add(new MainMenuPanel(), "MENU");
        // this.mainPanel.add(new GamePanel(), "GAME");
        // this.mainPanel.add(new CreateHeroPanel(), "CREATE");
        // this.frame.setContentPane(this.mainPanel);

        // this.frame.setSize(1200, 600);
        // this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// frame.setVisible(true);
    }

    @Override
    public void start() {
        // this.frame.showPanel("MENU");
        this.frame.setVisible(true);
        // this
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
    public void displayBattleLog(BattleSimulator battleSimulator) {

    }

    @Override
    public void displayMainMenu() {
        this.frame.showPanel("MENU");

    //    this.cardLayout.show(this.mainPanel, "MENU");

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
    public void displayChooseHeroFromList(List<Hero> heroes) {
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
}
