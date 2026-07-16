package com.swingy.model;

import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.model.GameMap;

public class GameModel {
    private Hero hero;
    private Villain villain;
    private GameMap gameMap;

    public GameModel(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

}
