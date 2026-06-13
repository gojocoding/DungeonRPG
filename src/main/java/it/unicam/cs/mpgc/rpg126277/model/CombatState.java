package it.unicam.cs.mpgc.rpg126277.model;

import it.unicam.cs.mpgc.rpg126277.model.Enemy;
import it.unicam.cs.mpgc.rpg126277.world.RoomOutcome;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;

public class CombatState {
    private final Player player;
    private final Enemy enemy;
    private boolean playerTurn = true;

    public CombatState(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public RoomResult playerAttack() {

        if (!playerTurn) {
            return new RoomResult("Not your turn!", RoomOutcome.STAY);
        }

        enemy.takeDamage(player.getAttack());

        if (!enemy.isAlive()) {
            return new RoomResult("Enemy defeated!", RoomOutcome.NEXT_ROOM);
        }

        playerTurn = false;

        return new RoomResult("You hit the enemy!", RoomOutcome.STAY);
    }

    public RoomResult enemyTurn() {

        if (playerTurn) {
            return new RoomResult("Wait your turn!", RoomOutcome.STAY);
        }

        player.takeDamage(enemy.getAttack());

        if (!player.isAlive()) {
            return new RoomResult("You died!", RoomOutcome.GAME_OVER);
        }

        playerTurn = true;

        return new RoomResult("Enemy attacks!", RoomOutcome.STAY);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
