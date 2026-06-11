package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class CombatRoom implements Room {
    @Override
    public RoomType getType() {
        return RoomType.COMBAT;
    }
    @Override
    public RoomResult enter(Player player) {

        int enemyHp = 50;
        int enemyAttack = 10;

        Random rand = new Random();

        while (player.isAlive() && enemyHp > 0) {

            enemyHp -= player.getAttack();

            if (enemyHp <= 0) {
                player.setXp(player.getXp() + 50);
                return new RoomResult("Hai vinto il combattimento!", false);
            }

            player.takeDamage(enemyAttack);

            if (!player.isAlive()) {
                return new RoomResult("Sei morto in combattimento!", true);
            }
        }

        return new RoomResult("Fine combattimento", !player.isAlive());
    }
}
