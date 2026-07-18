package com.swingy.model;

import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.model.GameMap;

public class GameModel {
    private Hero hero;
    // private Villain villain;
    private GameMap gameMap;

    public GameModel(Hero hero) {
        this.hero = hero;
        this.startNewGame();
    }

    public void startNewGame() {
        this.gameMap = new GameMap(this.hero.getLevel());
    }

    public Hero getHero() {
        return hero;
    }

    public GameMap getMap() {
        return this.gameMap;
    }

}
