package edu.csc413.tankgame.model;



public class PowerUp extends Entity {

    private static final int initialHealth = 1;
    public PowerUp(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED,initialHealth);
    }

    @Override
    public double getXBound() {

        return getX() + 10.0;

    }

    @Override
    public double getYBound() {
        return getY() + 10.0;
    }

    @Override
    public void adjustForBoundary(GameState gameState) {

    }

    @Override
    public void move(GameState gameState) {

    }
}
