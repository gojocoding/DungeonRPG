package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.CombatState;
import it.unicam.cs.mpgc.rpg126277.model.Enemy;
import it.unicam.cs.mpgc.rpg126277.model.EnemyFactory;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import java.util.Random;

public class CombatRoom implements Room{
    @Override
    public RoomResult enter(Player player){
        return new RoomResult(
                "Appare un nemico!",
                RoomOutcome.COMBAT_START
        );
    }

    @Override
    public RoomType getType(){
        return RoomType.COMBAT;
    }
}