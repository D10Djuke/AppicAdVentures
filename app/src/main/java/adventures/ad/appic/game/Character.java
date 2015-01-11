package adventures.ad.appic.game;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class Character {

    private String name = "Guido";
    private int level = 1;

    public Character(){

    }

    public Character(String name){
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
}
