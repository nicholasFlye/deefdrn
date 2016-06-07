package nrd.waco.nrdfeed;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.widget.ImageView;
import android.view.View;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.json.*;
import java.io.IOException;

/**
 * Created by XxFLY on 2/27/2016.
 */
public class NrdPostsFeed {

    private static Context myContext;
    public NrdPostsFeed(){
    }
    public NrdPostsFeed(Context context){
        myContext = context;
    }

    public JSONArray getJSONposts() throws JSONException {
        return new JSONArray(getPostData());
    }

    public static String getPostData(){

        StringBuilder content = new StringBuilder();
        try{
            URL connectedURL = new URL("http://www.nrdfeed.com/app.php");
            URLConnection urlConnection = connectedURL.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            String line;

            while((line = bufferedReader.readLine()) != null){

                content.append(line);

            }
            bufferedReader.close();
        }
        catch(Exception e){
            e.printStackTrace();;
        }

        //Log.d("POSTS", content.toString());

        return content.toString();
    }

    public static String getIMGurl(int position) throws IOException, JSONException{
        JSONArray postInfoJSON;
        String imgUrl = "";
        try {
            postInfoJSON = new JSONArray(getPostData());
            if(postInfoJSON.getJSONObject(position).get("type") != "video")
                imgUrl = postInfoJSON.getJSONObject(position).getString("imgURL");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return imgUrl;
    }

    public static void displayPostInfo() throws IOException, JSONException{

        JSONArray postInfoJSON = new JSONArray(getPostData());
        //Log.d("Posts", getPostData());
        for(int i = 0; i < 30; i++) {
            Log.d("MASTER", postInfoJSON.getJSONObject(i).getString("masterID"));
            Log.d("TITLE", postInfoJSON.getJSONObject(i).getString("title"));
            Log.d("TYPE", postInfoJSON.getJSONObject(i).getString("type"));
            Log.d("USER", postInfoJSON.getJSONObject(i).getString("user"));
            Log.d("IMG", postInfoJSON.getJSONObject(i).getString("imgURL"));
            Log.d("LIKES", postInfoJSON.getJSONObject(i).getString("likes"));
        }
    }

    public static void loginCred() throws IOException, JSONException {



    }

}
