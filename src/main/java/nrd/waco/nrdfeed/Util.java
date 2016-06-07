package nrd.waco.nrdfeed;

import android.util.Log;

/**
 * Created by XxFLY on 3/8/2016.
 */
public class Util {
    public static String logTag = "NrdFeed";
    public static boolean debugging = true;

    public static void Log(String s){
        if(debugging){
            Log.i(logTag, s);
        }
    }
}
