package com.swingy;

import javax.swing.SwingUtilities;

import com.swingy.controller.GameController;
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
        gameController.loadHeroesFromFile();

        ViewManager viewManager = new ViewManager(gameController);

        if (args[0].equals("console")) {
            viewManager.switchToConsole();

        } else {
            viewManager.switchToSwing();
        }
    }
}
