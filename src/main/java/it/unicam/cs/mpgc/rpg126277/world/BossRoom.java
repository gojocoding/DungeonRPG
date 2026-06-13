package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;


public class BossRoom implements Room {
    @Override
    public RoomResult enter(Player player) {

        int enemyHp = 80 + player.getLevel() * 20;
        int enemyAttack = 10 + player.getLevel() * 3;

        while (player.isAlive() && enemyHp > 0) {

            enemyHp -= player.getAttack();

            if (enemyHp <= 0) {
                player.addXp(200);
                return new RoomResult(
                        "Hai sconfitto il boss finale!",
                        RoomOutcome.NEXT_ROOM
                );
            }

            player.takeDamage(enemyAttack);

            if (!player.isAlive()) {
                return new RoomResult(
                        "Il boss ti ha ucciso!",
                        RoomOutcome.GAME_OVER
                );
            }
        }

        return new RoomResult(
                player.isAlive() ? "Victory" : "Defeat",
                player.isAlive() ? RoomOutcome.NEXT_ROOM : RoomOutcome.GAME_OVER
        );
    }

    @Override
    public RoomType getType() {
        return RoomType.BOSS;
    }
}