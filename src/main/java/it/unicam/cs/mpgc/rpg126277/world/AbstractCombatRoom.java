package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

public abstract class AbstractCombatRoom implements  Room{
    protected abstract int baseHp(Player player);
    protected abstract int baseAttack(Player player);
    protected abstract int xpReward(Player player);
    protected abstract String winMessage();
    protected abstract String loseMessage();

    @Override
    public RoomResult enter(Player player) {

        int enemyHp = baseHp(player);
        int enemyAttack = baseAttack(player);

        while (player.isAlive() && enemyHp > 0) {

            enemyHp -= player.getAttack();

            if (enemyHp <= 0) {
                player.addXp(xpReward(player));
                return new RoomResult(
                        winMessage(),
                        RoomOutcome.NEXT_ROOM
                );
            }

            player.takeDamage(enemyAttack);

            if (!player.isAlive()) {
                return new RoomResult(
                        loseMessage(),
                        RoomOutcome.GAME_OVER
                );
            }
        }

        return new RoomResult(
                "Fine combattimento",
                player.isAlive() ? RoomOutcome.NEXT_ROOM : RoomOutcome.GAME_OVER
        );
    }
}
