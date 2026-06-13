package it.unicam.cs.mpgc.rpg126277.model;

import java.util.Random;

public class EnemyFactory {
    public static Enemy randomEnemy() {
        Random r = new Random();
        if(r.nextBoolean()) {
            return new Enemy(
                    "Goblin",
                    40,
                    6
              );
        }

        return new Enemy(
                "Orco",
                70,
                12
         );
    }
    public static Enemy boss(Player p) {
        return new Enemy(
                "Boss",
                90 ,
                15
        );
    }
}