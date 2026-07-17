package com.swingy;

// import javax.swing.SwingUtilities;


import com.swingy.controller.GameController;
import com.swingy.view.View;
import com.swingy.view.console.ConsoleView;
import com.swingy.model.GameModel;


public class App
{
    public static void main( String[] args )
    {
        if (args.length == 0)
        {
            System.out.println( "Please provide a mode argument: 'console' or 'gui'" );
            return;
        }

        View view = new ConsoleView();
        GameController gameController = new GameController(view);
        gameController.loadHeroesFromFile("heroes.txt");
        gameController.toMainMenu();

        if (args[0].equals("console")) {
            // Start the console version of the game
            // ConsoleGame consoleGame = new ConsoleGame();
            // consoleGame.start();
            System.out.println( "Console version started!" );

        } else {
            // Start the Swing GUI version of the game
            // SwingUtilities.invokeLater(() -> {
            //     new MainFrame();
            // });
        }
        System.out.println( "Hello World!" );


        // SwingUtilities.invokeLater(() -> {
        //     new MainFrame();
        // });
    }
}
