package com.swingy.controller;

import java.util.List;

import com.swingy.persistence.HeroRepository;
import com.swingy.model.HeroBuilder;
import com.swingy.model.HeroDirector;
import com.swingy.model.Hero;
import com.swingy.view.View;
import com.swingy.model.HeroCredentials;
import com.swingy.model.GameModel;

public class GameController {
    private HeroRepository heroRepository;
    private View view;
    private GameModel gameModel;

    // Controller implementation
    public GameController(View view) {
        // Initialize the game controller
        this.heroRepository = HeroRepository.getInstance();
        this.view = view;
    }

    public void startGame(Hero hero) {
        // Logic to start the game
        this.gameModel = new GameModel(hero);
        this.view.displayMap(this.gameModel.getMap());
    }

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
        heroCredentials.setName(view.getUserInput("Enter hero name"));
        while (heroCredentials.getName().isEmpty()) {
            view.displayOnIncorrectInput();
            heroCredentials.setName(view.getUserInput("Enter hero name"));
        }
        view.promptChooseHeroClass();
        String archetype = view.getUserInput("Choose an option ");
        while (!archetype.equals("1") && !archetype.equals("2") && !archetype.equals("3")) {
            view.displayOnIncorrectInput();
            archetype = view.getUserInput("Choose an option ");
        }
        switch (archetype) {
            case "1":
                heroCredentials.setHeroArchetype("wizard");
                break;
             case "2":
                heroCredentials.setHeroArchetype("warrior");
                break;
             case "3":
                heroCredentials.setHeroArchetype("barbarian");
                break;
        }
        return heroCredentials;
    }

    private Hero createHero() {
        HeroCredentials heroCredentials = this.createHeroCredentials();
        HeroDirector director = new HeroDirector(new HeroBuilder());
        return director.constructNewHero(heroCredentials.getName(), heroCredentials.getHeroType());
        // Logic to create a new hero with the given name and archetype
    }

    public void toMainMenu() {
        // Logic to return to the main menu
        this.view.displayMainMenu();
        int chosenOption = this.view.promptMainMenu();
        this.view.displayMainMenuStatus(chosenOption);
        Hero currentHero = null;
        switch (chosenOption) {
            case 1:
                currentHero = this.createHero();
                this.view.displayHeroStats(currentHero);
                this.startGame(currentHero);
                // Logic to create a new hero
                break;
            case 2:
                List<Hero> heroes = this.heroRepository.getHeroes();
                this.view.displayChooseHeroFromList(heroes);
                int choice = this.view.promptChooseHeroFromList(heroes.size());
                currentHero = heroes.get(choice - 1);
                this.view.displayChooseHeroFromListStatus(currentHero);
                this.startGame(currentHero);
                break;
            case 3:
                exitGame();
                break;
            default:
                view.displayOnIncorrectInput();
                toMainMenu(); // Recursively call toMainMenu() for invalid input
                break;
        }
    }

}
