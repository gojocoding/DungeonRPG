package it.unicam.cs.mpgc.rpg126277.model;


public class Enemy {

    private String name;
    private int hp;
    private int attack;

    public Enemy(String name, int hp, int attack) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }
}