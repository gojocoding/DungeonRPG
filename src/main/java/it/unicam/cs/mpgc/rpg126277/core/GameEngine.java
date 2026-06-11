package it.unicam.cs.mpgc.rpg126277.core;

import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveData;
import it.unicam.cs.mpgc.rpg126277.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveRepository;

public class GameEngine {

    private GameState gameState;

    public GameEngine(GameState gameState) {
        this.gameState = gameState;
    }

    public RoomResult playNextRoom() {

        Player player = gameState.getPlayer();

        Room room = gameState.getCurrentRoom();

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
    private SaveRepository saveRepository = new JsonSaveRepository();

    public void saveGame() {
        saveRepository.save(gameState.toSaveData());
    }

    public void loadGame(String playerName) {
        SaveData data = saveRepository.load(playerName);
        this.gameState = GameState.fromSaveData(data);
    }
}