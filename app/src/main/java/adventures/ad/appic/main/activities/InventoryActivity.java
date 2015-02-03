package adventures.ad.appic.main.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import adventures.ad.appic.app.R;
import adventures.ad.appic.web.Connection;

public class InventoryActivity extends ActionBarActivity {

    private Connection c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Connection c = new Connection();
        c.readService();
        TextView t = (TextView) findViewById(R.id.testText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
