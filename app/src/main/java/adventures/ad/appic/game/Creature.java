package adventures.ad.appic.game;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Jory on 25/01/2015.
 */
public class Creature implements Character{

    protected int experience;
    protected int location;
    protected String description;
    protected ArrayList<String> stats = new ArrayList<>(9);
    protected String name;
    protected int level;
    protected int currHealth;
    protected Location loc;
    protected Dificulity diff;
    protected Element element;

    protected Player target;

    public enum Dificulity{
        EASY,
        NORMAL,
        HARD,
        MASTER
    }

    public enum Element{
        FIRE,
        WATER,
        NATURE
    }

    public Creature(Dificulity diff, String name, Player target, Element element){
        this.name = name;
        this.diff = diff;
        //this.loc = loc;
        this.target = target;
        this.element = element;

        setBaseStats();
        levelWithPlayer(target.getLevelAsNumber());

        currHealth = getStat(0);
    }

    private void setBaseStats(){

        stats.add("");
        stats.add("");
        stats.add("");
        stats.add("10");
        stats.add("10");
        stats.add("10");
        stats.add("5");
        stats.add("5");
        stats.add("5");
    }

    private void levelWithPlayer(int playerLevel){

        switch (diff){
            case EASY:
                level = playerLevel - 10;
                break;
            case NORMAL:
                level = playerLevel;
                break;
            case HARD:
                level = playerLevel + 10;
                break;
            case MASTER:
                level = playerLevel + 25;
                break;
        }

        int elementType = 0;

        switch(element){
            case FIRE:
                elementType = Integer.parseInt(stats.get(6));
                break;
            case NATURE:
                elementType = Integer.parseInt(stats.get(7));
                break;
            case WATER:
                elementType = Integer.parseInt(stats.get(8));
                break;
        }

        stats.set(0, Integer.toString(100));
        stats.set(1, Integer.toString(level * level * (Integer.parseInt(stats.get(4)) * 4 + elementType ) / 256));
        stats.set(2, Integer.toString((Integer.parseInt(stats.get(3)) * 4 + (level * Integer.parseInt(stats.get(4)) * Integer.parseInt(stats.get(4)) / 32))));
    }

    public int getLevel(){
        return level;
    }

    public int getStat(int i){
        return Integer.parseInt(stats.get(i));
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int dealDamage(Player player){
        int damage = 0;
        int blockValue = (255 - Integer.parseInt(player.getStats(4)) * 2) + 1;
        if(blockValue > 255 ){
            blockValue = 255;
        }else if(blockValue < 1){
            blockValue = 1;
        }

        if(((1 * blockValue) / 256) < 100){

        }
        return damage;
    }

    @Override
    public void takeDamage(int damage) {
        currHealth = currHealth - damage;
    }

    public int getHealth(){
        return currHealth;
    }
}
