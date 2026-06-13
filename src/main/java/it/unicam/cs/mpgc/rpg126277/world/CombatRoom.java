package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;

import java.util.Random;

public class CombatRoom extends AbstractCombatRoom {

    @Override
    protected int baseHp(Player player) {
        return 30 + new Random().nextInt(40);
    }

    @Override
    protected int baseAttack(Player player) {
        return 5 + new Random().nextInt(10);
    }

    @Override
    protected int xpReward(Player player) {
        return 50;
    }

    @Override
    protected String winMessage() {
        return "Hai vinto il combattimento!";
    }

    @Override
    protected String loseMessage() {
        return "Sei morto in combattimento!";
    }

    @Override
    public RoomType getType() {
        return RoomType.COMBAT;
    }
}