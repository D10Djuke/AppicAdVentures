package adventures.ad.appic.main.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wikitude.architect.ArchitectView;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Account;
import adventures.ad.appic.main.custom.MessageBox;


public class MainActivity extends ActionBarActivity {

    private Account myAccount = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ArchitectView.isDeviceSupported(this)) {

            setContentView(R.layout.activity_main);

            ((TextView) findViewById(R.id.charName)).setText(myAccount.getCharacter().getName());
            ((TextView) findViewById(R.id.charLvl)).setText(myAccount.getCharacter().getLevelAsText());

            ((TextView) findViewById(R.id.inventory)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), InventoryActivity.class);
                    startActivity(i);
                }
            });

            ((TextView) findViewById(R.id.map)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(i);
                }
            });


        }else{
            new MessageBox("Device Error","Your Device is not Supported", MessageBox.Type.ERROR_BOX,this).popMessage();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
