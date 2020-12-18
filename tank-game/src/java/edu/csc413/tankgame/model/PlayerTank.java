package edu.csc413.tankgame.model;

import edu.csc413.tankgame.GameKeyListener;

import java.awt.event.KeyListener;

public class PlayerTank extends Tank {

    private int coolDown;

    public PlayerTank(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED);
        coolDown = 200;
    }


    @Override
    public void adjustForBoundary(GameState gameState) {
        if (getX() < GameState.TANK_X_LOWER_BOUND) {
            setX(GameState.TANK_X_LOWER_BOUND);
        } else if (getX() > GameState.TANK_X_UPPER_BOUND) {
            setX(GameState.TANK_X_UPPER_BOUND);
        } else if (getY() < GameState.TANK_Y_LOWER_BOUND) {
            setY(GameState.TANK_Y_LOWER_BOUND);
        } else if (getY() > GameState.TANK_Y_UPPER_BOUND) {
            setY(GameState.TANK_Y_UPPER_BOUND);
        }
    }


    @Override
    public void move(GameState gameState) {
        if (coolDown > 0) {
            coolDown--;
        }
        if (gameState.isUpPressed()) {
            moveForward();
        } else if (gameState.isDownPressed()) {
            moveBackward();
        } else if (gameState.isRightPressed()) {
            turnRight();
        } else if (gameState.isLeftPressed()) {
            turnLeft();
        } else if (gameState.isSpacePressed() && coolDown == 0) {
            coolDown = 200;
            shoot(gameState);
        }

    }
}
