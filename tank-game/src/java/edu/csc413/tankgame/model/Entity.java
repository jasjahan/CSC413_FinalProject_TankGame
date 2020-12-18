package edu.csc413.tankgame.model;

public abstract class Entity {
    private final String id;
    private double x;
    private double y;
    private double angle;
    private final double MOVEMENT_SPEED;
    private static final double TURN_SPEED = Math.toRadians(3.0);
    private int health;

    public Entity(String id, double x, double y, double angle, double MOVEMENT_SPEED, int health) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.MOVEMENT_SPEED = MOVEMENT_SPEED;
        this.health = health;
    }

    public void reduceHealth(GameState gameState){
        health--;
        if(health==0){
            gameState.addDeadEntity(this);
        }
    }


    public String getId() {
        return id;
    }

    public abstract double getXBound();
    public abstract double getYBound();


    public double getX() { return x; }

    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public double getAngle() {
        return angle;
    }

    public double getMOVEMENT_SPEED() {
        return MOVEMENT_SPEED;
    }


    public abstract void adjustForBoundary(GameState gameState);

    public abstract void move(GameState gameState);


    // TODO: The methods below are provided so you don't have to do the math for movement. However, note that they are
    // protected. You should not be calling these methods directly from outside the Tank class hierarchy. Instead,
    // consider how to design your Tank class(es) so that a Tank can represent both a player-controlled tank and an AI
    // controlled tank.

    protected void moveForward() {
        x += MOVEMENT_SPEED * Math.cos(angle);
        y += MOVEMENT_SPEED * Math.sin(angle);
    }

    protected void moveBackward() {
        x -= MOVEMENT_SPEED * Math.cos(angle);
        y -= MOVEMENT_SPEED * Math.sin(angle);
    }

    protected void turnLeft() {
        angle -= TURN_SPEED;
    }

    protected void turnRight() {
        angle += TURN_SPEED;
    }

}
