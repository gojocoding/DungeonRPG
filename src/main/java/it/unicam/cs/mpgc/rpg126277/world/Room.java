package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

public interface Room{
    RoomResult enter(Player player);
    RoomType getType();
}