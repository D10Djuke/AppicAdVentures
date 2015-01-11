package adventures.ad.appic.main.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by 11305205 on 10/01/2015.
 */
public class MessageBox{

    private AlertDialog.Builder messageBox;

    public enum Type{
           ERROR_BOX
    }


    public MessageBox(String title, String s, Type type, Context context){
        messageBox = new AlertDialog.Builder(context);
        messageBox.setMessage(s);
        messageBox.setTitle(title);

        setMessageType(type);

    }

    private void setMessageType(Type type){

        switch (type.toString()){
            case "ERROR_BOX":
                messageBox.setCancelable(false);
                messageBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
            break;
        }
    }

    public void popMessage(){
        messageBox.show();
    }
}
