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
    private boolean gameStarted = false;

    public GameEngine(GameState gameState) {
        this.gameState = gameState;
    }

    public RoomResult playNextRoom() {
        return nextTurn();
    }

    public RoomResult nextTurn() {
        if (gameState.isGameOver()) {
            return new RoomResult("Game Over", true);
        }

        Room room = gameState.getCurrentRoom();
        Player player = gameState.getPlayer();

        RoomResult result = room.enter(player);

        if (!player.isAlive()) {
            gameState.setGameOver(true);
            gameState.setVictory(false);
            return result;
        }

        gameState.nextRoom();


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
    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    private SaveRepository saveRepository = new JsonSaveRepository();

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