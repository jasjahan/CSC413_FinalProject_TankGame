package edu.csc413.tankgame;

import edu.csc413.tankgame.model.Entity;
import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.model.Shell;
import edu.csc413.tankgame.view.RunGameView;

public class TankShellCollisionHandler implements CollisionHandler {


    @Override
    public void handleCollision(Entity entity1, Entity entity2, GameState gameState, RunGameView runGameView) {
        Shell shell = (Shell) entity2;
        if (!(entity1.getId()).equals(shell.getTankId())) {
            entity1.reduceHealth(gameState);
            shell.reduceHealth(gameState);
            runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    shell.getX(), entity1.getY());
        }
    }
}
