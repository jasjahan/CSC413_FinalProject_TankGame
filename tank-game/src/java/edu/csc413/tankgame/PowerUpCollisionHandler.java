package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.view.RunGameView;

public class PowerUpCollisionHandler implements CollisionHandler {
    @Override
    public void handleCollision(Entity entity1, Entity entity2, GameState gameState, RunGameView runGameView) {

        entity1.reduceHealth(gameState);
        entity2.gainHealth(gameState);

    }
}
