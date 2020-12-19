package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

import java.util.ArrayList;
import java.util.List;

/**
 * GameState represents the state of the game "world." The GameState object tracks all of the moving entities like tanks
 * and shells, and provides the controller of the program (i.e. the GameDriver) access to whatever information it needs
 * to run the game. Essentially, GameState is the "data context" needed for the rest of the program.
 */
public class GameState {
    public static final double TANK_X_LOWER_BOUND = 30.0;
    public static final double TANK_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width - 100.0;
    public static final double TANK_Y_LOWER_BOUND = 30.0;
    public static final double TANK_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height - 120.0;

    public static final double SHELL_X_LOWER_BOUND = -10.0;
    public static final double SHELL_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width;
    public static final double SHELL_Y_LOWER_BOUND = -10.0;
    public static final double SHELL_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height;

    public static final String PLAYER_TANK_ID = "player-tank";
    public static final String AI_TANK_ID = "ai-tank";
    public static final String CUSHION_AI_TANK_ID = "cushion-ai-tank";
    public static final String POWER_UP_ID = "shell-explosion-1";


    // TODO: Feel free to add more tank IDs if you want to support multiple AI tanks! Just make sure they're unique.

    // TODO: Implement.
    // There's a lot of information the GameState will need to store to provide contextual information. Add whatever
    // instance variables, constructors, and methods are needed.


    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;
    private boolean escPressed = false;

    public boolean isAlivePlayerTank() {
        return isAlivePlayerTank;
    }

    public void setAlivePlayerTank(boolean alivePlayerTank) {
        isAlivePlayerTank = alivePlayerTank;
    }

    public boolean isAliveAiTank() {
        return isAliveAiTank;
    }

    public void setAliveAiTank(boolean aliveAiTank) {
        isAliveAiTank = aliveAiTank;
    }

    public boolean isAliveCushionAiTank() {
        return isAliveCushionAiTank;
    }

    public void setAliveCushionAiTank(boolean aliveCushionAiTank) {
        isAliveCushionAiTank = aliveCushionAiTank;
    }

    private boolean isAlivePlayerTank = true;
    private boolean isAliveAiTank = true;
    private boolean isAliveCushionAiTank = true;



    private final List<Entity> entities = new ArrayList<>();

    public List<Entity> getEntities() {
        return entities;
    }


    public Entity getEntity(String playerTankId) {
        for (Entity entity : getEntities()) {
            if (entity.getId().equals(playerTankId)) {
                return entity;
            }
        }
        return null;
    }


    private final List<Entity> deadEntities = new ArrayList<>();

    public void reset(){
        entities.clear();
        deadEntities.clear();
    }

    public void addDeadEntity(Entity entity) {
        deadEntities.add(entity);
    }

    public void clearDeadEntities() {
        deadEntities.clear();
    }

    public List<Entity> getDeadEntities() {
        return deadEntities;
    }


    private final List<Entity> newEntities = new ArrayList<>();

    public void addNewEntity(Entity entity) {
        newEntities.add(entity);
    }

    public void clearNewEntities() {
        newEntities.clear();
    }

    public List<Entity> getNewEntities() {
        return newEntities;
    }


    public void addEntity(Entity tank) {
        entities.add(tank);
    }

    public void removeEntity(Entity tank) {
        entities.remove(tank);
    }


    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }


    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }


    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }


    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }


    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }


    public boolean isEscPressed() {
        return escPressed;
    }

    public void setEscPressed(boolean escPressed) {
        this.escPressed = escPressed;
    }


}
