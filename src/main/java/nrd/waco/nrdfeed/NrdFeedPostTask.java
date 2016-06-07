package nrd.waco.nrdfeed;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by XxFLY on 3/8/2016.
 */
public class NrdFeedPostTask extends AsyncTask<Integer, Void, JSONObject> {

    private TextView title;
    private ImageView postImage;
    private int position;
    private JSONArray nrdJSON;
    private WebView webView;

    public NrdFeedPostTask(int position, ImageView postImage, WebView webView, TextView title, JSONArray nrdJSON) throws JSONException {
        super();
        this.position = position;
        this.nrdJSON = nrdJSON;
        this.title = title;
        this.postImage = postImage;
        this.webView = webView;
    }

    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public String createHTML() {

        //can only use the embedded youtube link ".../embed/videoid
        String html = "<html> <body style=\"margin: 0; padding: 0\">" +
                "<iframe class=\"youtube.com/\"; style=\"" +
                "border: 0; width: 100%; height: 100%; padding:0px; margin:0px\" " +
                "id=\"ytplayer\" type=\"text/html\" " +
                "src=\"" + "https://www.youtube.com/embed/E2FlvoDpHno"
                + "?fs=1\" allowfullscreen>\n"
                + "</iframe></body></html>";
        /*try {
            html = "<html> <body style=\"margin: 0; padding: 0\">" +
                    "<iframe class=\"youtube.com/\"; style=\"" +
                    "border: 0; width: 100%; height: 100%; padding:0px; margin:0px\" " +
                    "id=\"ytplayer\" type=\"text/html\" " +
                    "src=\"" + "https://www.youtube.com/watch?v=LMLeZaSeQEA"
                + "?fs=1\" allowfullscreen>\n"
                + "</iframe></body></html>";
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return html;
    }

    @Override
    protected JSONObject doInBackground(Integer... params) {
        try {
            if(!(position >= nrdJSON.length()) && position > 0) {
                return nrdJSON.getJSONObject(this.position);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result){
        super.onPostExecute(result);

        if(postImage != null && title != null){
            try {
                if(!(position >= nrdJSON.length()) && position > 0) {
                    postImage.setImageDrawable(LoadImageFromWebOperations(result.getString("imgURL")));
                    title.setText(result.getString("title").replaceAll("#Q#", "\""));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(webView != null && title != null){
            try {
                if(!(position >= nrdJSON.length()) && position > 0) {
                    title.setText(result.getString("title").replaceAll("#Q#", "\""));
                    String html = createHTML();
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
                    webView.getSettings().setBuiltInZoomControls(false);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    final String encoding = "UTF-8";
                    final String mimeType = "text/html";
                    webView.loadDataWithBaseURL("", html, mimeType, encoding, "");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
