package adventures.ad.appic.game;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class Player implements Character {

    private String name = "Guido";
    private int level = 1;

    public Player(){

    }

    public Player(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getLevelAsNumber(){
        return level;
    }

    public String getLevelAsText(){
        return "Level " + level;
    }

    public void levelUp(){
        level++;
    }

    public void changeName(String name){
        this.name = name;
    }

    @Override
    public void dealDamage(){

    }

    @Override
    public void takeDamage(){

    }
}
