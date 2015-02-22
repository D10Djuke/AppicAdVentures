package adventures.ad.appic.main.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Item;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.ImageAdapter;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.web.Connection;

public class InventoryActivity extends ActionBarActivity {

    private Player mPlayer;

    private GridView gridview;

    private Item selectedItem;
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent intent = getIntent();
        mPlayer = (Player) intent.getParcelableExtra("mPlayer");

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, mPlayer.getInventory(), false));
        setOnclick();

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("mPlayer", mPlayer);
        setResult(Activity.RESULT_OK, data);
        super.onBackPressed();
    }

    private void setOnclick(){
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                selectedItem = mPlayer.getInventory().get(position);
                selectedIndex = position;

                if(!mPlayer.getInventory().get(position).getIconSource().equals("ico04")&&(!(mPlayer.getInventory().get(position).isEquipped()))){
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), "What do you want to do?", MessageBox.Type.ITEM_BOX, InventoryActivity.this).popMessage();
                }else if (mPlayer.getInventory().get(position).getIconSource().equals("ico04")){
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), "Please enter a valid code", MessageBox.Type.VOUCHER_BOX, InventoryActivity.this).popMessage();
                }else{
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), mPlayer.getInventory().get(position).getItemDescription(), MessageBox.Type.MESSAGE_BOX, InventoryActivity.this).popMessage();
                }
            }
        });
    }

    public Item getSelectedItem(){
        return selectedItem;
    }

    public void useItem(){
        mPlayer.getInventory().remove(mPlayer.getInventory().remove(selectedIndex));
        destroyItem();
    }

    public Player getPlayer(){
        return mPlayer;
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    public void equipItem(Item item){
        item.setEquipped(true);
        mPlayer.equipItem(item);
        reInit();
    }

    public void reInit(){
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, mPlayer.getInventory(), false));
        setOnclick();
    }

    public void destroyItem() {
        mPlayer.getInventory().remove(selectedIndex);
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, mPlayer.getInventory(), false));
        setOnclick();
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

    public void validateCode(String code){
        boolean succes = true;

        //TODO boolean succes = new Connection().validateVoucher(code);

        if(!succes){

        }
    }
}
