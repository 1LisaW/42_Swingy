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
        // this.view = view;
    }

    public void startGame(Hero hero) {
        // Logic to start the game
        this.gameModel = new GameModel(hero);
        System.out.println("Game started with hero: " + hero.getName() + ", class: ");
        // this.view.displayMap(this.gameModel.getMap());
        // this.gameLoop();
    }

    public void handleMovement() {
        if (this.gameModel.isGameOver())
            return ;

    }

    private void gameLoop() {
        // while (!(this.gameModel.isGameOver())) {
        //     String movement = this.view.promptHeroMove();
        //     this.gameModel.moveHero(movement);
        //     // this.view.displayMap(this.gameModel.getMap());
        //     if (this.gameModel.getOpponent() != null)
        //         this.initBattleSimulator();
        // }
        this.gameEnd();
    }

    public boolean isGameOver() {
        return this.gameModel.isGameOver();
    }

    public boolean isBattleTriggered() {
        return this.gameModel.getOpponent() != null;
    }

    public void moveHero(String movement) {
        this.gameModel.moveHero(movement);
        // this.view.displayMap(this.gameModel.getMap());
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
            // this.view.displayGameResult(false);
        }
        // else
            // this.view.displayGameResult(true);
        this.gameModel = null;
        try {
            this.heroRepository.saveHeroesToFile(java.nio.file.Paths.get("save.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBattleResult() {
        if (this.currentBattle != null) {
            return this.currentBattle.getBattleResult();
        }
        return -1; // No battle result available
    }

    public void simulateBattle() {
        int fightResult = this.currentBattle.fight();
        if (fightResult != 1)
            this.gameModel.retreatHero();
        if (fightResult == 1) {
            Villain villain = this.gameModel.removeOpponent();
        }
        // this.view.displayBattleLog(battleSimulator);
        // if (fightResult == 1) {
        //     Hero hero = this.gameModel.getHero();
        //     hero.setExperience(battleSimulator.getExperience());
        //     // if (hero.checkLevelUp())
        //         // this.view.displayLevelUp(hero);

        //      Artifact artifact = battleSimulator.generateArtifact();
        //     // if (artifact != null) {
        //     //     this.view.displayUseArtifact(artifact);
        //     //     if (this.view.promptUseArtifact() == 1) {
        //     //         hero.addArtifact(artifact);
        //     //     }
        //     // }
        // }
    }

    private void initBattleSimulator() {
        Hero hero = this.gameModel.getHero();
        Villain villain = this.gameModel.getOpponent();
        this.currentBattle = new BattleSimulator(hero, villain);
        // this.view.displayBattleParticipants(battleSimulator);
        // int option = this.view.promptBattleFightOrRun();
        // switch(option) {
        //     case 1:
        //         this.simulateBattle(battleSimulator);
        //         break;
        //     case 2:
        //         if (battleSimulator.run() == 1) {
        //             this.view.displayOnHeroRun(true);
        //             this.gameModel.retreatHero();
        //         }
        //         else {
        //             this.view.displayOnHeroRun(false);
        //             this.simulateBattle(battleSimulator);
        //         }
        //         break;
        // }
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

    public List<String> getBattleLog() {
        if (this.currentBattle != null) {
            return this.currentBattle.getLog();
        }
        return null; // No battle log available
    }
    // public void runBattle() {
    //     if (this.currentBattle != null) {
    //         if (this.currentBattle.run() == 1) {
    //             // this.view.displayOnHeroRun(true);
    //             this.gameModel.retreatHero();
    //         } else {
    //             // this.view.displayOnHeroRun(false);
    //             this.simulateBattle(this.currentBattle);
    //         }
    //         this.currentBattle = null;
    //     }
    // }

    public void saveGame() {
        // Logic to save the game state
    }

    public void exitGame() {
        // Logic to exit the game
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
        // heroCredentials.setName(view.getUserInput("Enter hero name"));
        // while (heroCredentials.getName().isEmpty()) {
        //     // view.displayOnIncorrectInput();
        //     heroCredentials.setName(view.getUserInput("Enter hero name"));
        // }
        // view.promptChooseHeroClass();
        // String archetype = view.getUserInput("Choose an option ");
        // while (!archetype.equals("1") && !archetype.equals("2") && !archetype.equals("3")) {
        //     // view.displayOnIncorrectInput();
        //     archetype = view.getUserInput("Choose an option ");
        // }
        // switch (archetype) {
        //     case "1":
        //         heroCredentials.setHeroArchetype("wizard");
        //         break;
        //      case "2":
        //         heroCredentials.setHeroArchetype("warrior");
        //         break;
        //      case "3":
        //         heroCredentials.setHeroArchetype("barbarian");
        //         break;
        // }
        return heroCredentials;
    }

    public Hero createHero(HeroCredentials heroCredentials) {
        // HeroCredentials heroCredentials = this.createHeroCredentials();
        HeroDirector director = new HeroDirector(new HeroBuilder());
        return director.constructNewHero(heroCredentials.getName(), heroCredentials.getHeroType());
        // Logic to create a new hero with the given name and archetype
    }

    public List<Hero> getHeroes() {
        return heroRepository.getHeroes();
    }

    public GameMap getGameMap() {
        return this.gameModel.getMap();
    }

    public Hero getHero() {
        if (this.gameModel != null) {
            return this.gameModel.getHero();
        }
        return null;
    }

}
