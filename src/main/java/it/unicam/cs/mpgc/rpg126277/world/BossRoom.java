package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;


public class BossRoom implements Room{
    @Override
    public RoomType getType(){
        return RoomType.BOSS;
    }

    @Override
    public RoomResult enter(Player player){
        return new RoomResult("Stanza del BOSS...", RoomOutcome.STAY);
    }
}