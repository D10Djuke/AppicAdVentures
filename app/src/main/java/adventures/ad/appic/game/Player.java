package adventures.ad.appic.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class Player extends Character implements Parcelable {

    private int currExp = 0;
    private ArrayList<Item> inventory;

    private ArrayList<String> stats  = new ArrayList<>(9);

    public Player(){

    }

    public int getCurrExp(){
        return currExp;
    }

    public void setCurrExp(int currExp){
        this.currExp = currExp;
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory){
        this.inventory = inventory;
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
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        //dest.writeList(inventory);
        dest.writeInt(currExp);
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        //inventory = in.readArrayList(getClass().getClassLoader());
        currExp = in.readInt();
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
