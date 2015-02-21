package adventures.ad.appic.main.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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
        gridview.setAdapter(new ImageAdapter(this));
        setOnclick();

    }

    private void setOnclick(){
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                selectedItem = mPlayer.getInventory().get(position);
                selectedIndex = position;

                if(mPlayer.getInventory().get(position).getItemType() != Item.Type.VOUCHER){
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), "What do you want to do?", MessageBox.Type.ITEM_BOX, InventoryActivity.this);
                }else{
                    new MessageBox(mPlayer.getInventory().get(position).getItemName(), "Please enter a valid code", MessageBox.Type.VOUCHER_BOX, InventoryActivity.this);
                }
            }
        });
    }

    public Item getSelectedItem(){
        return selectedItem;
    }

    public void useItem(){
        mPlayer.getInventory().remove(mPlayer.getInventory().remove(selectedIndex));
    }

    public void equipItem(){

    }

    public void destroyItem(){
        mPlayer.getInventory().remove(mPlayer.getInventory().remove(selectedIndex));
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

        public ImageAdapter(Context c) {
            mContext = c;

            ArrayList<Integer> tempArr = new ArrayList<Integer>();
            for(Item i: mPlayer.getInventory()){
                String packageName = c.getPackageName();
                int resId = c.getResources().getIdentifier(i.getIconSource(), "drawable", packageName);
                tempArr.add(resId);
            }
            mThumbIds = new Integer[tempArr.size()];
            mThumbIds = tempArr.toArray(mThumbIds);



        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds;
    }
}
