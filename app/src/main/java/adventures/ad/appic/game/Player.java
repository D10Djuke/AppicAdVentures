package adventures.ad.appic.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class Player extends Character implements Parcelable {

    private int currExp = 0;
    private ArrayList<Item> inventory;
    private Item[] equippedItems = new Item[12];
    private ArrayList<Item> armory = new ArrayList<Item>();

    private Item weaponItem = null;
    private Item offHeldItem = null;
    private Item headItem = null;
    private Item legsItem = null;
    private Item bodyItem = null;
    private Item feetItem = null;
    private Item shoulderItem = null;
    private Item trinketItem = null;
    private Item relicItem = null;


    private ArrayList<String> stats  = new ArrayList<>(9);

    public Player(){
        for(int i = 0; i<12;i++){
            Item item = new Item("blank");
            equippedItems[i] = item;
        }

        armory = new ArrayList<Item>(Arrays.asList(equippedItems));
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

    public void equipItem(Item item){
        Item it = item;
        switch(item.getItemType()){
            case "BODY":
                    bodyItem = item;
                    equippedItems[4] = item;
                break;
            case "WEAPON":
                weaponItem = item;
                equippedItems[9] = item;
                break;
        }

        armory = new ArrayList<Item>(Arrays.asList(equippedItems));
    }

    public void unEquipItem(Item item){

    }

    public ArrayList<Item> getEquippedItems(){
        return armory;
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
        dest.writeList(inventory);
        dest.writeList(armory);
        dest.writeInt(currExp);
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        inventory = in.readArrayList(getClass().getClassLoader());
        armory = in.readArrayList(getClass().getClassLoader());
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
