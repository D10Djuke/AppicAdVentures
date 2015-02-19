package adventures.ad.appic.main.custom;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.activities.InventoryActivity;
import adventures.ad.appic.main.activities.LoginActivity;
import adventures.ad.appic.main.activities.SettingsActivity;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class MessageBox{

    AlertDialog.Builder aBuilder;

    private AlertDialog messageBox = null;
    private Player mPlayer;

    private LoginActivity logAct;
    private ArrayList<String> accArr;
    private Spinner spinner;

    private String title;
    private String s;
    private Context context;

    private View dialogView2;

    public enum Type{
        ERROR_BOX,
        MESSAGE_BOX,
        TEST_BOX,
        VICTORY_BOX,
        ACCOUNTPICK_BOX,
        NEWACCOUNT_BOX
    }


    public MessageBox(String title, String s, Type type, Context context){
        this.title = title;
        this.s = s;
        this.context = context;

        setMessageType(type);

    }

    public void setPlayer(Player p)
    {
        mPlayer = p;
    }

    private void setMessageType(Type type){

        switch (type.toString()){
            case "ERROR_BOX":

                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });

                messageBox = aBuilder.create();

            break;
            case "MESSAGE_BOX":
                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);
                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                    }
                });

                messageBox = aBuilder.create();

            break;
            case "TEST_BOX":
                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                        ((Activity) context).finish();
                        Intent i = new Intent(context, SettingsActivity.class);
                        context.startActivity(i);
                    }
                });

                messageBox = aBuilder.create();

            break;
            case "VICTORY_BOX":
                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                        ((Activity) context).finish();
                        Intent i = new Intent(context, InventoryActivity.class);
                        i.putExtra("mPlayer", mPlayer);
                        context.startActivity(i);
                    }
                });

                messageBox = aBuilder.create();

            break;
            case "ACCOUNTPICK_BOX":

                aBuilder = new AlertDialog.Builder(context);
                logAct = (LoginActivity) context;

                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_accountpick, null);
                aBuilder.setView(dialogView);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        logAct.changeUserName(spinner.getSelectedItem().toString());
                        messageBox.dismiss();
                    }
                });

                aBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                    }
                });

                messageBox = aBuilder.create();

                accArr = new ArrayList<String>();
                populate(accArr);

                spinner = (Spinner) dialogView.findViewById(R.id.account_pick_spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(logAct, android.R.layout.simple_spinner_item, accArr);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                break;
            case "NEWACCOUNT_BOX":
                aBuilder = new AlertDialog.Builder(context);
                logAct = (LoginActivity) context;

                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogView2 = inflater2.inflate(R.layout.dialog_newaccount, null);
                aBuilder.setView(dialogView2);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText ed = (EditText) dialogView2.findViewById(R.id.account_new_name);
                        logAct.createNew(ed.getText().toString());

                        messageBox.dismiss();
                    }
                });

                messageBox = aBuilder.create();
        }
    }

    private void populate(ArrayList<String> arr){
        AccountManager manager = AccountManager.get(logAct);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            for(String email : possibleEmails) {
                String[] parts = email.split("@");
                arr.add(parts[0]);
            }
        }
    }

    public void popMessage(){
        messageBox.show();
    }

    public boolean isShowing() { return isShowing(); }

}
