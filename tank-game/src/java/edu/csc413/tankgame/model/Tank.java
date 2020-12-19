package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

/**
 * Model class representing a tank in the game. A tank has a position and an angle. It has a movement speed and a turn
 * speed, both represented below as constants.
 */
// TODO: Notice that Tank has a lot in common with Shell. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.
public abstract class Tank extends Entity {
    RunGameView runGameView = new RunGameView();
    private static final int initialHealth = 40;



    public Tank(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED, initialHealth);
    }

    protected void shoot(GameState gameState) {
        Shell shell = new Shell(getShellX(), getShellY(), getAngle(), getMOVEMENT_SPEED(), this.getId()  );
        gameState.addNewEntity(shell);
    }



    public double getXBound() {
        return getX() + 55.0 ;
    }

    public double getYBound() {
        return getY() + 55.0;
    }

    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.

    private double getShellX() {
        return getX() + 30.0 * (Math.cos(getAngle()) + 0.5);
    }

    private double getShellY() {
        return getY() + 30.0 * (Math.sin(getAngle()) + 0.5);
    }
}
