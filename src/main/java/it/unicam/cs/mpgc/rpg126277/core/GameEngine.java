package it.unicam.cs.mpgc.rpg126277.core;

import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;

public class GameEngine {

    private GameState gameState;

    public GameEngine(GameState gameState) {
        this.gameState = gameState;
    }

    public RoomResult playNextRoom(Room room) {

        Player player = gameState.getPlayer();

        RoomResult result = room.enter(player);
        gameState.nextRoom();

        if (!player.isAlive()) {
            gameState.setGameOver(true);
        }

        return result;
    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    public GameState getGameState() {
        return gameState;
    }
}
