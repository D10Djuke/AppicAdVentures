package adventures.ad.appic.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wikitude.architect.ArchitectView;

import adventures.ad.appic.app.R;
import adventures.ad.appic.custom.MessageBox;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ArchitectView.isDeviceSupported(this)) {
            setContentView(R.layout.activity_main);
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
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }
}
