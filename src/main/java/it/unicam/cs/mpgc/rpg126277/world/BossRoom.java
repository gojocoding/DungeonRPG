package it.unicam.cs.mpgc.rpg126277.world;

import it.unicam.cs.mpgc.rpg126277.model.Player;


public class BossRoom extends AbstractCombatRoom {@Override
protected int baseHp(Player player) {
    return 80 + player.getLevel() * 20;
}

    @Override
    protected int baseAttack(Player player) {
        return 10 + player.getLevel() * 3;
    }

    @Override
    protected int xpReward(Player player) {
        return 200;
    }

    @Override
    protected String winMessage() {
        return "Hai sconfitto il boss finale!";
    }

    @Override
    protected String loseMessage() {
        return "Il boss ti ha ucciso!";
    }

    @Override
    public RoomType getType() {
        return RoomType.BOSS;
    }
}