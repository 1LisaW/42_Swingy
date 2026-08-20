package com.swingy.view.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;

import com.swingy.model.GameMap;
import com.swingy.controller.GameController;


public class GamePanel extends JPanel {
    private final GameController controller;
    // private GameMap gameMap;

    public GamePanel(GameController controller) {
        this.controller = controller;
        // this.gameMap = gameMap;
    // (GuiView view) {

        setLayout(new BorderLayout());

        JLabel game = new JLabel("Game Screen", SwingConstants.CENTER);

        JButton back = new JButton("Back to Menu");

        // back.addActionListener(e -> view.showScreen("MENU"));

        add(game, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
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

        int cellWidth = getWidth() / size;
        int cellHeight = getHeight() / size;

        drawBoard(g, size, cellWidth, cellHeight);
        drawVillains(g, size, cellWidth, cellHeight);
        drawHero(g, cellWidth, cellHeight);
    }

    private void drawBoard(
            Graphics g,
            int size,
            int cellWidth,
            int cellHeight) {

        g.setColor(Color.LIGHT_GRAY);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;

                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }

    private void drawHero(
            Graphics g,
            int cellWidth,
            int cellHeight) {

        GameMap gameMap = controller.getGameMap();

        int position = gameMap.getHeroPosition();

        int row = position / gameMap.getSize();
        int col = position % gameMap.getSize();

        int x = col * cellWidth;
        int y = row * cellHeight;

        g.setColor(Color.BLUE);

        g.fillOval(
                x + 5,
                y + 5,
                cellWidth - 10,
                cellHeight - 10
        );
    }

    private void drawVillains(
            Graphics g,
            int size,
            int cellWidth,
            int cellHeight) {

        // List<Villain> villains = gameMap.getGrid();
        GameMap gameMap = controller.getGameMap();

        g.setColor(Color.RED);

        for (int position = 0; position < size; position++) {

            int villainLevel = gameMap.getVillainAtPos(position);

            if (villainLevel == 0) {
                continue;
            }

            int row = position / size;
            int col = position % size;

            int x = col * cellWidth;
            int y = row * cellHeight;

            g.fillRect(
                    x + 5,
                    y + 5,
                    cellWidth - 10,
                    cellHeight - 10
            );
        }
    }
}
