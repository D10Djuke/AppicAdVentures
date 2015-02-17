package adventures.ad.appic.main.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;

public class AccountActivity extends ActionBarActivity {

    private Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent intent = getIntent();
        mPlayer = (Player) intent.getParcelableExtra("mPlayer");
        loadStats();
    }

    private void loadStats(){
        ((TextView) findViewById(R.id.nameStat)).setText(mPlayer.getName());
        ((TextView) findViewById(R.id.lvlStat)).setText(mPlayer.getLevelAsText());

        ((TextView) findViewById(R.id.healthStat)).setText(mPlayer.getStats(0));
        ((TextView) findViewById(R.id.atkStat)).setText(mPlayer.getStats(1));
        ((TextView) findViewById(R.id.defStat)).setText(mPlayer.getStats(2));
        ((TextView) findViewById(R.id.endStat)).setText(mPlayer.getStats(3));
        ((TextView) findViewById(R.id.strStat)).setText(mPlayer.getStats(4));
        ((TextView) findViewById(R.id.stamStat)).setText(mPlayer.getStats(5));
        ((TextView) findViewById(R.id.spirStat)).setText(mPlayer.getStats(6));
        ((TextView) findViewById(R.id.charStat)).setText(mPlayer.getStats(7));
        ((TextView) findViewById(R.id.wisdStat)).setText(mPlayer.getStats(8));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
