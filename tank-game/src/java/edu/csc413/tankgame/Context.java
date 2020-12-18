package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.view.RunGameView;

public class Context {

    private CollisionHandler collisionHandler;

    public Context(CollisionHandler collisionHandler){
        this.collisionHandler = collisionHandler;
    }

    public void action(Entity entity1, Entity entity2, GameState gameState, RunGameView runGameView){
             collisionHandler.handleCollision(entity1,entity2, gameState, runGameView);
    }
}
