package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;
import edu.csc413.tankgame.view.StartMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * GameDriver is the primary controller class for the tank game. The game is launched from GameDriver.main, and
 * GameDriver is responsible for running the game loop while coordinating the views and the data models.
 */
public class GameDriver {
    // TODO: Implement.
    // Add the instance variables, constructors, and other methods needed for this class. GameDriver is the centerpiece
    // for the tank game, and should store and manage the other components (i.e. the views and the models). It also is
    // responsible for running the game loop.
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameState gameState;



    public GameDriver() {
        PrintListener listener = new PrintListener();
        mainView = new MainView(listener);
        runGameView = mainView.getRunGameView();
        gameState = new GameState();

    }

    public void start() {
        // TODO: Implement.
        // This should set the MainView's screen to the start menu screen.
       mainView.setScreen(MainView.Screen.START_MENU_SCREEN);

    }

    public class PrintListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String actionCommand = event.getActionCommand();
            if(actionCommand.equals(StartMenuView.START_BUTTON_ACTION_COMMAND)){
                System.out.println("Start button pressed");
                mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
                runGame();

            }
            else if(actionCommand.equals(StartMenuView.EXIT_BUTTON_ACTION_COMMAND))
            {
                System.out.println("Exit button pressed.");
            }
        }
    }


    private void runGame() {
        Tank playerTank = new PlayerTank(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_INITIAL_X,
                RunGameView.PLAYER_TANK_INITIAL_Y,
                RunGameView.PLAYER_TANK_INITIAL_ANGLE);

        Tank aiTank = new AiTank(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_INITIAL_X,
                RunGameView.AI_TANK_INITIAL_Y,
                RunGameView.AI_TANK_INITIAL_ANGLE);

        gameState.addEntity(playerTank);
        gameState.addEntity(aiTank);

        runGameView.addDrawableEntity(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),
                playerTank.getY(),
                playerTank.getAngle());

        runGameView.addDrawableEntity(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle());

        Runnable gameRunner = () -> {
            while (update()) {
                runGameView.repaint();
                try {
                    Thread.sleep(8L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };
        new Thread(gameRunner).start();
    }

    // TODO: Implement.
    // update should handle one frame of gameplay. All tanks and shells move one step, and all drawn entities
    // should be updated accordingly. It should return true as long as the game continues.
    private boolean update() {

        //Ask all tanks, shells, etc to move


        /*for(Tank tanks: gameState.getTanks()){
            tanks.move(gameState);
        }*/

        for(Entity entity: gameState.getEntities()){
            entity.move(gameState);
        }

        //Ask all tanks, shells, etc to check bounds

        //Check collisions

        // Ask gameState -- any new shells to draw?
        // if so, call addDrawableEntity

        //  Ask gameState -- any new shells to remove?
        // if so, call removeDrawableEntity


        /*for(Tank tanks: gameState.getTanks()){
            runGameView.setDrawableEntityLocationAndAngle(tanks.getId(),tanks.getX(), tanks.getY(), tanks.getAngle());
        }*/


        for(Entity entity: gameState.getEntities()){
            runGameView.setDrawableEntityLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }


        return true;
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}
