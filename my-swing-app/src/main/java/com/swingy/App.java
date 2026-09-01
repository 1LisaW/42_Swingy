package com.swingy;

import javax.swing.SwingUtilities;


import com.swingy.controller.GameController;
import com.swingy.view.View;
import com.swingy.view.console.ConsoleView;
import com.swingy.view.gui.GuiView;
import com.swingy.model.GameModel;

import com.swingy.view.ViewManager;



public class App
{
    public static void main( String[] args )
    {
        if (args.length == 0)
        {
            System.out.println( "Please provide a mode argument: 'console' or 'gui'" );
            return;
        }
        
        GameController gameController = new GameController();
        gameController.loadHeroesFromFile("heroes.txt");

        // View view = null;
        ViewManager viewManager = new ViewManager(gameController);

        if (args[0].equals("console")) {
            viewManager.switchToConsole();
            // view = new ConsoleView(gameController);

        } else {
            viewManager.switchToSwing();
            // view = new GuiView(gameController);
            // view.displayChooseHeroFromList(gameController.getHeroes());
            // SwingUtilities.invokeLater(view::start);
        }
        // view.mainMenu();
    }
}
