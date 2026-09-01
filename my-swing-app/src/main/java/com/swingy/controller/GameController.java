package com.swingy.controller;

import java.util.List;

import com.swingy.persistence.HeroRepository;
import com.swingy.model.HeroBuilder;
import com.swingy.model.HeroDirector;
import com.swingy.model.Hero;
// import com.swingy.view.View;
import com.swingy.model.HeroCredentials;
import com.swingy.model.GameModel;
import com.swingy.model.GameMap;
import com.swingy.model.BattleSimulator;
import com.swingy.model.Villain;
import com.swingy.model.Artifact;
import com.swingy.model.ArtifactFactory;


public class GameController {
    private HeroRepository heroRepository;
    private GameModel gameModel;

    private BattleSimulator currentBattle = null;

    // Controller implementation
    public GameController() {
        // Initialize the game controller
        this.heroRepository = HeroRepository.getInstance();
    }

    public void startGame(Hero hero) {
        // Logic to start the game
        this.gameModel = new GameModel(hero);
        System.out.println("Game started with hero: " + hero.getName() + ", class: ");
    }

    public void handleMovement() {
        if (this.gameModel.isGameOver())
            return ;

    }

    public boolean isGameOver() {
        return this.gameModel.isGameOver();
    }

    public boolean isBattleTriggered() {
        return this.gameModel.getOpponent() != null;
    }

    public void moveHero(String movement) {
        this.gameModel.moveHero(movement);
        if (this.gameModel.getOpponent() != null)
            this.initBattleSimulator();
    }

    private void gameEnd() {
        Hero hero = this.gameModel.getHero();
        if (hero.getHitPoints() == 0) {
            if (!this.heroRepository.containsHero(hero)) {
                this.heroRepository.addHero(hero);
                // this.heroRepository.saveHeroesToFile("save.txt");
            }
        }
        this.gameModel = null;
        try {
            this.heroRepository.saveHeroesToFile(java.nio.file.Paths.get("save.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runBattle() {
        if (this.currentBattle != null) {
            int fightResult = this.currentBattle.fight();
            if (fightResult != 1)
                this.gameModel.retreatHero();
            if (fightResult == 1) {
                Villain villain = this.gameModel.removeOpponent();
            }
        }
    }

    public void simulateBattle() {
        int fightResult = this.currentBattle.fight();
        if (fightResult != 1)
            this.gameModel.retreatHero();
        if (fightResult == 1) {
            Villain villain = this.gameModel.removeOpponent();
        }
    }

    public int getBattleResult() {
        if (this.currentBattle != null) {
            return this.currentBattle.getBattleResult();
        }
        return -1; // No battle in progress
    }

    public List<String> getBattleLog() {
        if (this.currentBattle != null) {
            return this.currentBattle.getLog();
        }
        return null; // No battle in progress
    }

    private void initBattleSimulator() {
        Hero hero = this.gameModel.getHero();
        Villain villain = this.gameModel.getOpponent();
        this.currentBattle = new BattleSimulator(hero, villain);
    }

    public int runFromBattle() {
        if (this.currentBattle != null) {
            int result = this.currentBattle.run();
            if (result == 1) {
                this.currentBattle = null;
                this.gameModel.retreatHero();
            } 
            return result;
        }
        return -1; // No battle to run from
    }

    public void loadHeroesFromFile(String filePath) {
        try {
            List<String> heroDataList = heroRepository.readHeroesFromFile(java.nio.file.Paths.get(filePath));
            heroRepository.parseHeroesFromRepository(heroDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HeroCredentials createHeroCredentials() {
        HeroCredentials heroCredentials = new HeroCredentials();
        return heroCredentials;
    }

    public Hero createHero(HeroCredentials heroCredentials) {
        HeroDirector director = new HeroDirector(new HeroBuilder());
        return director.constructNewHero(heroCredentials.getName(), heroCredentials.getHeroType());
    }

    public List<Hero> getHeroes() {
        return heroRepository.getHeroes();
    }

    public GameMap getGameMap() {
        return this.gameModel.getMap();
    }

    public Hero getHero() {
        return this.gameModel.getHero();
    }

    public String getGamePhase() {
        if (this.gameModel == null) {
            return "NO_GAME";
        } else if (this.currentBattle != null) {
            return "BATTLE";
        } else {
            return "EXPLORATION";
        }
    }

}
