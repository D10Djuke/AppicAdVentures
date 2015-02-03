package adventures.ad.appic.main.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import adventures.ad.appic.main.activities.MapActivity;
import adventures.ad.appic.main.activities.SettingsActivity;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class MessageBox{

    private AlertDialog messageBox;
    private Context mContext;


    public enum Type{
        ERROR_BOX,
        MESSAGE_BOX,
        TEST_BOX,
        LOGIN_BOX
    }


    public MessageBox(String title, String s, Type type, Context context){
        messageBox = new AlertDialog.Builder(context).create();
        messageBox.setMessage(s);
        messageBox.setTitle(title);
        this.mContext = context;

        setMessageType(type);

    }

    private void setMessageType(Type type){

        switch (type.toString()){
            case "ERROR_BOX":
                messageBox.setCancelable(false);
                messageBox.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
            break;
            case "MESSAGE_BOX":
                messageBox.setCancelable(false);
                messageBox.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                    }
                });
            break;
            case "LOGIN_BOX":
                messageBox.setCancelable(false);
                messageBox.setButton(Dialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                    }
                });
                messageBox.setButton(Dialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
            break;
            case "TEST_BOX":
                messageBox.setCancelable(false);
                messageBox.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        messageBox.dismiss();
                        ((Activity) mContext).finish();
                        Intent i = new Intent(mContext, SettingsActivity.class);
                        mContext.startActivity(i);
                    }
                });
            break;
        }
    }

    public void popMessage(){ messageBox.show(); }
    public boolean isShowing() { return messageBox.isShowing(); }

}
