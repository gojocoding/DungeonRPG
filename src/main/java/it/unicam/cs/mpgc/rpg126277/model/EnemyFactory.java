package it.unicam.cs.mpgc.rpg126277.model;

import java.util.Random;

public class EnemyFactory {
    public static Enemy randomEnemy() {

        Random r = new Random();

        if (r.nextBoolean()) {

            return new Enemy(
                    "Goblin",
                    40,
                    6,
                    40
            );
        }

        return new Enemy(
                "Orc",
                70,
                12,
                80
        );
    }
}
