package adventures.ad.appic.main.activities;

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
        //mDataMan = (DataManager) intent.getParcelableExtra("mDataMan");
        mPlayer = (Player) intent.getParcelableExtra("mPlayer");

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, mPlayer.getInventory()));
        setOnclick();

    }

    private void setOnclick(){
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.d("here", ""+position);

                selectedItem = mPlayer.getInventory().get(position);
                selectedIndex = position;

                if(!mPlayer.getInventory().get(position).getIconSource().equals("ico04")){
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), "What do you want to do?", MessageBox.Type.ITEM_BOX, InventoryActivity.this).popMessage();
                }else{
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), "Please enter a valid code", MessageBox.Type.VOUCHER_BOX, InventoryActivity.this).popMessage();
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

    public void equipItem(){

    }

    public void destroyItem() {
        mPlayer.getInventory().remove(selectedIndex);
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, mPlayer.getInventory()));
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

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Item[] values;

        public ImageAdapter(Context c, ArrayList<Item> inventory) {
            mContext = c;
            values = new Item[inventory.size()];
            values = inventory.toArray(values);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(mContext);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.grid_inventory, null);

                // set image based on selected text
                ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

                Log.d("test: ", ""+ values[position].getIconSource());
                switch (values[position].getIconSource()) {
                    case "ico00":
                        imageView.setImageResource(R.drawable.ico00);
                        break;
                    case "ico03":
                        imageView.setImageResource(R.drawable.ico03);
                        break;
                    case "ico04":
                        imageView.setImageResource(R.drawable.ico04);
                        break;
                }

            } else {
                gridView = (View) convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
}
