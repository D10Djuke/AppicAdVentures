package adventures.ad.appic.game;

import android.content.Context;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import adventures.ad.appic.app.R;

/**
 * Created by Jory on 25/01/2015.
 */
public class Item implements Parcelable{

    private Image icon;

    private boolean isEquipped;

    private String iconSource;
    private String itemID;
    private String itemName;
    private String itemDescription;
    private String itemType;
    private String type;

    private Context c;

    public Item(){

    }

    public void setEquipped(boolean isEquipped){
        this.isEquipped = isEquipped;
    }

    public boolean isEquipped(){
        return isEquipped;
    }

    public Item(String iconSource){
        this.iconSource = iconSource;
    }

    public enum Type{
        WEAPON,
        HEAD,
        BODY,
        VOUCHER,
        LEGS,
        FEET,
        POTION,
        BLANK
    }

    public void setIconSource(){
       iconSource = "ico" + itemType;
    }

    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public void setItemDescription(String itemDescription){
        this.itemDescription = itemDescription;
    }

    public String getItemID(){
        return itemID;
    }

    public void setItemID(String itemID){
        this.itemID = itemID;
    }

    public String getIconSource(){
        return iconSource;
    }

    public String getItemType(){
        return type;
    }

    public void setType(String itemType){
        this.itemType = itemType;
        switch (itemType){
            case "00":
                type = Type.WEAPON.toString();
                break;
            case "01":
                type = Type.HEAD.toString();
                break;
            case "02":
                type = Type.BODY.toString();
                break;
            case "03":
                type = Type.POTION.toString();
                break;
            case "04":
                type = Type.VOUCHER.toString();
                break;
            case "05":
                type = Type.LEGS.toString();
                break;
            case "06":
                type = Type.FEET.toString();
                break;
            case "blank":
                type= Type.BLANK.toString();
                break;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemID);
        dest.writeString(itemName);
        dest.writeString(iconSource);
        dest.writeString(itemDescription);
        dest.writeString(itemType);
        dest.writeString(type);
        dest.writeByte((byte) (isEquipped ? 1 : 0));
    }

    private void readFromParcel(Parcel in) {
        itemID = in.readString();
        itemName = in.readString();
        iconSource = in.readString();
        itemDescription = in.readString();
        itemType = in.readString();
        type = in.readString();
        isEquipped = in.readByte() != 0;
    }

    public Item(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
