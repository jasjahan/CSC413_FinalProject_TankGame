package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.view.RunGameView;

public interface CollisionHandler {
     public boolean entitiesOverlap(Entity entity1, Entity entity2);
     public void handleCollision(Entity entity1, Entity entity2, GameState gameState, RunGameView runGameView);

}
