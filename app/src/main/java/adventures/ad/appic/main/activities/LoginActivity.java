package adventures.ad.appic.main.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.manager.DataManager;
import adventures.ad.appic.web.Connection;

public class LoginActivity extends FragmentActivity{

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView t = (TextView) findViewById(R.id.textView);
        user = getUsername();
        t.setText(user);

    }

    public void login(View view) {

        EditText passText = (EditText) findViewById(R.id.editText);

        Boolean trueUser = new Connection().confirmUser(user, passText.getText().toString());

        if(trueUser) {
            //String userData = new Connection().getUser(user);
            String userData = "APPIC;Guido;1";
            DataManager dataM = new DataManager(userData);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }else{
            new MessageBox("No Account Found", "No account could be found linked to your google-id. Do you wish to create one?", MessageBox.Type.MESSAGE_BOX,this).popMessage();
        }
    }

    public void createNewAccount(View view) {
        // TODO create new account
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();
        //Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type
            // values.
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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}