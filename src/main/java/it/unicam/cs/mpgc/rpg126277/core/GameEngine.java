package it.unicam.cs.mpgc.rpg126277.core;

import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.world.RoomFactory;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;
import it.unicam.cs.mpgc.rpg126277.world.RoomOutcome;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveData;
import it.unicam.cs.mpgc.rpg126277.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveRepository;

import java.util.List;

public class GameEngine {
    private GameState gameState;
    private final SaveRepository saveRepository;

    public GameEngine(GameState gameState, SaveRepository saveRepository) {
        this.gameState = gameState;
        this.saveRepository = saveRepository;
    }

    public RoomResult nextTurn() {

        if (isFinished()) {
            return new RoomResult(
                    "Game finished",
                    RoomOutcome.GAME_OVER
            );
        }

        Player player = gameState.getPlayer();
        Room room = gameState.getCurrentRoom();

        RoomResult result = room.enter(player);

        // gestione morte
        if (result.getOutcome() == RoomOutcome.GAME_OVER) {
            return result;
        }

        // avanzamento stanza solo se richiesto
        if (result.getOutcome() == RoomOutcome.NEXT_ROOM) {
            gameState.nextRoom();
        }

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

        Player p = gameState.getPlayer();

        SaveData data = new SaveData(
                p.getName(),
                p.getCharacterClass(),
                p.getLevel(),
                p.getXp(),
                p.getHp(),
                p.getMaxHp(),
                p.getAttack(),
                gameState.getCurrentRoomIndex(),
                gameState.getDungeon()
                        .stream()
                        .map(Room::getType)
                        .toList()
        );

        saveRepository.save(data);
    }

    public void loadGame(String playerName) {

        SaveData data = saveRepository.load(playerName);

        if (data == null) return;

        Player player = new Player(
                data.getPlayerName(),
                data.getCharacterClass()
        );

        player.setLevel(data.getLevel());
        player.setXp(data.getXp());
        player.setHp(data.getHp());
        player.setMaxHp(data.getMaxHp());
        player.setAttack(data.getAttack());

        List<Room> dungeon = data.getDungeon()
                .stream()
                .map(RoomFactory::create)
                .toList();

        this.gameState = new GameState(player, dungeon);
        this.gameState.setCurrentRoomIndex(data.getCurrentRoomIndex());
    }
}