package com.swingy.controller;

import java.util.List;

import com.swingy.persistence.HeroRepository;
import com.swingy.model.HeroBuilder;
import com.swingy.model.HeroDirector;
import com.swingy.model.Hero;
import com.swingy.view.View;
import com.swingy.model.HeroCredentials;

public class GameController {
    private HeroRepository heroRepository;
    private View view;

    // Controller implementation
    public GameController(View view) {
        // Initialize the game controller
        this.heroRepository = HeroRepository.getInstance();
        this.view = view;
    }

    public void createHero(String name) {
        // Logic to create a new hero
    }

    public void loadHero(String name) {
        // Logic to load an existing hero
    }

    public void startGame() {
        // Logic to start the game
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
            view.promptOnIncorrectInput();
            heroCredentials.setName(view.getUserInput("Enter hero name"));
        }
        view.promptChooseHeroClass();
        String archetype = view.getUserInput("Choose an option ");
        while (!archetype.equals("1") && !archetype.equals("2") && !archetype.equals("3")) {
            view.promptOnIncorrectInput();
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
        view.promptMainMenu();
        String chosenOption = view.getUserInput("Please enter your choice").trim(); // Assuming getUserInput() is a method in the View class to get user input
        while (!chosenOption.equals("1") && !chosenOption.equals("2") && !chosenOption.equals("3")) { // Assuming valid options are "1", "2", and "3"
            view.promptOnIncorrectInput();
            chosenOption = view.getUserInput("Please enter your choice").trim();
        }
        Hero currentHero = null;
        switch (chosenOption) {
            case "1":
                currentHero = this.createHero();
                this.view.displayHeroStats(currentHero);
                // Logic to create a new hero
                break;
            case "2":
                this.view.promptChooseFromHeroList(this.heroRepository.getHeroes());
                // Logic to load an existing hero
                break;
            case "3":
                exitGame();
                break;
            default:
                view.promptOnIncorrectInput();
                toMainMenu(); // Recursively call toMainMenu() for invalid input
                break;
        }
    }

}
