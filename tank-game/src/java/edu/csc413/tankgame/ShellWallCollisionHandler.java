package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.view.RunGameView;

public class ShellWallCollisionHandler extends EntityCollisionHandler  {
    RunGameView runGameView;

    @Override
    public void handleCollision(Entity shell1, Entity wall1, GameState gameState, RunGameView runGameView) {
        if(entitiesOverlap(shell1, wall1)){
            shell1.reduceHealth(gameState);
            wall1.reduceHealth(gameState);
            runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION,RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    shell1.getX(), wall1.getY());

        }
    }
}
