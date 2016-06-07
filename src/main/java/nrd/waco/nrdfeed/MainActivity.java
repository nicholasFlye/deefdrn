package nrd.waco.nrdfeed;

import nrd.waco.nrdfeedui.ZoomOutPageTransformer;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Debug;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/***
 * MainActivity
 * Purpose:
 */
public class MainActivity extends FragmentActivity {

    private JSONArray nrdfeedposts;
    private NrdPostsFeed nrdPostsFeed;

    private static DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ExpandListAdapter mDrawerListAdapter;
    private ArrayList<NrdListGroup> nrdListGroupArrayList;

    private ViewPager mPager;
    private int NUM_PAGES = 7;
    private TabLayout tabLayout;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Set main content view
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Activate primary UI with correct dimensions
        activateUI();
        //Get NrdFeed Posts
        nrdPostsFeed = new NrdPostsFeed();
        try {
            nrdfeedposts = nrdPostsFeed.getJSONposts();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Setup ViewPager for primary content viewing
        mPager = (ViewPager) findViewById(R.id.postspager);
        mPagerAdapter = new ScreenSlidePagerAdapter();
        //getSupportFragmentManager() this goes in the adapter constructor for fragment view
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setOffscreenPageLimit(9);
        setupTablayout();

        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);

        String memMessage = String.format("App Memory: Pss=%.2f MB\nPrivate=%.2f MB\nShared=%.2f MB",
                memoryInfo.getTotalPss() / 1024.0,
                memoryInfo.getTotalPrivateDirty() / 1024.0,
                memoryInfo.getTotalSharedDirty() / 1024.0);

        Toast.makeText(this,memMessage, Toast.LENGTH_LONG).show();
        Log.i("log_tag", memMessage);

    }

    /***
     * ScreenSlidePagerAdapter
     * Purpose:
     */
    public class ScreenSlidePagerAdapter extends PagerAdapter {

        //private NrdPost nrdPost;
        private ImageView postImage;
        private TextView title;
        private WebView webView;
        private String type = "";

        public ScreenSlidePagerAdapter() {
            super();
        }

        @Override
        public int getCount() {
            //Log.d("PAGE", String.valueOf(mPager.getCurrentItem()));
            return NUM_PAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Log.d("PAGE", String.valueOf(position));

            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View page = null;

            try {

                type = "video";

                if(type.equals("video")) {
                    page = inflater.inflate(R.layout.post_fragment_video, null);
                    title = (TextView) findViewById(R.id.postTitleVideo);
                    webView = (WebView)findViewById(R.id.webView);
                }
                else{
                    page = inflater.inflate(R.layout.post_fragment_image, null);
                    title = (TextView) findViewById(R.id.postTitleImage);
                    postImage = (ImageView)findViewById(R.id.postImage);
                }

                NrdFeedPostTask nrdTask = new NrdFeedPostTask(position, postImage, webView, title, nrdfeedposts);
                nrdTask.execute();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            container.addView(page, 0);

            return page;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view){
            container.removeView((View) view);
        }


    }

    /***
     * setupTablayout()
     * Purpose:
     * Return: void
     */
    private void setupTablayout() {

        //Initialize string for post type
        String type = "";

        //Set tabLayout preferences
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.isHorizontalScrollBarEnabled();
        tabLayout.clearFocus();
        tabLayout.setupWithViewPager(mPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.transparent));

        //For each tab, determine post type then apply view with custom background to the tab
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
            try {
                 type = nrdfeedposts.getJSONObject(i).getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (type){
                case "article":
                    view1.findViewById(R.id.postIcons).setBackgroundResource(R.drawable.app_icon_article);
                    break;
                case "fight":
                    view1.findViewById(R.id.postIcons).setBackgroundResource(R.drawable.app_icon_fight);
                    break;
                case "video":
                    view1.findViewById(R.id.postIcons).setBackgroundResource(R.drawable.app_icon_cast);
                    break;
                case "meme":
                    view1.findViewById(R.id.postIcons).setBackgroundResource(R.drawable.app_icon_alert);
                    break;
                default:
                    view1.findViewById(R.id.postIcons).setBackgroundResource(R.drawable.app_icon_blank);
                    break;
            };
            tabLayout.getTabAt(i).setCustomView(view1);
        }

    }

    /***
     * SetStandardGroups()
     * Purpose:
     * Return:
     */
    public ArrayList<NrdListGroup> SetStandardGroups() {

        //Create new lists for NavDrawer
        ArrayList<ExpandableNrdList> subList = new ArrayList<ExpandableNrdList>();
        ArrayList<NrdListGroup> headerList = new ArrayList<NrdListGroup>();

        //Make list headers
        NrdListGroup nav = new NrdListGroup();
        nav.setName("Navigation");
        NrdListGroup gurus = new NrdListGroup();
        gurus.setName("Gurus");

        //Get nrdLists resources
        String[] navList = getResources().getStringArray(R.array.navigation);
        String[] guruList = getResources().getStringArray(R.array.gurus);

        //Populate Navigation navdrawer_header's sublist
        for(int i = 0; i < navList.length; i++){
            ExpandableNrdList child = new ExpandableNrdList();
            child.setName(navList[i]);
            child.setTag(null);
            subList.add(child);
        }
        nav.setItems(subList);

        //Reset temporary sublist
        subList = new ArrayList<ExpandableNrdList>();

        //Populate Guru sublist
        for(int i = 0; i < guruList.length; i++){
            ExpandableNrdList child = new ExpandableNrdList();
            child.setName(guruList[i]);
            child.setTag(null);
            subList.add(child);
        }
        gurus.setItems(subList);

        //Add sublists to main arrayList & return to expandableListView
        headerList.add(nav);
        headerList.add(gurus);

        return headerList;
    }

    /***
     * activateUI()
     * Purpose:
     * Return:
     */
    public void activateUI(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Make viewport fullscreen
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ImageView top = (ImageView) findViewById(R.id.top);
                ImageView left = (ImageView) findViewById(R.id.left);
                ImageView right = (ImageView) findViewById(R.id.right);
                ImageView bottom = (ImageView) findViewById(R.id.bottom);

                //Fit all buttons to proper dimensions
                top.setScaleType(ImageView.ScaleType.FIT_XY);
                left.setScaleType(ImageView.ScaleType.FIT_XY);
                right.setScaleType(ImageView.ScaleType.FIT_XY);
                bottom.setScaleType(ImageView.ScaleType.FIT_XY);

                //Highlight for icons tab is brought to the front
                ImageView highlight = (ImageView) findViewById(R.id.highlight);
                highlight.bringToFront();

                //Setup Navigation Drawer
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
                nrdListGroupArrayList = SetStandardGroups();
                mDrawerListAdapter = new ExpandListAdapter(MainActivity.this, nrdListGroupArrayList);
                mDrawerList.setAdapter(mDrawerListAdapter);

                //Append Header View to Navigation Drawer
                LayoutInflater inflater = getLayoutInflater();
                View listHeaderView = inflater.inflate(R.layout.navdrawer_header, mDrawerList, false);
                mDrawerList.addHeaderView(listHeaderView);

                //Set Navigation Drawer button listener
                ImageView navbtn = (ImageView) findViewById(R.id.navBtn);
                navbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrawerLayout.openDrawer(findViewById(R.id.left_drawer));
                    }
                });
            }
        });
    }

}
