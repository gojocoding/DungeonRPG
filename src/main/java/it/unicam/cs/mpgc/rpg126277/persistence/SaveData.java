package it.unicam.cs.mpgc.rpg126277.persistence;

import it.unicam.cs.mpgc.rpg126277.model.CharacterClass;
import it.unicam.cs.mpgc.rpg126277.world.RoomType;

import java.util.List;

public class SaveData {
    private String playerName;
    private CharacterClass characterClass;
    private int level;
    private int xp;
    private int hp;
    private int maxHp;
    private int attack;
    private int currentRoomIndex;
    private List<RoomType> dungeon;

    public SaveData(String playerName, CharacterClass characterClass, int level, int xp, int hp, int maxHp, int attack, int currentRoomIndex, List<RoomType> dungeon) {
        this.playerName = playerName;
        this.characterClass = characterClass;
        this.level = level;
        this.xp = xp;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attack = attack;
        this.currentRoomIndex = currentRoomIndex;
        this.dungeon = dungeon;
    }

    public String getPlayerName(){
            return playerName;
        }
        public CharacterClass getCharacterClass()
        return characterClass;
    public int getLevel(){
        return level;
    }
    public int getXp(){
        return xp;
    }
    public int getHp(){
        return hp;
    }
    public int getMaxHp(){
    return maxHp;
    }
    public int getAttack(){
        return attack;
    }
    public int getCurrentRoomIndex(){
        return currentRoomIndex;
    }
    public List<RoomType> getDungeon(){
        return dungeon;
    }
}


