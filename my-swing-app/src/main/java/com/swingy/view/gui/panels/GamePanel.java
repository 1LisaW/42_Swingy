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
import com.swingy.controller.GameController;
import com.swingy.controller.GameOverWonPanelAction;
import com.swingy.controller.GameOverLostPanelAction;
import com.swingy.controller.Phases;


public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 52;
    private final GameController controller;
    private final GameOverWonPanelAction gameOverWonPanelAction;
    private final GameOverLostPanelAction gameOverLostPanelAction;

    private JLabel gameLabel;

    // private GameMap gameMap;

    public GamePanel(GameOverWonPanelAction gameOverWonPanelAction, GameOverLostPanelAction gameOverLostPanelAction, GameController controller) {
        this.controller = controller;
        this.gameOverWonPanelAction = gameOverWonPanelAction;
        this.gameOverLostPanelAction = gameOverLostPanelAction;

        setLayout(new BorderLayout());


        JButton back = new JButton("Back to Menu");

        // back.addActionListener(e -> view.showScreen("MENU"));

        gameLabel = new JLabel("Game Screen", SwingConstants.CENTER);
        add(gameLabel, BorderLayout.NORTH);
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

        // int cellWidth = getWidth() / size;
        // int cellHeight = getHeight() / size;
         // Camera follows the hero
         Point camera = calculateCamera(new Point(heroPosition % size, heroPosition / size), gameMap);
        // int cameraX = heroPosition % size * CELL_SIZE - getWidth() / 2 + CELL_SIZE / 2;
        // int cameraY = heroPosition / size * CELL_SIZE - getHeight() / 2 + CELL_SIZE / 2;
        int adjustedCameraX = Math.max(0, getWidth() / 4 - camera.x / 2);
        int cameraX = camera.x - adjustedCameraX; // Adjust for the hero's position
        int cameraY = camera.y - 50; // Adjust for the title label height

        if (controller.getHero() != null) {
            System.out.println("Updating game label with hero info: " + controller.getHero().getName() + " - Level: " + controller.getHero().getLevel() + ", XP: " + controller.getHero().getExperience() + "/" + controller.getHero().getMaxExperience());
            gameLabel.setText(controller.getHero().getName() + " - Game Screen - " + controller.getHero().getLevel() + " " + controller.getHero().getExperience() + " XP/ " + controller.getHero().getMaxExperience() + " XP");
            // JLabel game = new JLabel(controller.getHero().getName() + " - Game Screen - " + controller.getHero().getLevel() + " " + controller.getHero().getExperience() + " XP/ " + controller.getHero().getMaxExperience() + " XP" , SwingConstants.CENTER);
            // add(game, BorderLayout.NORTH);
        }

        drawBoard(g, gameMap, cameraX, cameraY);
        drawVillains(g, gameMap, cameraX, cameraY);
        drawHero(g, heroPosition, cameraX, cameraY);
    }

    private Point calculateCamera(Point heroPos, GameMap gameMap) {
        int mapPixelWidth = gameMap.getSize() * CELL_SIZE;
        int mapPixelHeight = gameMap.getSize() * CELL_SIZE;

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
            //      g.setColor(Color.GRAY);
            // g.setFont(new Font("Arial", Font.BOLD, 14));

            // // String level = String.valueOf(villainLevel);
            // String level = String.valueOf(x) + "," + String.valueOf(y); // Display position instead of level

            // FontMetrics metrics = g.getFontMetrics();

            // int textWidth = metrics.stringWidth(level);
            // int textHeight = metrics.getAscent();

            // int textX = screenX + (CELL_SIZE - textWidth) / 2;
            // int textY = screenY + (CELL_SIZE + textHeight) / 2;

            // g.drawString(level, textX, textY);
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

        g.setColor(Color.BLUE);

        g.fillOval(
                screenX,
                screenY,
                CELL_SIZE,
                CELL_SIZE
        );
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
            // System.out.println("Villain at position: " + position + " with level: " + villainLevel);
            g.setColor(Color.RED);
            g.fillRect(
                    screenX+5,
                    screenY+5,
                    CELL_SIZE-10,
                    CELL_SIZE-10
            );
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));

            String level = String.valueOf(villainLevel);
            // String level = String.valueOf(position); // Display position instead of level

            FontMetrics metrics = g.getFontMetrics();

            int textWidth = metrics.stringWidth(level);
            int textHeight = metrics.getAscent();

            int textX = screenX + (CELL_SIZE - textWidth) / 2;
            int textY = screenY + (CELL_SIZE + textHeight) / 2;

            g.drawString(level, textX, textY);
        }
    }
    public void showPopup(Phases phase) {
        if (phase == Phases.BATTLE_RUN_OR_FIGHT) {
            startBattle();
        }
        if (phase == Phases.BATTLE_RUN_RESULT) {
            showBattleRunResultPopup();
        }
    }
}
