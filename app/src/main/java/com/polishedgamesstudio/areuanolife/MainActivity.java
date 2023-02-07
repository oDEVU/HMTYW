package com.polishedgamesstudio.areuanolife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity<UsageStatsAdapter> extends AppCompatActivity {

    private String[] packages_list = {
            "com.instagram.android",
            "com.zhiliaoapp.musically",//
            "com.facebook.katana",
            "com.facebook.orca",
            "com.snapchat.android",//
            "com.reddit.frontpage",
            "com.discord",
            "com.vanced.android.youtube",
            "com.google.android.youtube"
    };

    private int global_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                Drawable tempImage = getResources().getDrawable(R.drawable.info_dark);
                ImageButton tempButton = (ImageButton)findViewById(R.id.imageButton);
                tempButton.setImageDrawable(tempImage);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                Drawable tempImage2 = getResources().getDrawable(R.drawable.info_light);
                ImageButton tempButton1 = (ImageButton)findViewById(R.id.imageButton);
                tempButton1.setImageDrawable(tempImage2);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Drawable tempImage3 = getResources().getDrawable(R.drawable.info_dark);
                ImageButton tempButton3 = (ImageButton)findViewById(R.id.imageButton);
                tempButton3.setImageDrawable(tempImage3);
                break;
        }

        if(getUsageStatsPermissionsStatus(getApplicationContext()) == PermissionStatus.DENIED){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Grand permissions");
            alertDialog.setMessage("First you need to grand usage access permission.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Go to settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            startActivity(intent);
                            //dialog.dismiss();

                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Required restart");
                            alertDialog.setMessage("Application needs to be restarted.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Restart",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            restartApplication(MainActivity.this);
                                        }
                                    });
                            alertDialog.show();
                        }
                    });
            alertDialog.show();
        }

        UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService("usagestats");
        String topPackageName;

        long total_time = 0;

        View linearLayout =  findViewById(R.id.info);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            long time = System.currentTimeMillis();
            // We get usage stats for the last 10 seconds
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, time);
            List<UsageStats> statsb = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 86400*1000, time);
            // Sort the stats by the last time used
            if(stats != null) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                Map<String, UsageStats> aggregatedStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(0, time);
                Map<String, UsageStats> aggregatedStatsMapb= mUsageStatsManager.queryAndAggregateUsageStats(time - 86400*1000, time);
                for (int i = 0; i < packages_list.length; i++) {

                    String package_name = packages_list[i];

                    UsageStats usageStats = aggregatedStatsMap.get(package_name);
                    UsageStats usageStatsb = aggregatedStatsMapb.get(package_name);
                    //mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);

                    if(aggregatedStatsMap.containsKey(package_name)) {
                        Space space = new Space(this);
                        space.setId(global_id);
                        space.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 86));

                        ((LinearLayout) linearLayout).addView(space);
                        global_id++;

                        ImageView icon = new ImageView(this);
                        icon.setId(global_id);
                        switch (package_name) {
                            case "com.google.android.youtube":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.yt_icon));
                                break;
                            case "com.vanced.android.youtube":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.yt_icon));
                                break;
                            case "com.instagram.android":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ig_icon));
                                break;
                            case "com.facebook.katana":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fb_icon));
                                break;
                            case "com.reddit.frontpage":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.r_icon));
                                break;
                            case "com.facebook.orca":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.msg_icon));
                                break;
                            case "com.discord":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dc_icon));
                                break;
                            case "com.zhiliaoapp.musically":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tt_icon));
                                break;
                            case "com.snapchat.android":
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sc_icon));
                                break;
                            default:
                                icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground));
                                break;
                        }
                        icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 186));

                        ((LinearLayout) linearLayout).addView(icon);
                        global_id++;

                        TextView valueTV = new TextView(this);
                        switch (package_name) {
                            case "com.google.android.youtube":
                                valueTV.setText("Youtube:");
                                break;
                            case "com.vanced.android.youtube":
                                valueTV.setText("Youtube [Vanced]:");
                                break;
                            case "com.instagram.android":
                                valueTV.setText("Instagram:");
                                break;
                            case "com.facebook.katana":
                                valueTV.setText("Facebook:");
                                break;
                            case "com.reddit.frontpage":
                                valueTV.setText("Reddit:");
                                break;
                            case "com.facebook.orca":
                                valueTV.setText("Messenger:");
                                break;
                            case "com.discord":
                                valueTV.setText("Discord:");
                                break;
                            case "com.zhiliaoapp.musically":
                                valueTV.setText("TikTok:");
                                break;
                            case "com.snapchat.android":
                                valueTV.setText("Snapchat:");
                                break;
                            default:
                                valueTV.setText(package_name);
                                break;
                        }
                        valueTV.setId(global_id);
                        valueTV.setGravity(Gravity.CENTER);
                        valueTV.setTextSize(24);
                        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        ((LinearLayout) linearLayout).addView(valueTV);
                        global_id++;

                        Space space2 = new Space(this);
                        space2.setId(global_id);
                        space2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 24));

                        ((LinearLayout) linearLayout).addView(space2);
                        global_id++;

                        TextView last = new TextView(this);
                        last.setId(global_id);
                        last.setText("Last time used: " + DateUtils.formatSameDayTime(usageStats.getLastTimeUsed(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString());
                        if(usageStats.getLastTimeUsed() == 0){
                            last.setText("Last time used: Never");
                        }
                        //last.setText("Discord:");
                        last.setGravity(Gravity.LEFT);
                        last.setTextSize(24);
                        last.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        ((LinearLayout) linearLayout).addView(last);
                        global_id++;

                        TextView last2 = new TextView(this);
                        last2.setId(global_id);
                        last2.setText("Time on screen: " + DateUtils.formatElapsedTime(usageStats.getTotalTimeInForeground() / 1000).toString());

                        //last2.setText("Discord:");
                        last2.setGravity(Gravity.LEFT);
                        last2.setTextSize(24);
                        last2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        ((LinearLayout) linearLayout).addView(last2);
                        global_id++;

                        TextView last3 = new TextView(this);
                        last3.setId(global_id);
                        last3.setText("In last 24 hours: " + DateUtils.formatElapsedTime(usageStatsb.getTotalTimeInForeground() / 1000).toString());
                        if(usageStatsb.getTotalTimeInForeground() / 1000 > (7200*1.5)) {
                            last3.setTextColor(Color.parseColor("#FF0000"));
                        }else if(usageStatsb.getTotalTimeInForeground() / 1000 < 3600){
                            last3.setTextColor(Color.parseColor("#00FF00"));
                        }else{
                            last3.setTextColor(Color.parseColor("#FFFF00"));
                        }

                        total_time += usageStatsb.getTotalTimeInForeground() / 1000;

                        //last2.setText("Discord:");
                        last3.setGravity(Gravity.LEFT);
                        last3.setTextSize(24);
                        last3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        ((LinearLayout) linearLayout).addView(last3);
                        global_id++;
                    }else {

                    }
                }
            }
        }

        Space space = new Space(this);
        space.setId(global_id);
        space.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 112));

        ((LinearLayout) linearLayout).addView(space);
        global_id++;

        TextView valueTV = new TextView(this);
        valueTV.setText("Total on all:");
        valueTV.setId(global_id);
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextSize(24);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) linearLayout).addView(valueTV);
        global_id++;

        TextView total_text = new TextView(this);
        total_text.setId(global_id);
        total_text.setText("Time on screen in last 24h: " + DateUtils.formatElapsedTime(total_time).toString());

        if(total_time > (14400*1.5)) {
            total_text.setTextColor(Color.parseColor("#FF0000"));
        }else if(total_time < 7200){
            total_text.setTextColor(Color.parseColor("#00FF00"));
        }else{
            total_text.setTextColor(Color.parseColor("#FFFF00"));
        }

        //last2.setText("Discord:");
        total_text.setGravity(Gravity.LEFT);
        total_text.setTextSize(24);
        total_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) linearLayout).addView(total_text);
        global_id++;

    }

    public static PermissionStatus getUsageStatsPermissionsStatus(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return PermissionStatus.CANNOT_BE_GRANTED;
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        final int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_DEFAULT ?
                (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED)
                : (mode == AppOpsManager.MODE_ALLOWED);
        return granted ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
    }

    public enum PermissionStatus {
        GRANTED, DENIED, CANNOT_BE_GRANTED
    }

    public void restartApplication(final @NonNull Activity activity) {
        // Systems at 29/Q and later don't allow relaunch, but System.exit(0) on
        // all supported systems will relaunch ... but by killing the process, then
        // restarting the process with the back stack intact. We must make sure that
        // the launch activity is the only thing in the back stack before exiting.
        final PackageManager pm = activity.getPackageManager();
        final Intent intent = pm.getLaunchIntentForPackage(activity.getPackageName());
        activity.finishAffinity(); // Finishes all activities.
        activity.startActivity(intent);    // Start the launch activity
        System.exit(0);    // System finishes and automatically relaunches us.
    }

    public void onInfoButtonClick(View v){
        Intent myIntent = new Intent(MainActivity.this, InfoActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}