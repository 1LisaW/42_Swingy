package com.swingy.view.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

import com.swingy.controller.GameController;
import com.swingy.controller.NavigationController;
import com.swingy.controller.MainMenuAction;
import com.swingy.controller.GamePanelAction;
import com.swingy.controller.ExitAction;

class MainFrame extends JFrame {
    private final GameController controller;
    private CardLayout cardLayout;
    private JPanel container;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private CreateHeroPanel createHeroPanel;
    private SelectHeroFromListPanel selectHeroFromListPanel;

    public MainFrame(GameController controller) {
        this.controller = controller;
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        NavigationController navigator = new NavigationController(cardLayout, container);

        MainMenuAction mainMenuAction = new MainMenuAction(navigator);
        GamePanelAction gamePanelAction = new GamePanelAction(navigator);
        ExitAction exitAction = new ExitAction(this);

        // Create panels
        mainMenuPanel = new MainMenuPanel(exitAction);
        createHeroPanel = new CreateHeroPanel(mainMenuAction, gamePanelAction, controller);
        gamePanel = new GamePanel(controller);
        selectHeroFromListPanel = new SelectHeroFromListPanel(mainMenuAction, gamePanelAction, controller);

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
