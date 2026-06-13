package it.unicam.cs.mpgc.rpg126277.model;

import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.world.RoomFactory;
import it.unicam.cs.mpgc.rpg126277.persistence.SaveData;
import it.unicam.cs.mpgc.rpg126277.world.RoomType;

import java.util.List;
import java.util.stream.Collectors;

public class GameState {
    private Player player;
    private int currentRoomIndex;
    private List<Room> dungeon;

    public GameState(Player player, List<Room> dungeon) {
        this.player = player;
        this.dungeon = dungeon;
        this.currentRoomIndex = 0;
    }

    public void nextRoom() {
        currentRoomIndex++;
    }

    public Room getCurrentRoom() {
        if(currentRoomIndex >= dungeon.size()) {
            return null;
        }
        return dungeon.get(currentRoomIndex);
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }

    public void setCurrentRoomIndex(int currentRoomIndex) {
        this.currentRoomIndex = currentRoomIndex;
    }

    public List<Room> getDungeon() {
        return dungeon;
    }

    public void setDungeon(List<Room> dungeon) {
        this.dungeon = dungeon;
    }
}