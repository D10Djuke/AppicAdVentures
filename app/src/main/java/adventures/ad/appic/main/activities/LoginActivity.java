package adventures.ad.appic.main.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.manager.DataManager;
import adventures.ad.appic.web.Connection;

public class LoginActivity extends FragmentActivity{

    private String user;
    private ProgressDialog mProgressDialog;
    private Connection con;
    private Player player;
    private String charName;
    private int userId;

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
    }

    public void createNewAccount(View view) {
        new MessageBox("New Account", "Please choose a character name", MessageBox.Type.NEWACCOUNT_BOX, this).popMessage();
    }

    public void changeAccount(View view){
        new MessageBox("Change Account", "Please choose an account", MessageBox.Type.ACCOUNTPICK_BOX, this).popMessage();
    }

    public void changeUserName(String user){
        TextView t = (TextView) findViewById(R.id.usernameTextView);
        t.setText(user);
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

    public void login(String characterData){
        DataManager dataM = new DataManager(characterData, getApplicationContext());
        player = dataM.getmPlayer();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("mPlayer", player);
        startActivity(i);
    }

    public void createNew(String name){

        charName = name;

        con = new Connection(this);
        new CreateFilesTask().execute();
        new DownloadFilesTask().execute();
    }

    private class CreateFilesTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute(String userName) {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setTitle("Creating");
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(false);
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
                new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, LoginActivity.this).popMessage();
            }

            if(trueUser != -1){
                con.create(userObj, createFlag);

                createFlag = "CHARACTER";
                con.create(playerObj, createFlag);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if(mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            login(result);
        }
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, String> {
        boolean success = true;
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setTitle("Getting UserData");
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        protected String doInBackground(Void... urls) {
            int trueUser = con.confirmUser(user);


            String result = "default";

            if(trueUser >= 0) {
                result = con.getPlayerData(trueUser);
            }else{
                mProgressDialog.dismiss();
                success = false;

            }
            return result;
        }

        protected void onPostExecute(String result) {
            if(!success)
            {
                new MessageBox("No Account Found", "No account could be found linked to your google-id. \n Please create one.", MessageBox.Type.MESSAGE_BOX,LoginActivity.this).popMessage();
            }
           if(mProgressDialog != null) {
               mProgressDialog.dismiss();
           }

            login(result);
        }
    }
}