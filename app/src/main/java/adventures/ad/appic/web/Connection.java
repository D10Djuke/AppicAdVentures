package adventures.ad.appic.web;

import android.content.Context;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
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

    String localURL = "mickelmac.local/appic/connect.php?action=get&object=user";
    String serverURL = "http://85.151.202.128:550/appic/connect.php?action=get&object=user";
    String url = "https://bugzilla.mozilla.org/rest/bug?assigned_to=lhenry@mozilla.com";
    String input;

    public Connection(Context context){

        input = readService();

    }

    public String getTestText(){
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
        HttpGet httpGet = new HttpGet(localURL);
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
