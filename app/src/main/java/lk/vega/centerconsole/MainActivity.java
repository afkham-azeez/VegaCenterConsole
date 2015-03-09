/*
 * (C) Copyright ${year} CodeGen International (http://codegen.net) and others.
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
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lk.vega.centerconsole.fragments.EnergyDisplayFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    List<AppInfo> res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the apps at the bottom
        LinearLayout extApp = (LinearLayout) findViewById(R.id.extApp);
        getAppList();
        for (int i = 0; i < res.size(); i++) {
            AppInfo app = res.get(i);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ImageButton myButton = new ImageButton(this);
            myButton.setImageDrawable(app.icon);
            myButton.setOnClickListener(this);
            myButton.setTag(i);
            extApp.addView(myButton, lp);
        }
    }

    public void getAppList() {
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        res = new ArrayList<AppInfo>();
        for (int i = 0; i < apps.size(); i++) {
            PackageInfo p = apps.get(i);
            if ((p.packageName.contains("lk.vega") && !p.packageName.equals("lk.vega.centerconsole"))
                    || p.packageName.contains("maps") || p.packageName.contains("phone")) {
                System.out.println("+++ package=" + p.packageName);
                AppInfo newInfo = new AppInfo();
                newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
                newInfo.pname = p.packageName;
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
        Intent it = pm.getLaunchIntentForPackage(app.pname);

        if (null != it)
            this.startActivity(it);*/

    }


    class AppInfo {
        String appname = "";
        String pname = "";
        String versionName = "";
        int versionCode = 0;
        Drawable icon;

    }

    public void clickEnergy(View v) {
//        LinearLayout i = (LinearLayout) findViewById(R.id.lights);
//        i.setVisibility(View.VISIBLE);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new EnergyDisplayFragment()).commit();
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
