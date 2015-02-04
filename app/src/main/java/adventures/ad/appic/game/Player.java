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
    private ArrayList<Item> inventory;

    public Player(){

    }

    public void setBasic(String name, int level, ArrayList<Item> inventory){
        this.name = name;
        this.level = level;
        this.inventory = inventory;
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

    @Override
    public void dealDamage(){

    }

    @Override
    public void takeDamage(){

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
