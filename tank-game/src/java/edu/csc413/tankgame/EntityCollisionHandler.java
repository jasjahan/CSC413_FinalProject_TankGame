package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;

public abstract class EntityCollisionHandler implements CollisionHandler{

    public boolean entitiesOverlap(Entity entity1, Entity entity2){
        return entity1.getX() < entity2.getXBound() &&
                entity1.getXBound() > entity2.getX() &&
                entity1.getY() < entity2.getYBound() &&
                entity1.getYBound() > entity2.getY();
    }
}
