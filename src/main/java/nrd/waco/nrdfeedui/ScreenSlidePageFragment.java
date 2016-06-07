package nrd.waco.nrdfeedui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nrd.waco.nrdfeed.R;

/**
 * Created by XxFLY on 3/1/2016.
 */
public class ScreenSlidePageFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.post_fragment_video, container, false);

        return rootView;
    }
}
