package adventures.ad.appic.web;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import adventures.ad.appic.game.Item;
import adventures.ad.appic.game.Location;


import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;

/**
 * Created by 11305205 on 19/01/2015.
 */
public class Connection {

    //String localURL = "http://172.20.10.5:80/appic/";
    String serverURL = "http://85.151.202.128:550/appic/";
    String url;
    String input;
    JSONArray locations = null;
    JSONObject user = null;
    JSONObject player = null;

    ArrayList<Location> locationList = new ArrayList<>();
    Player mPlayer;

    Context c;

    public Connection(Context c){
        this.c = c;
        //serverURL = localURL;
    }

    public String getUserInventory(String user){
        return "001;002;003;004;005;005;005;005";
    }

    public Location getLocation(LatLng crd){
        for(int i = 0; i < locationList.size(); i++) {
            Log.d("x : ", "" +crd.longitude);
            Log.d("y : ", "" +crd.latitude);
            Log.d("xloc : ", "" +Double.parseDouble(locationList.get(i).getCoordx()));
            Log.d("yloc : ", "" +Double.parseDouble(locationList.get(i).getCoordy()));
           if(Double.parseDouble(locationList.get(i).getCoordx()) == crd.longitude && Double.parseDouble(locationList.get(i).getCoordy()) == crd.latitude){
                return locationList.get(i);
            }
        }
        return null;
    }

    public ArrayList<Location> getLocationlist(){
        return locationList;
    }

    public int confirmUser(String userName){

        int trueUser = -1;

        Log.d("test: " , "looooool");
        url = serverURL + "get/Users";
        try{
            Log.d("test2: " , "hahahahha");
            String read = readServiceWithParam("goodleID", userName);
            Log.d("test3: " , "tralala");
            if(read != null){
                user = new JSONObject(read);
                trueUser = user.getInt("userId");
            }
            Log.d("test4: " , "pfffffffffffffffff");
        }catch (Exception e) {
  //          new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }

        return trueUser;
    }

    public ArrayList<Item> getInventory(int userId){

        ArrayList<Item> inventory = new ArrayList<>();

        url = serverURL + "get/Inventory";
        try{
            //TODO write body





        }catch (Exception e){

        }

        return inventory;

    }

    public Player getPlayer(int userId){
        url = serverURL + "get/Characters";

        try{
            String read = readServiceWithParam("userId", Integer.toString(userId));

            if(read != null){
                player = new JSONObject(read);

                mPlayer = new Player();
                mPlayer.setCharacterName(player.getString("name"));
                mPlayer.setLvl(player.getInt("level"));
                mPlayer.setCurrExp(player.getInt("exp"));
            }
        }catch (Exception e){
            //new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }

        return mPlayer;
    }

    public void create(JSONObject obj, String flag){
        switch (flag)
        {
            case "USER":
                url = serverURL + "add/Users";
                insertService(obj);
                break;
            case "CHARACTER":
                url = serverURL + "add/Characters";
                insertService(obj);
                break;
        }
    }

    public void getLocations(){
        url = serverURL + "get/Locations";

        try {
            String read = readService();

            if(read != null)
            {

                locations = new JSONArray(read);
                for (int i = 0; i < locations.length(); i++) {
                    JSONObject c = locations.getJSONObject(i);

                    Location loc = new Location();

                    loc.setId(c.getInt("locationId"));
                    loc.setName(c.getString("name"));
                    loc.setCoordx(c.getString("coordx"));
                    loc.setCoordy(c.getString("coordy"));
                    loc.setAddress(c.getString("address"));
                    loc.setPhoto(c.getString("photo"));
                    loc.setEventId(c.getInt("eventId"));

                    locationList.add(loc);

                }
            }
        } catch (Exception e) {
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }
    }

    public String readServiceWithParam(String param, String value) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(param,value));

        HttpGet httpGet = new HttpGet(url + "?" + URLEncodedUtils.format(params, "utf-8"));
        try {

            Log.d("test0: " , "test0");
            HttpResponse response = client.execute(httpGet);
            Log.d("test1: " , "test1");
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            Log.d("test2: " , ""+statusCode);

            if (statusCode == 200) {
                Log.d("test3: " , "test3");
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                Log.d("test4: " , "test4");

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                Log.d("test5: " , "test5");
                content.close();
                entity.consumeContent();

            } else {
                new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        } catch (IOException e) {
            e.printStackTrace();
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }
        return builder.toString();
    }

    public String readService() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {

            Log.d("test0: " , "test0");
            HttpResponse response = client.execute(httpGet);
            Log.d("test1: " , "test1");
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            Log.d("test2: " , ""+statusCode);

            if (statusCode == 200) {
                Log.d("test3: " , "test3");
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                Log.d("test4: " , "test4");

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                Log.d("test5: " , "test5");
                content.close();
                entity.consumeContent();

            } else {
                new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        } catch (IOException e) {
            e.printStackTrace();
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }
        return builder.toString();
    }

    public void updateService(JSONObject o){
        HttpClient client = new DefaultHttpClient();
        HttpPut request = new HttpPut(url);

        try{

            StringEntity s = new StringEntity(o.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");

            request.setEntity(s);
            request.addHeader("accept", "application/json");

            client.execute(request);

        }catch (Exception e){
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }

    }

    public void insertService(JSONObject o){
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        try{
            StringEntity s = new StringEntity(o.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");

            request.setEntity(s);
            request.addHeader("accept", "application/json");

            client.execute(request);

        } catch (Exception e){
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }

    }

    public void deleteService(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(url);

        try{
            delete.addHeader("accept", "application/json");
            httpclient.execute(delete);
        }catch (Exception e){
            new MessageBox(MessageBox.Type.STANDARD_ERROR_BOX, c).popMessage();
        }
    }

}
