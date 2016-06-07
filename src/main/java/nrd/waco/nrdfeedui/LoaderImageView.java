package nrd.waco.nrdfeedui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by XxFLY on 4/13/2016.
 */
public class LoaderImageView extends RelativeLayout {
    private Context     context;
    private ProgressBar progressBar;
    private ImageView   imageView;

    public LoaderImageView(final Context context) {
        super(context);
        instantiate(context);
    }

    public LoaderImageView(final Context context, final AttributeSet attrSet) {
        super(context, attrSet);
        instantiate(context, attrSet);
    }

    private void instantiate(final Context _context) {
        context = _context;
        imageView = new ImageView(context);
        progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);

        addView(progressBar);
        addView(imageView);

        this.setGravity(Gravity.CENTER);
    }

    private void instantiate(final Context _context, AttributeSet attrSet) {
        context = _context;
        imageView = new ImageView(context, attrSet);
        progressBar = new ProgressBar(context, attrSet);
        progressBar.setIndeterminate(true);

        addView(progressBar);
        addView(imageView);

        this.setGravity(Gravity.CENTER);
    }

}