package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class EventRoom implements Room {

    @Override
    public RoomResult enter(Player player) {

        Random rand = new Random();
        int event = rand.nextInt(3);

        switch (event) {

            case 0 -> {
                player.heal(20);
                return new RoomResult("Fonte magica: +20 HP", false);
            }

            case 1 -> {
                player.takeDamage(15);
                return new RoomResult("Trappola: -15 HP", !player.isAlive());
            }

            default -> {
                player.setXp(player.getXp() + 30);
                return new RoomResult("Antico tomo: +30 XP", false);
            }
        }
    }
}
