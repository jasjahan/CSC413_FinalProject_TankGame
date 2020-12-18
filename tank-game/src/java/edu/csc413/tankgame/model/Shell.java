package edu.csc413.tankgame.model;

/**
 * Model class representing a shell that has been fired by a tank. A shell has a position and an angle, as well as a
 * speed. Shells by default should be unable to turn and only move forward.
 */
// TODO: Notice that Shell has a lot in common with Tank. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.
public class Shell extends Entity {
    private static final String SHELL_ID_PREFIX = "shell-";
    //private static final double MOVEMENT_SPEED = 4.0;

    private static long uniqueId = 0L;
    private static final int initialHealth = 1;
    private String tankId;


    public Shell(double x, double y, double angle, double MOVEMENT_SPEED, String tankId) {
        super(getUniqueId(), x, y, angle, MOVEMENT_SPEED, initialHealth);
        this.tankId = tankId;

    }

    private static String getUniqueId() {
        return SHELL_ID_PREFIX + uniqueId++;
    }


    public String getTankId() {
        return tankId;
    }

    public double getXBound() {
        return getX() + 24.0 ;
    }

    public double getYBound() {
        return getY() + 24.0;
    }

    @Override
    public void adjustForBoundary(GameState gameState) {
        if (getX() < GameState.SHELL_X_LOWER_BOUND) {
            gameState.addDeadEntity(this);
        } else if (getX() > GameState.SHELL_X_UPPER_BOUND) {
            gameState.addDeadEntity(this);
        } else if (getY() < GameState.SHELL_Y_LOWER_BOUND) {
            gameState.addDeadEntity(this);
        } else if (getY() > GameState.SHELL_Y_UPPER_BOUND) {
            gameState.addDeadEntity(this);
        }

    }

    @Override
    public void move(GameState gameState) {
        moveForward();
    }
}
