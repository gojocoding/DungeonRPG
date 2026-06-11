package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

public class BossRoom implements Room {
    @Override
    public RoomType getType() {
        return RoomType.BOSS;
    }
    @Override
    public RoomResult enter(Player player) {

        int bossHp = 120;
        int bossAttack = 20;

        while (player.isAlive() && bossHp > 0) {

            bossHp -= player.getAttack();

            if (bossHp <= 0) {
                player.setXp(player.getXp() + 200);
                return new RoomResult("Hai sconfitto il boss finale!", false);
            }

            player.takeDamage(bossAttack);

            if (!player.isAlive()) {
                return new RoomResult("Il boss ti ha ucciso!", true);
            }
        }

        return new RoomResult("Fine boss fight", !player.isAlive());
    }
}