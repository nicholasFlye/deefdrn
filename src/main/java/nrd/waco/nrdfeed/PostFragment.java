package nrd.waco.nrdfeed;

/**
 * Created by XxFLY on 4/5/2016.
 */
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.InputStream;
import java.net.URL;

public class PostFragment extends Fragment {
    int fragVal;
    private static JSONArray nrdJSON;

    static PostFragment init(int val, JSONArray nrdfeedposts) {
        nrdJSON = nrdfeedposts;
        PostFragment postFrag = new PostFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        postFrag.setArguments(args);
        return postFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.post_fragment_video, container,
                false);
        View title = layoutView.findViewById(R.id.postTitleImage);
        //View postImage = layoutView.findViewById(R.id.postImage);
        if(!(fragVal >= nrdJSON.length())) {
            /*try {
                ((ImageView)postImage).setImageDrawable(LoadImageFromWebOperations(nrdJSON.getJSONObject(fragVal).getString("imgURL")));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            ((TextView) title).setText("THE TITLE");
        }

        return layoutView;
    }
}