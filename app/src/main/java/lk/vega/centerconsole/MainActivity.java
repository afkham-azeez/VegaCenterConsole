/*
 * (C) Copyright 2015 CodeGen International (http://codegen.net) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Afkham Azeez (afkham@gmail.com)
 */
package lk.vega.centerconsole;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener, View.OnLongClickListener {
    private List<AppInfo> res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*// Set the apps at the bottom
        LinearLayout extApp = (LinearLayout) findViewById(R.id.extApp);
        getAppList();
        for (int i = 0; i < res.size(); i++) {
            AppInfo app = res.get(i);
            ViewGroup.LayoutParams lp =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageButton appButton = new ImageButton(this);
//            appButton.setImageDrawable(app.icon);
            appButton.setBackground(app.icon);
            appButton.setPadding(0, 0, 0, 0);
            appButton.setOnClickListener(this);
            appButton.setTag(i);
            extApp.addView(appButton, lp);

            Space space = new Space(this);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
            extApp.addView(space, new ViewGroup.LayoutParams(px, ViewGroup.LayoutParams.MATCH_PARENT));
        }*/

        // Bottom
       /* SeekBar seekBar = (SeekBar) findViewById(R.id.acSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView textView = (TextView) findViewById(R.id.acSeekBarText);
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
//                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Temperature: " + progress + "/" + seekBar.getMax());
//                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });*/

        setButtonConfigurations(findViewById(R.id.bluetoothBtn), R.drawable.bluetooth_button_style);
        setButtonConfigurations(findViewById(R.id.hazardBtn), R.drawable.hazard_button_style);
        setButtonConfigurations(findViewById(R.id.settingsBtn), R.drawable.settings_button_style);
        setButtonConfigurations(findViewById(R.id.musicBtn), R.drawable.music_button_style);
        setButtonConfigurations(findViewById(R.id.callBtn), R.drawable.call_button_style);
        setButtonConfigurations(findViewById(R.id.webBtn), R.drawable.web_button_style);
        setButtonConfigurations(findViewById(R.id.mapBtn), R.drawable.map_button_style);
        setButtonConfigurations(findViewById(R.id.voiceBtn), R.drawable.voice_button_style);

    }

    private void setButtonConfigurations(View v, int resId) {
        v.setOnLongClickListener(this);
        v.setBackgroundResource(resId);
        v.setOnClickListener(this);
    }

    public void getAppList() {
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        res = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            PackageInfo p = apps.get(i);
            if ((p.packageName.contains("lk.vega") && !p.packageName.equals("lk.vega.centerconsole"))
                    || p.packageName.contains("maps") || p.packageName.contains("phone")) {
                System.out.println("+++ package=" + p.packageName);
                AppInfo newInfo = new AppInfo();
                newInfo.appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                newInfo.packageName = p.packageName;
                newInfo.versionName = p.versionName;
                newInfo.versionCode = p.versionCode;
                newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
                res.add(newInfo);
            }
        }
    }

    @Override
    public void onClick(View v) {
        /*int i = (int) v.getTag();
        AppInfo app = res.get(i);
        PackageManager pm = this.getPackageManager();
        Intent it = pm.getLaunchIntentForPackage(app.packageName);
        if (null != it) {
            this.startActivity(it);
        }*/
        Toast.makeText(getApplicationContext(), "Button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO Return to home view
        return false;
    }

    private static class AppInfo {
        String appName = "";
        String packageName = "";
        String versionName = "";
        int versionCode = 0;
        Drawable icon;
    }

    public void clickEnergy(View v) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.container, new EnergyDisplayFragment()).commit();
    }

    public void clickLights(View view) {
    }

    public void clickCamera(View view) {
    }

    public void clickBrakes(View view) {
    }

    public void clickAC(View view) {
    }

    public void clickPlayer(View view) {
    }
}
