package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.view.RunGameView;

public class TankTankCollisionHandler extends EntityCollisionHandler {

    @Override
    public void handleCollision(Entity tank1, Entity tank2, GameState gameState, RunGameView runGameView) {
        if (entitiesOverlap(tank1, tank2)) {
            double dis1 = (tank1.getXBound() - tank2.getX());
            double dis2 = (tank2.getXBound() - tank1.getX());
            double dis3 = (tank1.getYBound() - tank2.getY());
            double dis4 = (tank2.getYBound() - tank1.getY());

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
                tank1.moveDis(gameState, -dis1 / 2, 0);
                tank2.moveDis(gameState, dis1 / 2, 0);
            }
            if (minDis == dis2) {
                tank1.moveDis(gameState, dis2 / 2, 0);
                tank2.moveDis(gameState, -dis2 / 2, 0);
            }
            if (minDis == dis3) {
                tank1.moveDis(gameState, 0, dis3 / 2);
                tank2.moveDis(gameState, 0, -dis3 / 2);
            }
            if (minDis == dis4) {
                tank1.moveDis(gameState, 0, -dis4 / 2);
                tank2.moveDis(gameState, 0, dis4 / 2);
            }
        }
    }

}
