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
    private final SaveRepository saveRepository;

    public GameEngine(GameState gameState) {
        this.gameState = gameState;
        this.saveRepository = new JsonSaveRepository();
    }

    public RoomResult nextTurn() {

        if (isFinished()) {
            return new RoomResult("Game finished", true);
        }

        Player player = gameState.getPlayer();
        Room room = gameState.getCurrentRoom();

        RoomResult result = room.enter(player);

        if (!player.isAlive()) {
            return new RoomResult("💀 Sei stato sconfitto...", true);
        }

        gameState.nextRoom();

        return result;
    }

    public boolean isFinished() {
        return isGameOver() || isVictory();
    }

    public boolean isGameOver() {
        return gameState.getPlayer().getHp() <= 0;
    }

    public boolean isVictory() {
        return gameState.getCurrentRoomIndex() >= gameState.getDungeon().size();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void saveGame() {
        saveRepository.save(gameState.toSaveData());
    }

    public void loadGame(String playerName) {
        SaveData data = saveRepository.load(playerName);

        if (data == null) return;

        this.gameState = GameState.fromSaveData(data);
    }
}