package adventures.ad.appic.main.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.manager.DataManager;
import adventures.ad.appic.web.Connection;

public class LoginActivity extends FragmentActivity{

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView t = (TextView) findViewById(R.id.usernameTextView);
        user = getUsername();
        t.setText(user);
    }

    public void login(View view) {

        Boolean trueUser = true;
        //Boolean trueUser = new Connection(getApplicationContext()).confirmUser(user, passText.getText().toString());

        if(trueUser) {

            //String characterData = new Connection(getApplicationContext()).getCharacter(user);
            login();
        }else{
            new MessageBox("No Account Found", "No account could be found linked to your google-id. \n Please create one.", MessageBox.Type.MESSAGE_BOX,this).popMessage();
        }
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

    private void login(){
        String characterData = "APPIC;Guido;5;10";

        DataManager dataM = new DataManager(characterData, getApplicationContext());
        Player player = dataM.getmPlayer();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("mPlayer", player);
        startActivity(i);
    }

    public void createNew(String name){
        //TODO write connenction "create new user"
        login();
    }
}