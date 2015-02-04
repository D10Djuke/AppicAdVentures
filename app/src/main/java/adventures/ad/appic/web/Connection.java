package adventures.ad.appic.web;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import adventures.ad.appic.main.custom.MessageBox;

/**
 * Created by 11305205 on 19/01/2015.
 */
public class Connection {

    String localURL = "http://10.81.131.205/appic/Users/object/Users/gid/";
    String serverURL = "http://85.151.202.128:550/appic/connect.php?action=get&object=user";
    String url;
    String input;

    Context c;

    public Connection(Context c){
        this.c = c;
        //input = readService();

    }

    public String getUserInventory(String user){
        return "001;002;003;004;005;005;005;005";
    }

    public Boolean confirmUser(String user, String pass){

        url = localURL + user;

        try {
            JSONObject json = new JSONObject(readService());
            return true;
        } catch (Exception e) {
            Log.e("test",Log.getStackTraceString(e));
            return false;
        }
    }

    public String getCharacter(String user){

        String url = localURL + user;

        try {
            JSONObject json = new JSONObject(readService());
            return json.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    private String getTestText(){
        try {
           JSONObject json = new JSONObject(input);
            return json.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String readService() throws Exception{
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
}
