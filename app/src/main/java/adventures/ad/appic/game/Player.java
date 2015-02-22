package adventures.ad.appic.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class Player extends Character implements Parcelable {

    private double currExp = 0;
    private ArrayList<Item> inventory;
    //private Item[] equippedItems = new Item[12];
    private ArrayList<Item> armory = new ArrayList<Item>();
    private int charImgID;

    private Item weaponItem = null;
    private Item offHeldItem = null;
    private Item headItem = null;
    private Item legsItem = null;
    private Item bodyItem = null;
    private Item feetItem = null;
    private Item shoulderItem = null;
    private Item trinketItem = null;
    private Item relicItem = null;
    private Item handItem = null;


    private ArrayList<String> stats  = new ArrayList<>(9);

    public Player(){
        for(int i = 0; i<12;i++){
            Item item = new Item("blank");
            armory.add(item);
        }
        //armory = new ArrayList<Item>(Arrays.asList(equippedItems));
    }

    public double getCurrExp(){
        return currExp;
    }

    public void setCurrExp(double currExp){
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

    public void setCharImgID(int id){
        this.charImgID = id;
    }

    public int getCharImgID(){
        return  charImgID;
    }

    public void equipItem(Item item){
        switch(item.getItemType()){
            case "RELIC":
                relicItem = item;
                armory.set(0,item);
                break;
            case "HEAD":
                headItem = item;
                armory.set(1,item);
                break;
            case "TRINKET":
                trinketItem = item;
                armory.set(2,item);
                break;
            case "SHOULDER":
                shoulderItem = item;
                armory.set(3,item);
                armory.set(5,item);
                break;
            case "BODY":
                bodyItem = item;
                armory.set(4,item);
                break;
            case "HAND":
                handItem = item;
                armory.set(6,item);
                armory.set(8,item);
                break;
            case "LEGS":
                legsItem = item;
                armory.set(7,item);
                break;
            case "WEAPON":
                weaponItem = item;
                armory.set(9,item);
                break;
            case "FEET":
                feetItem = item;
                armory.set(10,item);
                break;
            case "OFFHELD":
                offHeldItem = item;
                armory.set(11,item);
                break;
        }
    }

    public void unEquipItem(int position, Item item){
        armory.set(position, new Item("blank"));

        if(position == 6){
            armory.set(8, new Item("blank"));
        }
        if(position == 3){
            armory.set(5, new Item("blank"));
        }

        for(Item i : inventory){
            if(i.getItemID().equals(item.getItemID())){
                i.setEquipped(false);
            }
        }
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
        dest.writeDouble(currExp);
        dest.writeInt(charImgID);
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        inventory = in.readArrayList(getClass().getClassLoader());
        armory = in.readArrayList(getClass().getClassLoader());
        currExp = in.readDouble();
        charImgID = in.readInt();
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
