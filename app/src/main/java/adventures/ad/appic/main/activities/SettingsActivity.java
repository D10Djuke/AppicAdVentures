package adventures.ad.appic.main.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.wikitude.architect.ArchitectView;

import adventures.ad.appic.app.R;

public class SettingsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_settings);

        if(ArchitectView.isDeviceSupported(this)) {

            setContentView(R.layout.activity_settings);

            ((Switch) findViewById(R.id.soundSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.v("Switch State=", "" + isChecked);
                }

            });

            ((Switch) findViewById(R.id.musicSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.v("Switch State=", "" + isChecked);
                }
            });

            ((TextView) findViewById(R.id.soundTextView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( ((Switch) findViewById(R.id.soundSwitch)).isChecked())
                        ((Switch) findViewById(R.id.soundSwitch)).setChecked(false);
                    else {
                        ((Switch) findViewById(R.id.soundSwitch)).setChecked(true);
                    }
                }
            });

            ((TextView) findViewById(R.id.musicTextView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( ((Switch) findViewById(R.id.musicSwitch)).isChecked())
                        ((Switch) findViewById(R.id.musicSwitch)).setChecked(false);
                    else {
                        ((Switch) findViewById(R.id.musicSwitch)).setChecked(true);
                    }
                }
            });

            ((Button)findViewById(R.id.GPSButton)).setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
