package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.world.RoomOutcome;

public class RoomResult{
    private String message;
    private RoomOutcome outcome;

    public RoomResult(String message, RoomOutcome outcome){
        this.message = message;
        this.outcome = outcome;
    }

    public String getMessage(){
        return message;
    }

    public RoomOutcome getOutcome(){
        return outcome;
    }

    public boolean isGameOver(){
        return outcome == RoomOutcome.GAME_OVER;
    }

    public boolean isNextRoom(){
        return outcome == RoomOutcome.NEXT_ROOM;
    }

    public boolean isContinue(){
        return outcome == RoomOutcome.CONTINUE;
    }
}