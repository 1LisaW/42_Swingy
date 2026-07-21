package com.swingy.view;

import java.util.List;

import com.swingy.model.Hero;
import com.swingy.model.GameMap;
import com.swingy.model.BattleSimulator;
import com.swingy.model.Artifact;

public abstract class View {

    public View() {
        // Initialize the view
    }

    public abstract void displayHeroStats(Hero hero);

    public abstract void displayMap(GameMap gameMap);

    public abstract void displayBattleParticipants(BattleSimulator battleSimulator);
    public abstract int promptBattleFightOrRun();
    public abstract void displayBattleLog(BattleSimulator battleSimulator);


    public abstract void displayMainMenu();
    public abstract int promptMainMenu();
    public abstract void displayMainMenuStatus(int choice);

    public abstract String getUserInput( String prompt);
    public abstract void displayOnIncorrectInput();
    public abstract void promptChooseHeroClass();
    public abstract void promptChooseFromHeroList(List<Hero> heroes);


    // Choose hero from Repo
    public abstract void displayChooseHeroFromList(List<Hero> heroes);
    public abstract int promptChooseHeroFromList(int maxNum);
    public abstract void displayChooseHeroFromListStatus(Hero hero);


    // public abstract void

    public abstract String promptHeroMove();

    public abstract void displayUseArtifact(Artifact artifact);
    public abstract int promptUseArtifact();

    public abstract void displayOnHeroRun(boolean isSuccessful);
    public abstract void displayLevelUp(Hero hero);

    public abstract void displayGameResult(boolean isWin);
}
