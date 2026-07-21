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

    public boolean isGameOver() {
        return (
            this.hero.getHitPoints() <= 0 ||
            this.gameMap.isHeroEscaped()
        );
    }
    public void moveHero(String movement) {
        this.gameMap.moveHero(movement);
    }

    public Villain getOpponent(){
        return this.gameMap.getOpponent();
    }

    public Villain removeOpponent(){
        return this.gameMap.removeOpponent();
    }

    public void retreatHero() {
        this.gameMap.retreatHero();
    }
}
