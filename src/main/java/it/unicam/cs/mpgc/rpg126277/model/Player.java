package it.unicam.cs.mpgc.rpg126277.model;

public class Player {

    private String name;
    private CharacterClass characterClass;
    private int level;
    private int xp;
    private int hp;
    private int maxHp;
    private int attack;

    public Player() {
    }

    public Player(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.level = 1;
        this.xp = 0;

        if (characterClass == CharacterClass.WARRIOR) {
            this.maxHp = 120;
            this.attack = 15;
        } else {
            this.maxHp = 80;
            this.attack = 10;
        }

        this.hp = this.maxHp;
    }
    public void addXp(int amount) {
        this.xp += amount;

        if (this.xp >= 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        xp = 0;

        maxHp += 10;
        attack += 2;
        hp = maxHp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }


    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > maxHp) this.hp = maxHp;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}