package it.unicam.cs.mpgc.rpg126277.world;

public class RoomResult {

    private String message;
    private boolean playerDied;

    public RoomResult(String message, boolean playerDied) {
        this.message = message;
        this.playerDied = playerDied;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPlayerDied() {
        return playerDied;
    }
}