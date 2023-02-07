package com.polishedgamesstudio.areuanolife;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView linkTextView = findViewById(R.id.a);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                Drawable tempImage = getResources().getDrawable(R.drawable.back_dark);
                ImageButton tempButton = (ImageButton)findViewById(R.id.imageButton);
                tempButton.setImageDrawable(tempImage);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                Drawable tempImage2 = getResources().getDrawable(R.drawable.back_light);
                ImageButton tempButton1 = (ImageButton)findViewById(R.id.imageButton);
                tempButton1.setImageDrawable(tempImage2);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Drawable tempImage3 = getResources().getDrawable(R.drawable.back_dark);
                ImageButton tempButton3 = (ImageButton)findViewById(R.id.imageButton);
                tempButton3.setImageDrawable(tempImage3);
                break;
        }

        TextView perm_text = (TextView) findViewById(R.id.perm);

        if(getUsageStatsPermissionsStatus(getApplicationContext()) == MainActivity.PermissionStatus.DENIED){
            perm_text.setText("Usage Permision: Denied");
        }else{
            perm_text.setText("Usage Permision: Granted");
        }

    }

    public static MainActivity.PermissionStatus getUsageStatsPermissionsStatus(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return MainActivity.PermissionStatus.CANNOT_BE_GRANTED;
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        final int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_DEFAULT ?
                (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED)
                : (mode == AppOpsManager.MODE_ALLOWED);
        return granted ? MainActivity.PermissionStatus.GRANTED : MainActivity.PermissionStatus.DENIED;
    }

    public enum PermissionStatus {
        GRANTED, DENIED, CANNOT_BE_GRANTED
    }

    public void onInfoButtonClick(View v){
        Intent myIntent = new Intent(InfoActivity.this, MainActivity.class);
        InfoActivity.this.startActivity(myIntent);
    }
}