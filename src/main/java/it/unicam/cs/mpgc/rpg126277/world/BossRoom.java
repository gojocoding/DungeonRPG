package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class BossRoom implements Room {
    @Override
    public RoomType getType() {
        return RoomType.BOSS;
    }
    @Override
    public RoomResult enter(Player player) {

        int bossHp = 80 + player.getLevel() * 20;
        int bossAttack = 10 + player.getLevel() * 3;

        while (player.isAlive() && bossHp > 0) {

            int damage = player.getAttack() + new Random().nextInt(5);
            bossHp -= damage;

            if (bossHp <= 0) {
                player.addXp(200);
                return new RoomResult("Hai sconfitto il boss finale!", false);
            }

            player.takeDamage(bossAttack + new Random().nextInt(3));

            if (!player.isAlive()) {
                return new RoomResult("Il boss ti ha ucciso!", true);
            }
        }

        return new RoomResult("Fine boss fight", !player.isAlive());
    }
}