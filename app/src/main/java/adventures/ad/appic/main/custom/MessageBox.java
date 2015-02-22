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
import adventures.ad.appic.game.Item;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.activities.AccountActivity;
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
        NEWACCOUNT_BOX,
        STANDARD_ERROR_BOX,
        FLEE_BOX,
        VOUCHER_BOX,
        CHARIMG_BOX,
        ITEM_BOX,
        DEFEAT_BOX,
        EQUIPPED_BOX
    }


    public MessageBox(String title, String s, Type type, Context context){
        this.title = title;
        this.s = s;
        this.context = context;

        setMessageType(type);

    }

    public MessageBox(Type type, Context context){
        title = "title comes here";
        s = "message comes here";
        this.context = context;

        setMessageType(type);

    }

    public void setPlayer(Player p)
    {
        mPlayer = p;
    }

    private void setMessageType(Type type){

        switch (type.toString()){
            case "STANDARD_ERROR_BOX":

                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage("We're terribly sorry, but something went wrong. \n Please restart the application.");
                aBuilder.setTitle("Oops");
                aBuilder.setCancelable(false);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });

                messageBox = aBuilder.create();
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
            case "FLEE_BOX":
                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                aBuilder.setPositiveButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                    }
                });

                aBuilder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                        ((Activity) context).finish();
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
            case "DEFEAT_BOX":
                aBuilder = new AlertDialog.Builder(context);
                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                        ((Activity) context).finish();
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
                break;
            case "VOUCHER_BOX":
                aBuilder = new AlertDialog.Builder(context);
                final InventoryActivity invAct = (InventoryActivity) context;

                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(false);

                LayoutInflater inflater3 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogView2 = inflater3.inflate(R.layout.dialog_voucher, null);
                aBuilder.setView(dialogView2);

                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText editText = (EditText) dialogView2.findViewById(R.id.voucher_code_field);

                        if(!editText.getText().toString().equals("")){
                            invAct.validateCode(editText.getText().toString());
                            messageBox.dismiss();
                        }
                    }
                });
                aBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                    }
                });

                messageBox = aBuilder.create();
                break;
            case "ITEM_BOX":
                aBuilder = new AlertDialog.Builder(context);
                final InventoryActivity invAct2 = (InventoryActivity) context;

                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(true);

                LayoutInflater inflater4 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogView2 = inflater4.inflate(R.layout.dialog_item, null);
                aBuilder.setView(dialogView2);

                if(invAct2.getSelectedItem().getIconSource().equals("ico03")){
                    aBuilder.setPositiveButton("USE", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                                invAct2.useItem();
                                messageBox.dismiss();
                        }
                    });
                }else if(!invAct2.getSelectedItem().isEquipped()) {
                    aBuilder.setPositiveButton("EQUIP", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            invAct2.equipItem(invAct2.getSelectedItem());
                            messageBox.dismiss();
                        }
                    });

                    aBuilder.setNegativeButton("DESTROY", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            invAct2.destroyItem();
                            messageBox.dismiss();
                        }
                    });

                }
                    messageBox = aBuilder.create();
                    break;
                    case "EQUIPPED_BOX":
                        aBuilder = new AlertDialog.Builder(context);
                        final AccountActivity accAct = (AccountActivity) context;

                        aBuilder.setCancelable(false);

                        if (accAct.getSelectedItem().getIconSource().equals("blank")) {

                            aBuilder.setTitle("No item equipped");
                            aBuilder.setMessage("This spot is empty. \n Equip an item in the Iventory");

                            aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    messageBox.dismiss();
                                }
                            });
                        } else {
                            aBuilder.setTitle(accAct.getSelectedItem().getItemName());
                            aBuilder.setMessage(accAct.getSelectedItem().getItemDescription());

                            aBuilder.setPositiveButton("UNEQUIP", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    accAct.unEquipItem(accAct.getSelectedItem());
                                    messageBox.dismiss();
                                }
                            });
                            aBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    messageBox.dismiss();
                                }
                            });
                        }

                        messageBox = aBuilder.create();
                        break;
            case "CHARIMG_BOX":
                aBuilder = new AlertDialog.Builder(context);
                final AccountActivity accAct2 = (AccountActivity) context;

                aBuilder.setMessage(s);
                aBuilder.setTitle(title);
                aBuilder.setCancelable(true);

                LayoutInflater inflater8 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogView2 = inflater8.inflate(R.layout.dialog_charimg, null);
                aBuilder.setView(dialogView2);
                messageBox = aBuilder.create();
                break;
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

    public void killMessage(){
        messageBox.dismiss();;
    }

    public void popMessage(){
        messageBox.show();
    }

    public boolean isShowing() { return isShowing(); }

}
