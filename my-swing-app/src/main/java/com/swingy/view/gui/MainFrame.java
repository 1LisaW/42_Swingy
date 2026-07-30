package com.swingy.view.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private CreateHeroPanel createHeroPanel;
    private SelectHeroFromListPanel selectHeroFromListPanel;

    public MainFrame() {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Create panels
        mainMenuPanel = new MainMenuPanel();
        createHeroPanel = new CreateHeroPanel();
        gamePanel = new GamePanel();
        selectHeroFromListPanel = new SelectHeroFromListPanel();
        // this.frame = new JFrame("Swingy");
        // this.cardLayout = new CardLayout();
        // this.mainPanel = new JPanel(this.cardLayout);

        // Register panels with names
        container.add(mainMenuPanel, "MENU");
        container.add(gamePanel, "GAME");
        container.add(createHeroPanel, "CREATE");
        container.add(selectHeroFromListPanel, "SELECT");

        add(container);
        setTitle("Swingy");
        // setContentPane(this.mainPanel);

        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // mainMenuPanel = new MainMenuPanel();

        mainMenuPanel.addNewGameListener(e -> showPanel("CREATE"));
        mainMenuPanel.addLoadHeroesButtonListener(e -> showPanel("SELECT"));

    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public CreateHeroPanel getCreateHeroPanel() {
        return createHeroPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public SelectHeroFromListPanel getSelectHeroFromListPanel() {
        return selectHeroFromListPanel;
    }

    public void addLoadHeroesButtonListener(ActionListener listener) {
        mainMenuPanel.addLoadHeroesButtonListener(listener);
    }
}