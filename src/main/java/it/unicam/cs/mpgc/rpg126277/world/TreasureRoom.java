package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class TreasureRoom implements Room {
    private static final Random RANDOM = new Random();

    @Override
    public RoomType getType() {
        return RoomType.TREASURE;
    }

    @Override
    public RoomResult enter(Player player) {

        int heal = RANDOM.nextInt(20) + 10;

        player.heal(heal);

        return new RoomResult(
                "Hai trovato una pozione! +" + heal + " HP",
                RoomOutcome.NEXT_ROOM
        );
    }
}