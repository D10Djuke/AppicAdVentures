package adventures.ad.appic.manager;

import android.accounts.AccountManager;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;


import adventures.ad.appic.web.Connection;

/**
 * Created by Jory on 27/01/2015.
 */
public class DataManager {

    private Connection conn = null;
    private Context c = null;

    public DataManager(Context c){
        conn = new Connection(c);
    }

    public Connection getConnection(){
        return conn;
    }

    public void getUserData(String gUserName){

    }





}
