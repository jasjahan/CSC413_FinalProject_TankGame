package edu.csc413.tankgame.model;

public class AiTank extends Tank {
    public AiTank(String id, double x, double y, double angle){
        super(id, x, y, angle);
    }
    @Override
    public void move(GameState gameState) {
        moveForward();
        turnRight();
    }
}
