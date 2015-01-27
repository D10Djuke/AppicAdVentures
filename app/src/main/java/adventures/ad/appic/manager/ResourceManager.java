package adventures.ad.appic.manager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jory on 27/01/2015.
 */
public class ResourceManager {

    private String gUserName;


    public ResourceManager(Context c){
        initGUsername(c);
    }

    public String initGUsername(Context c) {
        AccountManager manager = AccountManager.get(c);
        Account[] accounts = manager.getAccountsByType("com.google");
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

    public String getgUserName(){
        return gUserName;
    }
}
