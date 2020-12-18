package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.view.RunGameView;

public class TankWallCollisionHandler  extends EntityCollisionHandler   {
    @Override
    public void handleCollision(Entity tank1, Entity  wall1, GameState gameState, RunGameView runGameView) {
        if (entitiesOverlap(tank1, wall1)) {

            double dis1 = (tank1.getXBound() - wall1.getX());
            double dis2 = (wall1.getXBound() - tank1.getX());
            double dis3 = (tank1.getYBound() - wall1.getY());
            double dis4 = (wall1.getYBound() - tank1.getY());


            double minDis = dis1;
            if (minDis > dis2) {
                minDis = dis2;
            }
            if (minDis > dis3) {
                minDis = dis3;
            }
            if (minDis > dis4) {
                minDis = dis4;
            }
            if (minDis == dis1) {

                tank1.moveDis(gameState, -dis1, 0);

            } else if (minDis == dis2) {

                tank1.moveDis(gameState, dis2, 0);

            } else if (minDis == dis3) {

                tank1.moveDis(gameState, 0, dis3);

            } else if (minDis == dis4) {

                tank1.moveDis(gameState, 0, -dis4);

            }


        }

    }
}
