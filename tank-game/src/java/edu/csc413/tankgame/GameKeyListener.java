package edu.csc413.tankgame;

import edu.csc413.tankgame.model.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    GameState gameState;

    public GameKeyListener(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void keyTyped(KeyEvent event) {
        //Useless
    }

    @Override
    public void keyPressed(KeyEvent event) {

        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            gameState.setUpPressed(true);
        } else if (keyCode == KeyEvent.VK_A) {
            gameState.setLeftPressed(true);
        } else if (keyCode == KeyEvent.VK_D) {
            gameState.setRightPressed(true);
        } else if (keyCode == KeyEvent.VK_S) {
            gameState.setDownPressed(true);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            gameState.setSpacePressed(true);
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            gameState.setEscPressed(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent event) {

        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            gameState.setUpPressed(false);

        } else if (keyCode == KeyEvent.VK_A) {
            gameState.setLeftPressed(false);
        } else if (keyCode == KeyEvent.VK_D) {
            gameState.setRightPressed(false);
        } else if (keyCode == KeyEvent.VK_S) {
            gameState.setDownPressed(false);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            gameState.setSpacePressed(false);
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            gameState.setEscPressed(false);
        }

    }
}
