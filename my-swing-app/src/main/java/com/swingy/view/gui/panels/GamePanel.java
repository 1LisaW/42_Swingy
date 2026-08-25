package com.swingy.view.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import com.swingy.model.GameMap;
import com.swingy.controller.GameController;


public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 52;
    private final GameController controller;

    // private GameMap gameMap;

    public GamePanel(GameController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        JLabel game = new JLabel("Game Screen", SwingConstants.CENTER);

        JButton back = new JButton("Back to Menu");

        // back.addActionListener(e -> view.showScreen("MENU"));

        add(game, BorderLayout.CENTER);
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
        if (controller.isGameOver())
            return;
        controller.moveHero(movement);
        repaint();
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
        int cameraX = camera.x;
        int cameraY = camera.y;

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

        g.setColor(Color.LIGHT_GRAY);

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
                 g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.BOLD, 14));

            // String level = String.valueOf(villainLevel);
            String level = String.valueOf(x) + "," + String.valueOf(y); // Display position instead of level

            FontMetrics metrics = g.getFontMetrics();

            int textWidth = metrics.stringWidth(level);
            int textHeight = metrics.getAscent();

            int textX = screenX + (CELL_SIZE - textWidth) / 2;
            int textY = screenY + (CELL_SIZE + textHeight) / 2;

            g.drawString(level, textX, textY);
            };
        };
    }

    private void drawHero(
            Graphics g,
            int position,
            int cameraX,
            int cameraY
    ) {

        GameMap gameMap = controller.getGameMap();

        int row = position / gameMap.getSize();
        int col = position % gameMap.getSize();

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

        g.setColor(Color.RED);
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


        for (int position = 0; position < mapSize * mapSize; position++) {
            int villainLevel = gameMap.getVillainAtPos(position);

            if (villainLevel == 0) {
                continue;
            }
            System.out.println("Villain at position: " + position + " with level: " + villainLevel);

            int row = position / mapSize;
            int col = position % mapSize;

            int screenX  = col * CELL_SIZE - cameraX;
            int screenY = row * CELL_SIZE - cameraY;

            if (screenX + CELL_SIZE < startX ||
            screenY + CELL_SIZE < startY ||
            screenX > endX ||
            screenY > endY) {
                continue;
            }

            g.fillRect(
                    screenX,
                    screenY,
                    CELL_SIZE,
                    CELL_SIZE
            );
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));

            // String level = String.valueOf(villainLevel);
            String level = String.valueOf(position); // Display position instead of level

            FontMetrics metrics = g.getFontMetrics();

            int textWidth = metrics.stringWidth(level);
            int textHeight = metrics.getAscent();

            int textX = screenX + (CELL_SIZE - textWidth) / 2;
            int textY = screenY + (CELL_SIZE + textHeight) / 2;

            g.drawString(level, textX, textY);
        }
    }
}
