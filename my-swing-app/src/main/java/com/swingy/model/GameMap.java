package com.swingy.model;
import com.swingy.model.Villain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GameMap {
    private int level;
    private int size = 0;
    private List<Villain> grid = null;
    private int heroPosition = 0;

    public GameMap(int level) {
        this.level = level;
        this.size = calculateSize(level);
        this.generateGrid();
    }
    private int calculateSize(int level) {
        // Size calculation logic based on level
        return ((level - 1) * 5 + 10 - level % 2); // Placeholder return value
    }

    private int getRandomPosition() {
    // Logic to get a random position in the grid
        return (int) (Math.random() * size * size);
    }

    private void generateGrid() {
        // Logic to generate the grid based on size
        // Placeholder implementation
        grid = new ArrayList<>(Collections.nCopies(size * size, (Villain) null));
        heroPosition = size * size / 2 + size / 2; // Starting position of the hero
        int randomVillainAmount = (int) (Math.random() * (size * size / 4)); // Random number of villains
        for (int i = 0; i < randomVillainAmount; i++) {
            int villainPosition = getRandomPosition();
            // Ensure villain position is not the same as hero position
            while (villainPosition == heroPosition || grid.get(villainPosition) != null) {
                villainPosition = getRandomPosition();
            }
            int villainsLevel = (int) (Math.random() * level + 1); // Random villain level
            Villain villain = new Villain(villainsLevel);
            grid.set(villainPosition, villain); // Assuming 1 represents a villain
        }
    }

    public int getSize() {
        return this.size;
    }

    public int getHeroPosition() {
        return this.heroPosition;
    }

    public  int getVillainAtPos(int pos) {
        Villain villain = this.grid.get(pos);
        if (villain == null)
            return 0;
        return villain.getLevel();
    }
}
