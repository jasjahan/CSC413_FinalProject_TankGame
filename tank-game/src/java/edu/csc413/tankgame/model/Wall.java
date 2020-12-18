package edu.csc413.tankgame.model;

public class Wall extends Entity {
    private static final String WALL_IMAGE_FILE_PREFIX = "wall-";
    private static long uniqueId = 0L;
    private static final int initialHealth = 3;



    public Wall(double x, double y, double angle, double MOVEMENT_SPEED) {
        super(getUniqueId(), x, y, angle, MOVEMENT_SPEED, initialHealth);
    }


    private static String getUniqueId() {
        return WALL_IMAGE_FILE_PREFIX + uniqueId++;
    }

    public double getXBound() {
        return getX() + 32.0 ;
    }

    public double getYBound() {
        return getY() + 32.0;
    }

    @Override
    public void adjustForBoundary(GameState gameState) {

    }

    @Override
    public void move(GameState gameState) {

    }
}
