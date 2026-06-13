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
        if (isFinished()) {
            return new RoomResult("Game over", RoomOutcome.GAME_OVER);
        }

        if (inCombat) {
            return new RoomResult("Combattimento in corso", RoomOutcome.COMBAT_CONTINUE);
        }

        Room room = gameState.getCurrentRoom();

        if (room == null) {
            return new RoomResult("Dungeon finito", RoomOutcome.GAME_OVER);
        }

        if (room.getType() == RoomType.COMBAT) {

            currentEnemy = EnemyFactory.randomEnemy();
            inCombat = true;

            return new RoomResult(
                    "Appare un " + currentEnemy.getName(),
                    RoomOutcome.COMBAT_START
            );
        }

        if (room.getType() == RoomType.BOSS) {

            currentEnemy = EnemyFactory.boss(gameState.getPlayer());
            inCombat = true;

            return new RoomResult(
                    "IL BOSS APPARE!",
                    RoomOutcome.COMBAT_START
            );
        }
        RoomResult result = room.enter(gameState.getPlayer());
        if (result.isNextRoom()) {
            gameState.nextRoom();
        }
        return result;
    }

    public RoomResult attack() {
        if (!inCombat || currentEnemy == null) {
            return new RoomResult("Non c'è nessun nemico", RoomOutcome.NONE);
        }
        Player player = gameState.getPlayer();
        currentEnemy.takeDamage(player.getAttack());

        if (currentEnemy.isDead()) {

            boolean isBoss = gameState.getCurrentRoom() instanceof BossRoom;

            inCombat = false;
            currentEnemy = null;

            gameState.nextRoom();

            String message;

            if(isBoss){
                message = "Boss sconfitto!";
            }
            else{
                message = "Nemico sconfitto!";
            }

            return new RoomResult(message, RoomOutcome.NEXT_ROOM);
        }

            player.takeDamage(currentEnemy.getAttack());

            if (!player.isAlive()) {
                inCombat = false;
                currentEnemy = null;

                return new RoomResult("Sei morto!", RoomOutcome.GAME_OVER);
            }

            return new RoomResult("Hai colpito il nemico!", RoomOutcome.COMBAT_CONTINUE);
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