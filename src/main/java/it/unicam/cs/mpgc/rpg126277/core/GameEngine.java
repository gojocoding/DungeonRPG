package it.unicam.cs.mpgc.rpg126277.core;

import it.unicam.cs.mpgc.rpg126277.model.*;
import it.unicam.cs.mpgc.rpg126277.world.*;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveData;
import it.unicam.cs.mpgc.rpg126277.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveRepository;

import java.util.List;

public class GameEngine {
    private GameState gameState;
    private final SaveRepository saveRepository;
    private CombatState activeCombat;
    private Enemy currentEnemy;
    private boolean inCombat = false;

    public GameEngine(GameState gameState, SaveRepository saveRepository) {
        this.gameState = gameState;
        this.saveRepository = saveRepository;
    }

    public RoomResult nextTurn() {
        Player player = gameState.getPlayer();
        Room room = gameState.getCurrentRoom();

        if (isFinished()) {
            return new RoomResult(
                    "The dungeon has already ended...",
                    RoomOutcome.GAME_OVER
            );
        }

        // se sei in combat NON avanzare stanza
        if (inCombat) {
            return new RoomResult(
                    "Fight in progress!",
                    RoomOutcome.COMBAT_CONTINUE
            );
        }

        // entra in una stanza
        if (room instanceof CombatRoom) {

            currentEnemy = EnemyFactory.randomEnemy();
            inCombat = true;

            return new RoomResult(
                    "A wild " + currentEnemy.getName() + " appears!",
                    RoomOutcome.COMBAT_START
            );
        }

        RoomResult result = room.enter(player);

        if (result.getOutcome() == RoomOutcome.NEXT_ROOM) {
            gameState.nextRoom();
        }

        return result;
    }
    public RoomResult attack() {
        if (!inCombat || currentEnemy == null) {
            return new RoomResult("No enemy here", RoomOutcome.NONE);
        }

        Player player = gameState.getPlayer();

        currentEnemy.takeDamage(player.getAttack());

        if (currentEnemy.isDead()) {
            inCombat = false;
            currentEnemy = null;
            gameState.nextRoom();

            return new RoomResult("Enemy defeated!", RoomOutcome.NEXT_ROOM);
        }

        player.takeDamage(currentEnemy.getAttack());

        if (!player.isAlive()) {
            inCombat = false;
            currentEnemy = null;

            return new RoomResult("You died!", RoomOutcome.GAME_OVER);
        }

        return new RoomResult("You hit the enemy!", RoomOutcome.COMBAT_CONTINUE);
    }

    public boolean isInCombat() {
        return inCombat;
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