package adventures.ad.appic.main.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Item;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.web.Connection;

public class LoginActivity extends FragmentActivity{

    private String user;
    private ProgressDialog mProgressDialog;
    private Connection con;
    private String charName;

    private JSONObject playerObj;
    private JSONObject userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView t = (TextView) findViewById(R.id.usernameTextView);
        user = getUsername();
        t.setText(user);
    }

    public void login(View view) {
        con = new Connection(this);
        new DownloadFilesTask().execute();

        /*Player dummyPlayer = new Player();
        dummyPlayer.setCharacterName("Dummy");
        dummyPlayer.setLvl(1);
        dummyPlayer.setAtk(50);
        dummyPlayer.setDef(12);
        dummyPlayer.setHitPoints(110);
        dummyPlayer.setMaxHitPoints(110);
        dummyPlayer.setStam(10);
        dummyPlayer.setCurrExp(40);
        dummyPlayer.setCharImgID(1);

        createInventory(dummyPlayer);

        login(dummyPlayer);*/

    }



    private void createInventory(Player p){
        //TODO delete method

        Item i1 = new Item();
        i1.setItemName("Zwaard");
        i1.setItemDescription("een zwaard");
        i1.setType("00");
        i1.setItemID("ADR15");
        i1.setIconSource();

        Item i2 = new Item();
        i2.setItemName("Voucher");
        i2.setItemDescription("een voucher");
        i2.setType("04");
        i2.setItemID("A556F");
        i2.setIconSource();

        Item i3 = new Item();
        i3.setItemName("Potion");
        i3.setItemDescription("een potion");
        i3.setType("03");
        i3.setItemID("AF8F5");
        i3.setIconSource();

        ArrayList<Item> inv = new ArrayList<>();
        inv.add(i1);
        inv.add(i2);
        inv.add(i3);
        p.setInventory(inv);

    }

    public void createNewAccount(View view) {
        //Log.e("Userid:",""+user);
        //Log.e("userid:", con.confirmUser(user)+"");
        Log.e("Userid:",user);
        //Log.e("userid:", con.confirmUser(user)+"");
        con = new Connection(this);
     //   Log.e("userid:", con.confirmUser(user)+"");

        new TestTask().execute();
    }

    public void changeAccount(View view){
        new MessageBox("Change Account", "Please choose an account", MessageBox.Type.ACCOUNTPICK_BOX, this).popMessage();
    }

    public void changeUserName(String user){
        TextView t = (TextView) findViewById(R.id.usernameTextView);
        t.setText(user);
        this.user = user;
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

    public void login(Player character){

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("mPlayer", character);
        startActivity(i);
    }

    public void createNew(String name) {

        charName = name;

        con = new Connection(this);
        new CreateFilesTask().execute();
    }

    private class TestTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params){
            Integer trueUser = con.confirmUser(user);

            return trueUser;
        }

        @Override
        protected void onPostExecute(Integer i) {
            //Log.e("userid:", con.confirmUser(user)+"");

            if(i == -1) {
                new MessageBox("New Account", "Please choose a character name", MessageBox.Type.NEWACCOUNT_BOX, LoginActivity.this).popMessage();
            }
            else {
                new MessageBox("New Account", "You already have an character", MessageBox.Type.MESSAGE_BOX, LoginActivity.this).popMessage();
            }
        }
    }

    private class CreateFilesTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setTitle("Creating");
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(false);
          //  mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String createFlag = "USER";

            int trueUser = con.confirmUser(charName);

            userObj = new JSONObject();
            playerObj = new JSONObject();
            try {
                userObj.put("name", user);
                userObj.put("googleID", user);

                playerObj.put("name", charName);
                playerObj.put("userId", trueUser);

            } catch (JSONException e) {
                //new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, LoginActivity.this).popMessage();
            }

            if(trueUser == -1){
                Log.d("Create", "creating");
                con.create(userObj, createFlag);

                createFlag = "CHARACTER";
                con.create(playerObj, createFlag);
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            if(mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

        }
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, Player> {
        boolean success = true;
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setTitle("Getting UserData");
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        protected Player doInBackground(Void... urls) {
            int trueUser = con.confirmUser(user);

            Player result = null;

            if(trueUser >= 0) {
                result = con.getPlayer(trueUser);
                result.setInventory(con.getInventory(trueUser));

            }else{
                mProgressDialog.dismiss();
                success = false;

            }
            return result;
        }

        protected void onPostExecute(Player result) {
            if(!success)
            {
                new MessageBox("No Account Found", "No account could be found linked to your google-id. \n Please create one.", MessageBox.Type.MESSAGE_BOX,LoginActivity.this).popMessage();
            }

            else if(result==null)
            {
                new MessageBox("Error", "Result returned null", MessageBox.Type.ERROR_BOX,LoginActivity.this).popMessage();
            }else{
                login(result);
            }

            if(mProgressDialog != null) {
               mProgressDialog.dismiss();
            }
        }
    }
}