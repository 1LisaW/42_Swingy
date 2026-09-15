package com.swingy.view.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import com.swingy.model.GameMap;
import com.swingy.model.BattleResult;
import com.swingy.model.Hero;
import com.swingy.controller.GameController;
import com.swingy.controller.GameOverWonPanelAction;
import com.swingy.controller.GameOverLostPanelAction;
import com.swingy.controller.Phases;

import com.swingy.view.gui.ArtifactPopup;
import com.swingy.view.gui.APopup;
import com.swingy.view.gui.BattleRunOrFightPopup;

public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 82;
    private final GameController controller;
    private final GameOverWonPanelAction gameOverWonPanelAction;
    private final GameOverLostPanelAction gameOverLostPanelAction;

    private JLabel gameLabelLeft;
    private JLabel gameLabelCenter;
    private JLabel gameLabelRight;

    private ImageIcon heroIcon = null;
    final private ImageIcon weakerVillainIcon = getVillainIcon("goblin");
    final private ImageIcon equalVillainIcon = getVillainIcon("orc");
    final private ImageIcon strongerVillainIcon = getVillainIcon("golem");

    private APopup currentPopup = null;

    // private GameMap gameMap;
    private ImageIcon getVillainIcon(String name) {
        String imagePath = "/images/villain/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image scaled = icon.getImage().getScaledInstance(
            CELL_SIZE - 2, CELL_SIZE - 2, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }

    private ImageIcon getHeroIcon() {
        if (controller.getHero() == null) {
            return null;
        }
        String imagePath = "/images/hero/" + controller.getHero().getArchetype().toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image scaled = icon.getImage().getScaledInstance(
            CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }

    public GamePanel(GameOverWonPanelAction gameOverWonPanelAction, GameOverLostPanelAction gameOverLostPanelAction, GameController controller) {
        this.controller = controller;
        this.gameOverWonPanelAction = gameOverWonPanelAction;
        this.gameOverLostPanelAction = gameOverLostPanelAction;

        setLayout(new BorderLayout());


        JButton back = new JButton("Back to Menu");

        // back.addActionListener(e -> view.showScreen("MENU"));
        JPanel header = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.weighty = 1;

        gameLabelLeft = new JLabel("Left", SwingConstants.CENTER);
        gameLabelCenter = new JLabel("Game Screen", SwingConstants.CENTER);
        gameLabelRight = new JLabel("Right", SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.weightx = 0.2;
        header.add(gameLabelLeft, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        header.add(gameLabelCenter, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.2;
        header.add(gameLabelRight, gbc);

        add(header, BorderLayout.NORTH);


        // gameLabel = new JLabel("Game Screen", SwingConstants.CENTER);
        // add(gameLabel, BorderLayout.NORTH);
        add(back, BorderLayout.SOUTH);

        setFocusable(true);
        setupKeyBindings();
    }

     private void setupKeyBindings() {
        InputMap input = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actions = getActionMap();

        input.put(KeyStroke.getKeyStroke("W"), "moveUp");
        input.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        input.put(KeyStroke.getKeyStroke("S"), "moveDown");
        input.put(KeyStroke.getKeyStroke("D"), "moveRight");

        actions.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHero("up");
            }
        });

        actions.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHero("left");
            }
        });

        actions.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHero("down");
            }
        });

        actions.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHero("right");
            }
        });
    }

    private void moveHero(String movement) {
        controller.moveHero(movement);
        repaint();
        if (controller.isBattleTriggered()) {
            startBattle();
        }
        repaint();
         if (controller.isGameOver() && controller.getBattleResult() == BattleResult.WIN) {
            gameOverWonPanelAction.actionPerformed(null);
            // return;
        } else if (controller.isGameOver() && controller.getBattleResult() == BattleResult.LOSE) {
            gameOverLostPanelAction.actionPerformed(null);
            // return;
        }
    }

    // public void setMap(GameMap gameMap) {
    //     this.gameMap = gameMap;
    // }

    // public void update() {
    //     // Update the game panel based on the current map state
    //     if (gameMap == null) {
    //         return;
    //     }
    // }
    @Override
    protected void paintComponent(Graphics g) {
        GameMap gameMap = controller.getGameMap();
        super.paintComponent(g);

        int size = gameMap.getSize();
        int heroPosition = gameMap.getHeroPosition();

         // Camera follows the hero
         Point camera = calculateCamera(new Point(heroPosition % size, heroPosition / size), gameMap);
        int adjustedCameraX = Math.max(0, getWidth() / 4 - camera.x / 2);
        int cameraX = camera.x - adjustedCameraX; // Adjust for the hero's position
        int cameraY = camera.y - 50; // Adjust for the title label height

        Hero hero = controller.getHero();
        if (hero != null) {
            System.out.println("Updating game label with hero info: " + controller.getHero().getName() + " - Level: " + controller.getHero().getLevel() + ", XP: " + controller.getHero().getExperience() + "/" + controller.getHero().getMaxExperience());
            gameLabelLeft.setText("Hero: " + hero.getName() + " | Level: " + hero.getLevel());
            gameLabelCenter.setText(" HP:   " + hero.getBaseHitPoints() + " + " + hero.getBonusHitPoints() + "   "
                + "ATK:   " + hero.getBaseAttack() + " + " + hero.getBonusAttack() + "   "
                + "DEF:   " + hero.getBaseDefense() + " + " + hero.getBonusDefense() + "");
            gameLabelRight.setText(hero.getExperience() + " XP/ " + hero.getMaxExperience() + " XP");
        }
        //     gameLabel.setText(
        //         controller.getHero().getName()
        //         + " - Game Screen - "
        //         + controller.getHero().getLevel() + " "
        //         + "(HP: " + hero.getBaseHitPoints() + " + " + hero.getBonusHitPoints() + ") "
        //         + "(ATK: " + hero.getBaseAttack() + " + " + hero.getBonusAttack() + ") "
        //         + "(DEF: " + hero.getBaseDefense() + " + " + hero.getBonusDefense() + ")     "
        //         + controller.getHero().getExperience() + " XP/ "
        //         + controller.getHero().getMaxExperience() + " XP");
        // }

        drawBoard(g, gameMap, cameraX, cameraY);
        drawVillains(g, gameMap, cameraX, cameraY);
        if (hero != null && heroIcon == null) {
            heroIcon = getHeroIcon();
            System.out.println("Hero icon set for archetype: " + controller.getHero().getArchetype());
        }
        if (hero != null) {
            drawHero(g, heroPosition, cameraX, cameraY);
        }
        // drawHero(g, heroPosition, cameraX, cameraY);
    }

    private Point calculateCamera(Point heroPos, GameMap gameMap) {
        int mapPixelWidth = gameMap.getSize() * CELL_SIZE;
        int mapPixelHeight = gameMap.getSize() * CELL_SIZE + CELL_SIZE;

        int cameraX = heroPos.x * CELL_SIZE
                + CELL_SIZE / 2
                - getWidth() / 2;

        int cameraY = heroPos.y * CELL_SIZE
                + CELL_SIZE / 2
                - getHeight() / 2;

        // Don't show outside the map
        cameraX = Math.max(0, cameraX);
        cameraY = Math.max(0, cameraY);

        cameraX = Math.min(
                cameraX,
                Math.max(0, mapPixelWidth - getWidth())
        );

        cameraY = Math.min(
                cameraY,
                Math.max(0, mapPixelHeight - getHeight())
        );

        return new Point(cameraX, cameraY);
    }

    private void drawBoard(
        Graphics g,
        GameMap gameMap,
        int cameraX,
        int cameraY
    ) {

        // g.setColor(Color.LIGHT_GRAY);

        int mapSize = gameMap.getSize();

        // Which map cells are visible?
        int startX = Math.max(0, cameraX / CELL_SIZE);
        int startY = Math.max(0, cameraY / CELL_SIZE);

        int endX = Math.min(
                mapSize,
                (cameraX + getWidth()) / CELL_SIZE + 1
        );

        int endY = Math.min(
                mapSize,
                (cameraY + getHeight()) / CELL_SIZE + 1
        );

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {

                int screenX = x * CELL_SIZE - cameraX;
                int screenY = y * CELL_SIZE - cameraY;
                if ((x + y) % 2 == 0) {
                    g.setColor(new Color(220, 220, 220));
                } else {
                    g.setColor(new Color(180, 180, 180));
                }

                g.fillRect(
                        screenX,
                        screenY,
                        CELL_SIZE,
                        CELL_SIZE
                );
            };
        };
    }

    private void showBattleResultPopup() {
        ImageIcon icon = null;
        BattleResult battleResult = controller.getBattleResult();
        if (battleResult == BattleResult.WIN) {
            icon = new ImageIcon(getClass().getResource("/images/battle_won.png"));
        } else if (battleResult == BattleResult.LOSE) {
            icon = new ImageIcon(getClass().getResource("/images/battle_lost.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/images/battle_draw.png"));
        }
        Image scaled = icon.getImage().getScaledInstance(
            150, 150, Image.SCALE_SMOOTH
        );
        ImageIcon resultIcon = new ImageIcon(scaled);
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(
            panel,
            controller.getBattleLog().stream().reduce("", (acc, line) -> acc + line + "\n"),
            "Battle Results",
            JOptionPane.INFORMATION_MESSAGE,
            resultIcon
        );

        if (battleResult == BattleResult.WIN) {
            if (this.controller.isBattleProduceArtifact()) {
                this.controller.setGamePhase(Phases.BATTLE_ARTIFACT);
                ArtifactPopup artifactPopup = new ArtifactPopup(this.controller);
            }
            this.controller.collectBattleExperience();
        }
    }

    private void runBattle() {
            controller.simulateBattle();
            BattleResult battleResult = controller.getBattleResult();
            showBattleResultPopup();
    }

    private void startBattle() {
        // Implement battle logic here
        // For example, you can show a dialog or switch to a battle panel
        Object[] options = {"Run", "Fight"};
            JPanel panel = new JPanel();
            int result = JOptionPane.showOptionDialog(
                panel,
                "You met a villain! What do you want to do?",
                "Battle!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (result == 0) {
                showBattleRunResultPopup();
                // Run
            } else if (result == 1) {
                runBattle();
                // Fight
            }
    }

    private void showBattleRunResultPopup() {
        JPanel panel = new JPanel();
        if (controller.runFromBattle() == 1) {
            JOptionPane.showMessageDialog(panel, "You successfully ran away!");
        } else {
            JOptionPane.showMessageDialog(panel, "You failed to run away! Prepare to fight!");
            runBattle();
            // Fight
        }
    }



    private void drawHero(
            Graphics g,
            int position,
            int cameraX,
            int cameraY
    ) {

        GameMap gameMap = controller.getGameMap();

        int mapSize = gameMap.getSize();

        int row = position / mapSize;
        int col = position % mapSize;

        int screenX = col * CELL_SIZE - cameraX;
        int screenY = row * CELL_SIZE - cameraY;

        // g.setColor(Color.BLUE);

        // g.fillOval(
        //         screenX,
        //         screenY,
        //         CELL_SIZE,
        //         CELL_SIZE
        // );
        g.drawImage(heroIcon.getImage(), screenX + 1, screenY + 1, CELL_SIZE - 2, CELL_SIZE - 2, null);
    }

    private void drawVillains(
            Graphics g,
            GameMap gameMap,
             int cameraX,
             int cameraY
    ) {

        // List<Villain> villains = gameMap.getGrid();
        // GameMap gameMap = controller.getGameMap();

        int mapSize = gameMap.getSize();
        int startX = Math.max(0, cameraX / CELL_SIZE);
        int startY = Math.max(0, cameraY / CELL_SIZE);

        int endX = Math.min(
                mapSize,
                (cameraX + getWidth()) / CELL_SIZE + 1
        );

        int endY = Math.min(
                mapSize,
                (cameraY + getHeight()) / CELL_SIZE + 1
        );
        System.out.println("Drawing villains from position: " + startX + "," + startY + " to " + endX + "," + endY);

        for (int position = 0; position < mapSize * mapSize; position++) {
            int villainLevel = gameMap.getVillainAtPos(position);

            if (villainLevel == 0) {
                continue;
            }

            int row = position / mapSize;
            int col = position % mapSize;

            int screenX  = col * CELL_SIZE - cameraX;
            int screenY = row * CELL_SIZE - cameraY;

            if (col < startX ||
            row < startY ||
            col > endX ||
            row > endY) {
                continue;
            }

            int heroLevel = controller.getHero().getLevel();
            ImageIcon villainIcon;
            if (villainLevel < heroLevel) {
                villainIcon = weakerVillainIcon;
            } else if (villainLevel == heroLevel) {
                villainIcon = equalVillainIcon;
            } else {
                villainIcon = strongerVillainIcon;
            }
            g.drawImage(villainIcon.getImage(), screenX + 1, screenY + 1, CELL_SIZE - 2, CELL_SIZE - 2, null);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));

            String level = String.valueOf(villainLevel);

            FontMetrics metrics = g.getFontMetrics();

            int textWidth = metrics.stringWidth(level);
            int textHeight = metrics.getAscent();

            int textX = screenX + (CELL_SIZE - textWidth) / 2;
            int textY = screenY + (CELL_SIZE + textHeight) / 2;

            g.drawString(level, textX, textY);
        }
    }
    public void showPopup(Phases phase) {
        switch (phase) {
            case BATTLE_RUN_OR_FIGHT:
                startBattle();
                break;
            case BATTLE_RUN_RESULT:
                showBattleRunResultPopup();
                break;
            default:
                // No popup for other phases
                break;
        }

    }

    public void toGameplay() {

        if (currentPopup != null) {
            currentPopup.close();
            currentPopup = null;
        }

        switch (controller.getGamePhase()) {
            case BATTLE_RUN_OR_FIGHT:
                currentPopup = new BattleRunOrFightPopup(this.controller);
                break;
            case BATTLE_RUN_RESULT:
                // showBattleRunResultPopup();
                break;
            default:
                // No popup for other phases
                break;
        }
    }

    public void onHide() {
        // This method can be used to perform any cleanup or state saving when the panel is hidden
        heroIcon = null; // Reset hero icon to ensure it gets updated when the panel is shown again
        System.out.println("GamePanel onHide called. Hero icon reset.");
    }
}
