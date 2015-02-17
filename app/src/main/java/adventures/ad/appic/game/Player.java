package adventures.ad.appic.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class Player implements Character, Parcelable {

    private String name = "Default";
    private int level = 1;
    private int currExp = 0;
    private ArrayList<Item> inventory;

    private String[] stats;

    public Player(){

    }

    public void setBasic(String name, int level, ArrayList<Item> inventory, int currExp){
        this.name = name;
        this.level = level;
        this.inventory = inventory;
        this.currExp = currExp;

        setBaseStats();
        calculateStats();
    }

    private void calculateStats(){
        stats[0] = Integer.toString((Integer.parseInt(stats[4]) * 4 + (level * Integer.parseInt(stats[4]) * Integer.parseInt(stats[4]) / 32)));
        stats[1] = Integer.toString((Integer.parseInt(stats[4]) + ((level * level * (Integer.parseInt(stats[4])) / 256) * 3 / 2)));
        stats[2] = Integer.toString((Integer.parseInt(stats[3]) * 4 + (level * Integer.parseInt(stats[4]) * Integer.parseInt(stats[4]) / 32)));

    }

    private void setBaseStats(){
        stats = new String[9];
        stats[3] = "10";
        stats[4] = "10";
        stats[5] = "10";
        stats[6] = "5";
        stats[7] = "5";
        stats[8] = "5";
    }

    public String getStats(int i){
        return stats[i];
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

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public int dealDamage(Creature creature){
        int damage = 0;
        int blockValue = (255 - creature.getStat(4) * 2) + 1;
        if(blockValue > 255 ){
            blockValue = 255;
        }else if(blockValue < 1){
            blockValue = 1;
        }

        if(((1 * blockValue) / 256) < 100){
           damage = 33;
        }
        return damage;
    }

    @Override
    public void takeDamage(int damage){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeList(inventory);
    }

    private void readFromParcel(Parcel in) {
        name = in.readString();
        level = in.readInt();
        inventory = in.readArrayList(getClass().getClassLoader());
    }

    public Player(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {

        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
