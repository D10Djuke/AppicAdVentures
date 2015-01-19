package adventures.ad.appic.web;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by 11305205 on 19/01/2015.
 */
public class Authenticator {

    private String user = "";
    private String password = "";

    public Authenticator(){
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            client.getCredentialsProvider().setCredentials(new AuthScope("local", 433), new UsernamePasswordCredentials(user, password));
            HttpGet httpGet = new HttpGet("https://localhost/protected");

            HttpResponse resp = client.execute(httpGet);
            HttpEntity entity = resp.getEntity();

            if (entity != null) {

            }
        }catch (Exception e){

        }finally {
            client.getConnectionManager().shutdown();
        }
    }



}
