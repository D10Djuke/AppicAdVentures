package adventures.ad.appic.game;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import adventures.ad.appic.app.R;

/**
 * Created by Jory on 25/01/2015.
 */
public class Creature extends Character{

    protected int experience;
    protected String description;
    protected ArrayList<String> stats = new ArrayList<>(9);
    protected String name;
    protected int level;
    protected int currHealth;
    protected Location loc;
    protected Dificulity diff;
    protected Element element;
    protected Stance stance = Stance.IDLE;

    protected AnimationDrawable testAnimation = null;

    protected Player target;

    public enum Dificulity{
        EASY,
        NORMAL,
        HARD,
        MASTER
    }

    public enum Stance{
        IDLE,
        ATTACK
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
        levelWithPlayer(target.getLvl());

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

        stats.set(0, Integer.toString(level * Integer.parseInt(stats.get(5))* 2 ));
        stats.set(1, Integer.toString(level * level * (Integer.parseInt(stats.get(4)) * 4 + elementType ) / 256));
        stats.set(2, Integer.toString((Integer.parseInt(stats.get(3)) * 4 + (level * Integer.parseInt(stats.get(4)) * Integer.parseInt(stats.get(4)) / 32))));
    }

    public AnimationDrawable getAnim(){
        return testAnimation;
    }

    public void setAnimation(ImageView animationImage){
        animationImage.setBackgroundResource(R.drawable.anim_hugbear_idle);
        testAnimation = (AnimationDrawable) animationImage.getBackground();
    }

    public void setStance(Stance stance){
        this.stance = stance;
    }

    public Stance getStance(){
        return stance;
    }

    public AnimationDrawable changeAnimation(AnimationDrawable anim, Context c, ImageView v){

        int[] sequenceIDLE = {1,2,1,4};
        int[] sequenceATK = {1,2,3,4,3,2};
        String packageName = c.getPackageName();

        anim = new AnimationDrawable();
        switch (stance){
            case IDLE:
                for(int i : sequenceIDLE){
                    String s = "img_creature_" + name + "_idle" + i;
                    Log.e("stance" , s);
                    int resId = c.getResources().getIdentifier( s, "drawable", packageName);
                    anim.addFrame(c.getResources().getDrawable(resId), 200);
                }
                break;
            case ATTACK:
                for(int i : sequenceATK){
                    String s = "img_creature_" + name + "_atk" + i;
                    Log.e("stance" , s);
                    int resId = c.getResources().getIdentifier( s, "drawable", packageName);
                    anim.addFrame(c.getResources().getDrawable(resId), 200);
                }
                break;
        }
        anim.setOneShot(true);
        return anim;
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
