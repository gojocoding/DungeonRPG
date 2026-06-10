package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class TreasureRoom implements Room {

    @Override
    public RoomResult enter(Player player) {

        Random rand = new Random();
        int heal = rand.nextInt(20) + 10;

        player.heal(heal);

        return new RoomResult("Hai trovato una pozione! +" + heal + " HP", false);
    }
}