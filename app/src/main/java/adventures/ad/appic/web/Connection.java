package adventures.ad.appic.web;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import adventures.ad.appic.game.Location;
import adventures.ad.appic.main.custom.MessageBox;

/**
 * Created by 11305205 on 19/01/2015.
 */
public class Connection {

    String localURL = "http://172.20.10.5:80/appic/";
    String testURL = "http://169.254.25.143:80/appic/";
    String serverURL = "http://85.151.202.128:550/appic/";
    String url;
    String input;
    JSONArray locations = null;
    ArrayList<Location> locationList = new ArrayList<>();

    Context c;

    public Connection(Context c){
        this.c = c;
        //input = readService();

    }

    public String getUserInventory(String user){
        return "001;002;003;004;005;005;005;005";
    }

    public Boolean confirmUser(String user, String pass){

        url = localURL + "Users" + user;

        try {
            JSONObject json = new JSONObject(loginService());
            return true;
        } catch (Exception e) {
            Log.e("test",Log.getStackTraceString(e));
            return false;
        }
    }

    public String getCharacter(String user){

        String url = localURL + user;

        try {
            JSONObject json = new JSONObject(loginService());
            return json.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public Location getLocation(LatLng crd){
        for(int i = 0; i < locationList.size(); i++) {
           if(Double.parseDouble(locationList.get(i).getCoordx()) == crd.longitude && Double.parseDouble(locationList.get(i).getCoordy()) == crd.latitude){
                return locationList.get(i);
            }
        }
        return null;
    }

    public ArrayList<Location> getLocationlist(){
        return locationList;
    }

    public void getLocations(){
        url = serverURL + "get/Locations";
        //url = localURL + "Locations/object/Locations";
        try {

            Log.i("test0", "test0");
            String read = readService();
            Log.i("test", "test");
            if(read != null)
            {
                JSONObject json = new JSONObject(read);
                locations = json.getJSONArray("locations");
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
            e.printStackTrace();
        }


        try {

        } catch (Exception e) {
        }
    }

    private String getTestText(){
        try {
            JSONArray array = new JSONArray();
            JSONObject json = new JSONObject(input);
            return json.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String loginService() throws Exception{
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        HttpResponse response = client.execute(httpPost);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } else {
        }

        /*
        try {
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
            }
        } catch (ClientProtocolException e) {
            Log.e("test1", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e("test2", e.getLocalizedMessage());
        } catch (Exception e){
            Log.e("test3", e.getLocalizedMessage());
        }*/
        return builder.toString();
    }

    public String readService() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        Log.i("test4", "test4");
        try {
            Log.i("test5", "test5");
            HttpResponse response = client.execute(httpGet);
            Log.i("test6", "test6");
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            Log.i("test1", "test1");
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                Log.i("test2", "test2");
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    Log.i("test3", "test3");
                }
                Log.i("test4", "test4");
            } else {
               // Log.e(ParseJSON.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

  /*  public void writeService() {

        HttpClient client = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        try {
            Location loc = new Location();
            loc.setId(1);
            loc.setAddress("test");
            loc.setCoordx("5.34826098");
            loc.setCoordy("50.93813318");
            loc.setName("cdskjfql");
            loc.setEventId(1);
            loc.setPhoto("dbksjf");
            httpPut.setEntity((HttpEntity) loc);
            HttpResponse response= client.execute(httpPut);
        }
        catch (  UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    }*/

}
