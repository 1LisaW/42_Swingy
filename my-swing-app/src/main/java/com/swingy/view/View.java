package com.swingy.view;

import java.util.List;

import com.swingy.model.Hero;

public abstract class View {

    public View() {
        // Initialize the view
    }

    public abstract void displayHeroStats(Hero hero);
    public abstract void promptMainMenu();
    public abstract String getUserInput(String prompt);
    public abstract void promptOnIncorrectInput();
    public abstract void promptChooseHeroClass();
    public abstract void promptChooseFromHeroList(List<Hero> heroes);
}
