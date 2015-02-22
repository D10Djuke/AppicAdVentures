package adventures.ad.appic.main.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Item;
import adventures.ad.appic.main.activities.InventoryActivity;

/**
 * Created by Jory on 21/02/2015.
 */
public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Item[] values;
        private boolean forArmory;

        public ImageAdapter(Context c, ArrayList<Item> inventory, boolean forArmory) {
            mContext = c;
            this.forArmory = forArmory;
            values = new Item[inventory.size()];
            values = inventory.toArray(values);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(mContext);

                if(forArmory){

                    gridView = inflater.inflate(R.layout.grid_armory, null);

                    ImageView imageView = (ImageView) gridView.findViewById(R.id.armor_view);

                    Bitmap backImg = null;
                            switch(position){
                                case 0:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_1);
                                    break;
                                case 1:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_2);
                                    break;
                                case 2:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_3);
                                    break;
                                case 3:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_4);
                                    break;
                                case 4:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_5);
                                    break;
                                case 5:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_6);
                                    break;
                                case 6:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_7);
                                    break;
                                case 7:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_8);
                                    break;
                                case 8:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_9);
                                    break;
                                case 9:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_10);
                                    break;
                                case 10:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_11);
                                    break;
                                case 11:
                                    backImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_armory_12);
                                    break;
                            }

                    imageView.setImageBitmap(backImg);
                    if(values[position].isEquipped()){
                        imageView.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                    }

                }else {

                    gridView = inflater.inflate(R.layout.grid_inventory, null);
                    ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

                    switch (values[position].getIconSource()) {
                        case "ico00":
                            imageView.setImageResource(R.drawable.ico00);
                            break;
                        case "ico01":
                            imageView.setImageResource(R.drawable.ico01);
                            break;
                        case "ico02":
                            imageView.setImageResource(R.drawable.ico02);
                            break;
                        case "ico03":
                            imageView.setImageResource(R.drawable.ico03);
                            break;
                        case "ico04":
                            imageView.setImageResource(R.drawable.ico04);
                            break;
                        case "ico05":
                            imageView.setImageResource(R.drawable.ico05);
                            break;
                        case "ico06":
                            imageView.setImageResource(R.drawable.ico06);
                            break;
                        case "ico07":
                            imageView.setImageResource(R.drawable.ico07);
                            break;
                        case "ico08":
                            imageView.setImageResource(R.drawable.ico08);
                            break;
                        case "ico09":
                            imageView.setImageResource(R.drawable.ico09);
                            break;
                        case "ico10":
                            imageView.setImageResource(R.drawable.ico10);
                            break;
                        case "ico11":
                            imageView.setImageResource(R.drawable.ico11);
                            break;
                        case "ico12":
                            imageView.setImageResource(R.drawable.ico12);
                            break;
                    }

                    if(values[position].isEquipped()){
                        imageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    }
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


