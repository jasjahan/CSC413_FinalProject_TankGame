package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;;
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



    private boolean entitiesOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound() &&
                entity1.getXBound() > entity2.getX() &&
                entity1.getY() < entity2.getYBound() &&
                entity1.getYBound() > entity2.getY();
    }


    private void tankVsTank(Tank tank1, Tank tank2) {

        if (entitiesOverlap(tank1, tank2)) {
            double dis1 = (tank1.getXBound() - tank2.getX());
            double dis2 = (tank2.getXBound() - tank1.getX());
            double dis3 = (tank1.getYBound() - tank2.getY());
            double dis4 = (tank2.getYBound() - tank1.getY());

            double minDis = dis1;
            if (minDis > dis2) {
                minDis = dis2;
            }
            if (minDis > dis3) {
                minDis = dis3;
            }
            if (minDis > dis4) {
                minDis = dis4;
            }


            if (minDis == dis1) {
                tank1.moveDis(gameState, -dis1 / 2, 0);
                tank2.moveDis(gameState, dis1 / 2, 0);
            }
            if (minDis == dis2) {
                tank1.moveDis(gameState, dis2 / 2, 0);
                tank2.moveDis(gameState, -dis2 / 2, 0);
            }
            if (minDis == dis3) {
                tank1.moveDis(gameState, 0, dis3 / 2);
                tank2.moveDis(gameState, 0, -dis3 / 2);
            }
            if (minDis == dis4) {
                tank1.moveDis(gameState, 0, -dis4 / 2);
                tank2.moveDis(gameState, 0, dis4 / 2);
            }
        }
    }


    private void tankVsShell(Tank tank1, Shell shell1) {

        if (!(tank1.getId()).equals(shell1.getTankId()) && entitiesOverlap(tank1, shell1)) {
            tank1.reduceHealth(gameState);
            shell1.reduceHealth(gameState);
        }

    }


    private void shellVsTank(Shell shell1, Tank tank1) {
        if (!(tank1.getId()).equals(shell1.getTankId()) && entitiesOverlap(shell1, tank1)) {
            shell1.reduceHealth(gameState);
            tank1.reduceHealth(gameState);
            runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    shell1.getX(), tank1.getY());

        }
    }


    private void tankVsWall(Tank tank1, Wall wall1) {


        if (entitiesOverlap(tank1, wall1)) {

            double dis1 = (tank1.getXBound() - wall1.getX());
            double dis2 = (wall1.getXBound() - tank1.getX());
            double dis3 = (tank1.getYBound() - wall1.getY());
            double dis4 = (wall1.getYBound() - tank1.getY());


            double minDis = dis1;
            if (minDis > dis2) {
                minDis = dis2;
            }
            if (minDis > dis3) {
                minDis = dis3;
            }
            if (minDis > dis4) {
                minDis = dis4;
            }
            if (minDis == dis1) {

                tank1.moveDis(gameState, -dis1, 0);

            } else if (minDis == dis2) {

                tank1.moveDis(gameState, dis2, 0);

            } else if (minDis == dis3) {

                tank1.moveDis(gameState, 0, dis3);

            } else if (minDis == dis4) {

                tank1.moveDis(gameState, 0, -dis4);

            }


        }

    }



    private void shellVsShell(Shell shell1, Shell shell2) {
        if (entitiesOverlap(shell1, shell2)) {
            shell1.reduceHealth(gameState);
            shell2.reduceHealth(gameState);
            runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION,RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    shell1.getX(), shell2.getY());
        }
    }

    private void wallVsShell(Wall wall1, Shell shell1){
        if(entitiesOverlap( wall1, shell1)){
            wall1.reduceHealth(gameState);
            shell1.reduceHealth(gameState);

        }
    }

    private void shellVsWall(Shell shell1, Wall wall1){
        if(entitiesOverlap(shell1, wall1)){
            shell1.reduceHealth(gameState);
            wall1.reduceHealth(gameState);
            runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION,RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    shell1.getX(), wall1.getY());

        }
    }


    private void handleCollision(Entity entity1, Entity entity2, GameState gameState) {

        if (entity1 instanceof Tank && entity2 instanceof Tank) {

            tankVsTank((Tank) entity1, (Tank) entity2);

        } else if (entity1 instanceof Tank && entity2 instanceof Shell) {

            tankVsShell((Tank) entity1, (Shell) entity2);

        } else if (entity1 instanceof Shell && entity2 instanceof Tank) {

            shellVsTank((Shell) entity1, (Tank) entity2);

        } else if (entity1 instanceof Tank && entity2 instanceof Wall) {

            tankVsWall((Tank) entity1, (Wall) entity2);

        } else if (entity1 instanceof Wall && entity2 instanceof Tank) {

            //wallVsTank((Wall) entity1, (Tank) entity2);

        } else if (entity1 instanceof Shell && entity2 instanceof Shell) {

            shellVsShell((Shell) entity1, (Shell) entity2);

        } else if (entity1 instanceof Wall && entity2 instanceof Shell) {

            wallVsShell( (Wall) entity1, (Shell) entity2);

        } else if (entity1 instanceof Shell && entity2 instanceof Wall) {

            shellVsWall((Shell) entity1, (Wall) entity2);
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
                // update player tank status
            }
            else if (entity.getId().equals(GameState.AI_TANK_ID)) {
              isAliveAiTank = false;
                // update player tank status
            }
            else if (entity.getId().equals(GameState.CUSHION_AI_TANK_ID)) {
                isAliveCushionAiTank = false;
                // update player tank status
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

