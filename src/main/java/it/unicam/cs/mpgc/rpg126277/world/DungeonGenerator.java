package it.unicam.cs.mpgc.rpg126277.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DungeonGenerator {

    public static List<Room> generateDungeon(int size) {

        List<Room> dungeon = new ArrayList<>();

        // stanze normali
        for (int i = 0; i < size; i++) {
            dungeon.add(randomRoom());
        }

        // boss SEMPRE ultimo
        dungeon.add(new BossRoom());

        return dungeon;
    }

    private static Room randomRoom() {

        double r = Math.random();

        if (r < 0.4) {
            return new CombatRoom();
        } else if (r < 0.7) {
            return new TreasureRoom();
        } else {
            return new EventRoom();
        }
    }
}