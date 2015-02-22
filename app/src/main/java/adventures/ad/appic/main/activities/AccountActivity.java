package adventures.ad.appic.main.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;
import java.util.Arrays;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Item;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.ImageAdapter;
import adventures.ad.appic.main.custom.MessageBox;

public class AccountActivity extends ActionBarActivity {

    private Player mPlayer;
    private GridView gridview;

    private ArrayList<Item> armorList;

    private Item selectedItem;
    private int selectedIndex;

    private ImageView imgView;

    private ProgressBar expBar;
    private MessageBox mBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent intent = getIntent();

        mPlayer = (Player) intent.getParcelableExtra("mPlayer");

        imgView = (ImageView) findViewById(R.id.characterImg);
        String headimg = "img_char_head" + mPlayer.getCharImgID();
        int resId = getResources().getIdentifier(headimg, "drawable", getPackageName());

        imgView.setBackgroundResource(resId);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBox = new MessageBox("Choose your character", "Click on an image to pick a img", MessageBox.Type.CHARIMG_BOX, AccountActivity.this);
                mBox.popMessage();
            }
        });

        expBar = (ProgressBar) findViewById(R.id.expbar);

        double maxExp = 100*mPlayer.getLvl();

        expBar.setProgress((int) ((mPlayer.getCurrExp()/maxExp)*100));

        TextView expText = (TextView) findViewById(R.id.expView);
        expText.setText(((int)mPlayer.getCurrExp() + "/" + (100* mPlayer.getLvl())));
        expText.setTextColor(Color.WHITE);
        expText.setTextSize(25);

        gridview = (GridView) findViewById(R.id.armorView);

        armorList = mPlayer.getEquippedItems();

        gridview.setAdapter(new ImageAdapter(this, armorList, true));
        setOnclick();

        loadStats();
    }

    public void changeCharImg(View v){

        mBox.killMessage();

        mPlayer.setCharImgID(Integer.parseInt(v.getTag().toString()));

        String headimg = "img_char_head" + mPlayer.getCharImgID();
        int resId = getResources().getIdentifier(headimg, "drawable", getPackageName());

        imgView.setBackgroundResource(resId);
    }

    public void unEquipItem(Item item){
        item.setEquipped(false);
        mPlayer.unEquipItem(getSelectedIndex(), item);
    }

    private void setOnclick() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                selectedIndex = position;
                selectedItem = armorList.get(position);

                new MessageBox("","", MessageBox.Type.EQUIPPED_BOX, AccountActivity.this).popMessage();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("mPlayer", mPlayer);
        setResult(Activity.RESULT_OK, data);
        super.onBackPressed();
    }

    public Item getSelectedItem(){
        return selectedItem;
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    public Player getPlayer(){
        return mPlayer;
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
