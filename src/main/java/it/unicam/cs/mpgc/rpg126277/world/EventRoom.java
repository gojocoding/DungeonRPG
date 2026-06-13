package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class EventRoom implements Room {
    private static final Random RANDOM = new Random();

    @Override
    public RoomType getType() {
        return RoomType.EVENT;
    }

    @Override
    public RoomResult enter(Player player) {

        int event = RANDOM.nextInt(3);

        switch (event) {

            case 0 -> {
                player.heal(20);
                return new RoomResult(
                        "Fonte magica: +20 HP",
                        RoomOutcome.NEXT_ROOM
                );
            }

            case 1 -> {
                player.takeDamage(15);

                if (!player.isAlive()) {
                    return new RoomResult(
                            "Trappola mortale!",
                            RoomOutcome.GAME_OVER
                    );
                }

                return new RoomResult(
                        "Trappola: -15 HP",
                        RoomOutcome.NEXT_ROOM
                );
            }

            default -> {
                player.addXp(30);
                return new RoomResult(
                        "Antico tomo: +30 XP",
                        RoomOutcome.NEXT_ROOM
                );
            }
        }
    }

}