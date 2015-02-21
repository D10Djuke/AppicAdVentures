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
        ((TextView) findViewById(R.id.nameStat)).setText(mPlayer.getCharacterName());
        ((TextView) findViewById(R.id.lvlStat)).setText(Integer.toString(mPlayer.getLvl()));

        ((TextView) findViewById(R.id.healthStat)).setText(Integer.toString(mPlayer.getHitPoints()));
        ((TextView) findViewById(R.id.atkStat)).setText(Integer.toString(mPlayer.getAtk()));
        ((TextView) findViewById(R.id.defStat)).setText(Integer.toString(mPlayer.getDef()));
        ((TextView) findViewById(R.id.stamStat)).setText(Integer.toString(mPlayer.getStam()));

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
