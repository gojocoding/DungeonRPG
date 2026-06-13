package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.CombatState;
import it.unicam.cs.mpgc.rpg126277.model.Enemy;
import it.unicam.cs.mpgc.rpg126277.model.EnemyFactory;
import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class CombatRoom implements Room{
    private CombatState combatState;
    private Enemy enemy;

    @Override
    public RoomResult enter(Player player) {
        if (enemy == null) {
            enemy = new Enemy("Goblin", 40, 6);
        }

        if (combatState == null) {
            Enemy enemy = new Enemy(
                    "Goblin",
                    40,
                    8
            );

            combatState = new CombatState(player, enemy);
        }

        return new RoomResult(
                "Combat started vs " + combatState.getEnemy().getName(),
                RoomOutcome.STAY
        );

    }

    public CombatState getCombatState() {
        return combatState;
    }

    @Override
    public RoomType getType() {
        return RoomType.COMBAT;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}