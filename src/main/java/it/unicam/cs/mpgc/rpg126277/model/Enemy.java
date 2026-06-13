package it.unicam.cs.mpgc.rpg126277.model;

public class Enemy {

        private final String name;
        private int hp;
        private final int attack;
        private final int xp;

        public Enemy(
                String name,
                int hp,
                int attack,
                int xp
        ) {
            this.name = name;
            this.hp = hp;
            this.attack = attack;
            this.xp = xp;
        }

        public String getName() {
            return name;
        }

        public int getHp() {
            return hp;
        }

        public void takeDamage(int damage) {
            hp -= damage;
        }

        public boolean isAlive() {
            return hp > 0;
        }

        public int getAttack() {
            return attack;
        }

        public int getXp() {
            return xp;
        }
    }