package com.example;

// import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        if (args.length > 0 && args[0].equals("console")) {
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
