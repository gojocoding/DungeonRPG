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
    private boolean gameOver;
    private boolean victory;

    public GameState(Player player, List<Room> dungeon) {
        this.player = player;
        this.dungeon = dungeon;
        this.currentRoomIndex = 0;
        this.gameOver = false;
        this.victory = false;
    }

    public void nextRoom() {
        currentRoomIndex++;
    }

    public Room getCurrentRoom() {
        return dungeon.get(currentRoomIndex);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public SaveData toSaveData() {
        List<RoomType> types = dungeon.stream()
                .map(Room::getType)
                .collect(Collectors.toList());

        return new SaveData(player, currentRoomIndex, gameOver, victory, types);
    }

    public static GameState fromSaveData(SaveData data) {

        List<Room> dungeon = data.getDungeon().stream()
                .map(RoomFactory::create)
                .collect(Collectors.toList());

        GameState state = new GameState(data.getPlayer(), dungeon);
        state.setCurrentRoomIndex(data.getCurrentRoomIndex());
        state.setGameOver(data.isGameOver());
        state.setVictory(data.isVictory());

        return state;
    }
}
