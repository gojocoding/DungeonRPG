package it.unicam.cs.mpgc.rpg126277.persistence;

import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.world.RoomType;

import java.util.List;

public class SaveData {
        private Player player;
        private int currentRoomIndex;
        private boolean gameOver;
        private List<RoomType> dungeon;

        public SaveData() {
        }

        public SaveData(Player player, int currentRoomIndex, boolean gameOver, List<RoomType> dungeon) {
            this.player = player;
            this.currentRoomIndex = currentRoomIndex;
            this.gameOver = gameOver;
            this.dungeon = dungeon;
        }

        public Player getPlayer() {
            return player;
        }

        public int getCurrentRoomIndex() {
            return currentRoomIndex;
        }

        public boolean isGameOver() {
            return gameOver;
        }

        public List<RoomType> getDungeon() {
            return dungeon;
        }
    }

