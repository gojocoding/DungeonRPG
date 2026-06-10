package it.unicam.cs.mpgc.rpg126277.model;

import java.util.List;

public class GameState {

    private Player player;

    private int currentRoomIndex;

    private List<RoomType> dungeon;

    private boolean gameOver;

    public GameState(Player player, List<RoomType> dungeon) {
        this.player = player;
        this.dungeon = dungeon;
        this.currentRoomIndex = 0;
        this.gameOver = false;
    }


    public void nextRoom() {
        if (currentRoomIndex < dungeon.size() - 1) {
            currentRoomIndex++;
        } else {
            gameOver = true;
        }
    }

    public RoomType getCurrentRoom() {
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

    public List<RoomType> getDungeon() {
        return dungeon;
    }

    public void setDungeon(List<RoomType> dungeon) {
        this.dungeon = dungeon;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}