package it.unicam.cs.mpgc.rpg126277.ui;

import it.unicam.cs.mpgc.rpg126277.model.CharacterClass;
import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.world.DungeonGenerator;
import it.unicam.cs.mpgc.rpg126277.world.Room;

import java.util.List;


public class TestGameFactory {
    public static GameState createTestGame() {
        Player p = new Player("Test", CharacterClass.WARRIOR);
        List<Room> dungeon = DungeonGenerator.generateDungeon(4);
        return new GameState(p, dungeon);
    }
}