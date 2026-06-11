package it.unicam.cs.mpgc.rpg126277.model;

import it.unicam.cs.mpgc.rpg126277.world.Room;

import java.util.List;

public class GameState {

    private Player player;
    private int currentRoomIndex;
    private List<Room> dungeon;
    private boolean gameOver;

    public GameState(Player player, List<Room> dungeon) {
        this.player = player;
        this.dungeon = dungeon;
        this.currentRoomIndex = 0;
        this.gameOver = false;
    }

    public void nextRoom() {
        currentRoomIndex++;
        if (currentRoomIndex >= dungeon.size()) {
            gameOver = true;
        }
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}