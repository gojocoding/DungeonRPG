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
        this.saveRepository = new JsonSaveRepository();
    }

    public RoomResult playNextRoom() {
        return nextTurn();
    }

    public RoomResult nextTurn() {
        if (gameState.isGameOver()) {
            return new RoomResult("Game Over", true);
        }

        Player player = gameState.getPlayer();
        Room room = gameState.getCurrentRoom();
        RoomResult result = room.enter(player);

        gameState.nextRoom();


        if (!player.isAlive()) {
            gameState.setGameOver(true);
            gameState.setVictory(false);
            return new RoomResult("Sei stato sconfitto...", true);
        }

        if (gameState.getCurrentRoomIndex() >= gameState.getDungeon().size()) {
            gameState.setGameOver(true);
            gameState.setVictory(true);
        }

        return result;

    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    public boolean isVictory() {
        return gameState.isVictory();
    }

    public GameState getGameState() {
        return gameState;
    }

    private final SaveRepository saveRepository;

    public void saveGame() {
        saveRepository.save(gameState.toSaveData());
    }

    public void loadGame(String playerName) {
        SaveData data = saveRepository.load(playerName);

        if (data == null) {
            return;
        }

        this.gameState = GameState.fromSaveData(data);
    }

}