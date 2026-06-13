package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Enemy;
import it.unicam.cs.mpgc.rpg126277.model.EnemyFactory;
import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class CombatRoom implements Room{
    private Enemy enemy;

    public CombatRoom() {
        enemy = EnemyFactory.randomEnemy();
    }

    @Override
    public RoomResult enter(Player player) {

        player.takeDamage(enemy.getAttack());

        enemy.takeDamage(player.getAttack());

        if (!player.isAlive()) {

            return new RoomResult(
                    "The " + enemy.getName() + " defeated you!",
                    RoomOutcome.GAME_OVER
            );
        }

        if (!enemy.isAlive()) {

            player.addXp(enemy.getXp());

            return new RoomResult(
                    "You defeated the " + enemy.getName(),
                    RoomOutcome.NEXT_ROOM
            );
        }

        return new RoomResult(
                enemy.getName()
                        + " HP: "
                        + enemy.getHp(),
                RoomOutcome.STAY
        );
    }

    @Override
    public RoomType getType() {
        return RoomType.COMBAT;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}