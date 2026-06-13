package it.unicam.cs.mpgc.rpg126277.world;

public class RoomFactory{
    public static Room create(RoomType type) {

        return switch (type){
            case COMBAT -> new CombatRoom();
            case TREASURE -> new TreasureRoom();
            case EVENT -> new EventRoom();
            case BOSS -> new BossRoom();
        };
    }
}
