package it.unicam.cs.mpgc.rpg126277.persistence;

public interface SaveRepository {
    void save(SaveData data);
    SaveData load(String playerName);
}
