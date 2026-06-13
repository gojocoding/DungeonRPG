package it.unicam.cs.mpgc.rpg126277.persistence;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JsonSaveRepository implements SaveRepository {
    private static final String PATH = "saves/";
    private final Gson gson = new Gson();

    @Override
    public void save(SaveData data) {
        try (FileWriter writer = new FileWriter(PATH + data.getPlayerName() + ".json")) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SaveData load(String playerName) {
        try (FileReader reader = new FileReader(PATH + playerName + ".json")) {
            return gson.fromJson(reader, SaveData.class);
        } catch (IOException e) {
            return null;
        }
    }
}