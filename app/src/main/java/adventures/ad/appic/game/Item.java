package adventures.ad.appic.game;

import android.content.Context;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import adventures.ad.appic.app.R;
import adventures.ad.appic.manager.DataManager;

/**
 * Created by Jory on 25/01/2015.
 */
public class Item implements Parcelable{

    private Image icon;

    private String iconSource;
    private String itemID;
    private String itemName;
    private String itemDescription;
    private String itemType;

    private Context c;

    public Item(String itemID, DataManager data){

        c = data.getContext();

        this.itemID = itemID;
        loadItemData();
        setIconSource();
    }

    private void loadItemData(){

        String items = ReadFromfile();

        String[] itemDataString = items.split("à");

        for(String s : itemDataString){

            String itemData[] = s.split("é");
                if(itemData[0].equals(itemID)){
                    itemType = itemData[1];
                    itemName = itemData[2];
                    itemDescription = itemData[3];
                }
        }
    }

    public String ReadFromfile() {

        StringBuilder sb = new StringBuilder();

        for(int i = 1; i<=5; i++){

            String itemName = "ITEM00" + i;

            String packageName = c.getPackageName();
            int resId = c.getResources().getIdentifier(itemName, "string", packageName);

            sb.append(c.getResources().getString(resId));
        }

        return sb.toString();
    }

    private void setIconSource(){
       iconSource = "ico" + itemType;
    }

    public String getItemName(){
        return itemName;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public String getIconSource(){
        return iconSource;
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
    }

    private void readFromParcel(Parcel in) {
        itemID = in.readString();
        itemName = in.readString();
        iconSource = in.readString();
        itemDescription = in.readString();
        itemType = in.readString();
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
