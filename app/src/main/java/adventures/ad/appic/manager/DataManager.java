package adventures.ad.appic.manager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import adventures.ad.appic.game.Item;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.web.Connection;

/**
 * Created by Jory on 27/01/2015.
 */
public class DataManager implements Parcelable{

    private static final long serialVersionUID = 1L;

    private Player mPlayer;
    private Context c;

    public DataManager(String accountData, Context c){
        this.c = c;
        loadAccountData(accountData);
    }

    private void loadAccountData(String accountData){
        mPlayer = new Player();
        String[] data = accountData.split(";");

        ArrayList<Item> inventory = newInventory(data[0]);

        mPlayer.setBasic(data[1], Integer.parseInt(data[2]), inventory, Integer.parseInt(data[3]));
    }

    private ArrayList<Item> newInventory(String user){

        Item i;

        ArrayList<Item> inventory = new ArrayList<>();

        String inventoryData = new Connection(c).getUserInventory(user);

        String[] itemIDs = inventoryData.split(";");

        for(String id : itemIDs){
            i = new Item(id, this);
            inventory.add(i);
        }

        return inventory;
    }

    public Context getContext(){

        return c;
    }

    public Player getmPlayer(){
        return mPlayer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    private void readFromParcel(Parcel in) {

    }

    public DataManager(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator<DataManager> CREATOR = new Parcelable.Creator<DataManager>() {

        @Override
        public DataManager createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public DataManager[] newArray(int size) {
            return null;
        }
    };
}
