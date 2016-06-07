package nrd.waco.nrdfeedui;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nrd.waco.nrdfeed.ExpandableNrdList;
import nrd.waco.nrdfeed.NrdListGroup;
import nrd.waco.nrdfeed.R;

/**
 * Created by XxFLY on 4/1/2016.
 */
public class NrdFeedPopulationTask extends AsyncTask<Integer, Void, Void> {

    private Context context;
    private ImageView top;
    private ImageView left;
    private ImageView right;
    private ImageView bottom;
    private DrawerLayout mDrawerLayout;

    public NrdFeedPopulationTask(ImageView top, ImageView bottom, ImageView left, ImageView right, DrawerLayout mDrawerLayout){
        super();
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.mDrawerLayout = mDrawerLayout;

    }

    @Override
    protected Void doInBackground(Integer... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void j ){
        super.onPostExecute(null);
    }

}
