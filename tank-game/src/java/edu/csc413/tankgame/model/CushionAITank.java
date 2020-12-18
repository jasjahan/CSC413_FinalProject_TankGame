package edu.csc413.tankgame.model;

public class CushionAITank extends Tank {

    private int coolDown;

    public CushionAITank(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
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

        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);

        // To figure out what angle the AI tank needs to face, we'll use the change
        // in the x and y axes between the AI and player tanks.
        double dx = playerTank.getX() - getX();
        double dy = playerTank.getY() - getY();


        // atan2 applies arctangent to the ratio of the two provided values.
        double angleToPlayer = Math.atan2(dy, dx);
        double angleDifference = getAngle() - angleToPlayer;

        // We want to keep the angle difference between -180 degrees and 180 degrees
        // for the next step. This ensures that anything outside of that range
        // is adjusted by 360 degrees at a time until it is, so that the angle is
        // still equivalent.
        angleDifference -= Math.floor(angleDifference / Math.toRadians(360.0) + 0.5) * Math.toRadians(360.0);
        double distance = Math.sqrt(dx * dy + dy * dy);

        // The angle difference being positive or negative determines if we turn
        // left or right. However, we don’t want the Tank to be constantly bouncing
        // back and forth around 0 degrees, alternating between left and right
        // turns, so we build in a small margin of error.

        if (coolDown == 0) {
            coolDown = 200;
            shoot(gameState);
        }

        if (angleDifference < -Math.toRadians(3.0)) {
            turnRight();
        } else if (angleDifference > Math.toRadians(3.0)) {
            turnLeft();
        } else if (distance > 400.0) {
            moveForward();
        } else if (distance < 200.0) {
            moveBackward();
        }



    }

}
