package adventures.ad.appic.web;

import android.content.Context;
import android.os.AsyncTask;
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
import java.net.URL;

import adventures.ad.appic.main.custom.MessageBox;

/**
 * Created by 11305205 on 19/01/2015.
 */
public class Connection {

    String localURL = "http://169.254.16.223/appic/Users/object/Users/gid/";
    String serverURL = "http://85.151.202.128:550/appic/connect.php?action=get&object=user";
    String url;
    String input;

    String jsonString = "default";
    JSONObject jSon = null;

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
        new ConnectToServiceTask().execute(url);

        if(jsonString.equals("default")){
            return false;
        }else{
            return true;
        }
    }

    public String getCharacter(String user){

        String url = localURL + user;

        try {
            JSONObject json = new JSONObject();
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

    private class ConnectToServiceTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            try{
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
            }catch (Exception e){
                Log.e("test",Log.getStackTraceString(e));
            }
            jsonString = builder.toString();
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            try {
                jSon = new JSONObject(jsonString);
            } catch (Exception e) {
                Log.e("test",Log.getStackTraceString(e));
            }
        }
    }
}
