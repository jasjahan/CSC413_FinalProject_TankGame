package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;
import edu.csc413.tankgame.view.StartMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        gameState = new GameState();
        GameKeyListener listener1 = new GameKeyListener(gameState);
        mainView = new MainView(listener, listener1);
        runGameView = mainView.getRunGameView();
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }

    public void start() {
        // TODO: Implement.
        // This should set the MainView's screen to the start menu screen.
        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);

    }

    private void runGame() {
        Tank playerTank = new PlayerTank(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_INITIAL_X,
                RunGameView.PLAYER_TANK_INITIAL_Y,
                RunGameView.PLAYER_TANK_INITIAL_ANGLE,
                RunGameView.PLAYER_TANK_MOVEMENT_SPEED);

        Tank aiTank = new AiTank(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_INITIAL_X,
                RunGameView.AI_TANK_INITIAL_Y,
                RunGameView.AI_TANK_INITIAL_ANGLE,
                RunGameView.AI_TANK_MOVEMENT_SPEED);

        Tank cushionAiTank = new CushionAITank(
                GameState.CUSHION_AI_TANK_ID,
                RunGameView.AI_TANK_2_INITIAL_X,
                RunGameView.AI_TANK_2_INITIAL_Y,
                RunGameView.AI_TANK_2_INITIAL_ANGLE,
                RunGameView.AI_TANK_2_MOVEMENT_SPEED);

        gameState.addEntity(playerTank);
        gameState.addEntity(aiTank);
        gameState.addEntity(cushionAiTank);


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

        runGameView.addDrawableEntity(
                GameState.CUSHION_AI_TANK_ID,
                RunGameView.AI_TANK_IMAGE_FILE,
                cushionAiTank.getX(),
                cushionAiTank.getY(),
                cushionAiTank.getAngle());

        for (WallImageInfo wallImageInfo : WallImageInfo.readWalls()) {

            Wall wall = new Wall(wallImageInfo.getX(), wallImageInfo.getY(), 0, 0);
            gameState.addEntity(wall);
            runGameView.addDrawableEntity(
                    wall.getId(),
                    wallImageInfo.getImageFile(),
                    wall.getX(),
                    wall.getY(),
                    0
            );
        }


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



    private void handleCollision(Entity entity1, Entity entity2, GameState gameState) {

        if (entity1 instanceof Tank && entity2 instanceof Tank) {

            Context context = new Context(new TankTankCollisionHandler());
            context.action(entity1, entity2, gameState, runGameView);

        } else if (entity1 instanceof Tank && entity2 instanceof Shell) {

            Context context = new Context(new TankShellCollisionHandler());
            context.action(entity1, entity2, gameState, runGameView);

        }  else if (entity1 instanceof Tank && entity2 instanceof Wall) {

            Context context = new Context(new TankWallCollisionHandler());
            context.action(entity1, entity2, gameState, runGameView);

        }  else if (entity1 instanceof Shell && entity2 instanceof Shell) {

            Context context = new Context(new ShellShellCollisionHandler());
            context.action(entity1, entity2, gameState, runGameView);

        } else if (entity1 instanceof Shell && entity2 instanceof Wall) {

            Context context = new Context(new ShellWallCollisionHandler());
            context.action(entity1, entity2, gameState, runGameView);

        }
    }

    // TODO: Implement.
    // update should handle one frame of gameplay. All tanks and shells move one step, and all drawn entities
    // should be updated accordingly. It should return true as long as the game continues.
    private boolean update() {
        if(gameState.isEscPressed()){
            runGameView.reset();
            gameState.reset();
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            return false;
        }

        //Ask all tanks, shells, etc to move
        for (Entity entity : gameState.getEntities()) {
                   entity.move(gameState);
        }

        //Ask all tanks, shells, etc to check bounds

        for (Entity entity : gameState.getEntities()) {
                entity.adjustForBoundary(gameState);
        }

        //Check collisions
        for (Entity entity1 : gameState.getEntities()) {
            for (Entity entity2 : gameState.getEntities()) {
                if (!entity1.equals(entity2)) {
                    handleCollision(entity1, entity2, gameState);
                }
            }
        }


        // Ask gameState -- any new shells to draw?
        // if so, call addDrawableEntity

        for (Entity entity : gameState.getNewEntities()) {

            runGameView.addDrawableEntity(
                    entity.getId(),
                    RunGameView.SHELL_IMAGE_FILE,
                    entity.getX(),
                    entity.getY(),
                    entity.getAngle());
            gameState.addEntity(entity);

        }
        gameState.clearNewEntities();

        boolean isAlivePlayerTank = true;
        boolean isAliveAiTank = true;
        boolean isAliveCushionAiTank = true;

        //  Ask gameState -- any new shells to remove?
        // if so, call removeDrawableEntity
        for (Entity entity : gameState.getDeadEntities()) {
            runGameView.removeDrawableEntity(entity.getId());
            gameState.removeEntity(entity);
            if (entity.getId().equals(GameState.PLAYER_TANK_ID)) {
                isAlivePlayerTank = false;
            }
            else if (entity.getId().equals(GameState.AI_TANK_ID)) {
              isAliveAiTank = false;
            }
            else if (entity.getId().equals(GameState.CUSHION_AI_TANK_ID)) {
                isAliveCushionAiTank = false;
            }

        }

        gameState.clearDeadEntities();

        for (Entity entity : gameState.getEntities()) {
            runGameView.setDrawableEntityLocationAndAngle(entity.getId(),
                    entity.getX(), entity.getY(), entity.getAngle());
        }


        if(!isAlivePlayerTank || (!isAliveAiTank && !isAliveCushionAiTank)){
            runGameView.reset();
            gameState.reset();
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            return false;
        }

        return true;
    }

    public class PrintListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String actionCommand = event.getActionCommand();
            if (actionCommand.equals(StartMenuView.START_BUTTON_ACTION_COMMAND)) {
                System.out.println("Start button pressed");
                mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
                runGame();

            } else if (actionCommand.equals(StartMenuView.EXIT_BUTTON_ACTION_COMMAND)) {
                System.out.println("Exit button pressed.");
                mainView.closeGame();

            }
        }
    }
}

