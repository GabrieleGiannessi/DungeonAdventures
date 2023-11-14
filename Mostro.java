package DungeonAdventures;

public class Mostro {

    private int hp;

    public Mostro() {
        this.hp = (int) (Math.random() * 100) + 100;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
