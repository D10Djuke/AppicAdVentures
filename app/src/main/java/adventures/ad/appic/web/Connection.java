package adventures.ad.appic.web;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 11305205 on 19/01/2015.
 */
public class Connection {

    String localURL = "mickelmac.local/appic/connect.php?action=get&object=user";
    String serverURL = "http://85.151.202.128:550/appic/connect.php?action=get&object=user";
    String url;
    String input;

    public Connection(){

        //input = readService();

    }

    public Boolean confirmUser(String user, String pass){


        String url = "http://85.151.202.128:550/appic/connect.php?object=user&value=" + user;
        //TODO user & password confirm check

        return true;
    }

    public String getUser(String user){

        String url = "http://85.151.202.128:550/appic/connect.php?object=user&value=" + user;

        try {
            JSONObject json = new JSONObject(readService());
            return json.toString();
        } catch (Exception e) {
            Log.e("test", e.toString());
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

    public String readService() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
