package DungeonAdventures;

public class Giocatore{
    
    private int hp; 
    private int pozione; 

    public Giocatore (){
    this.hp = (int) (Math.random() * 60) + 40;
    this.pozione = (int) (Math.random() * 99) + 1;
    }

    public int getPozione() {
        return pozione;
    }

    public void setPozione(int pozione) {
        this.pozione = pozione;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
