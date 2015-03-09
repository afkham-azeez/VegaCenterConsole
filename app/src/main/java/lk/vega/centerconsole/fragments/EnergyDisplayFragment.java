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
package lk.vega.centerconsole.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import lk.vega.centerconsole.R;

public class EnergyDisplayFragment extends Fragment implements TabHost.OnTabChangeListener {
    private double graph2LastXValue = 5d;

    private TabHost mTabHost;
    private int mCurrentTab;

    private View rootView;

    private static final String TAG = "EnergyDisplayFragment";
    public static final String TAB_CONSUMPTION = "consumption";
    public static final String TAB_TRIP = "trip";

    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private Runnable mTimer1;
    private Runnable mTimer2;
    private final Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_energy_display, container, false);

        mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
        setupTabs();

        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        mSeries1 = new LineGraphSeries<DataPoint>(generateData());
        graph.addSeries(mSeries1);

        GraphView graph2 = (GraphView) rootView.findViewById(R.id.graph2);
        mSeries2 = new LineGraphSeries<DataPoint>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(40);

        return rootView;
    }

    private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_CONSUMPTION, R.string.consumption, R.id.consumptionTab));
        mTabHost.addTab(newTab(TAB_TRIP, R.string.trip, R.id.tripTab));
    }

    private TabHost.TabSpec newTab(String tag, int labelId, int tabContentId) {
        Log.d(TAG, "buildTab(): tag=" + tag);

//        View indicator = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_energy_tab,
//                (ViewGroup) rootView.findViewById(android.R.id.tabs), false);
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(tag);
//        tabSpec.setContent(new Intent().setClass(rootView.getContext(), AndroidActivity.class));
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        updateTab(TAB_CONSUMPTION, R.id.consumptionTab);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //TODO: chech whether this is needed - Azeez
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged(): tabId=" + tabId);
        if (TAB_CONSUMPTION.equals(tabId)) {
            updateTab(tabId, R.id.consumptionTab);
            mCurrentTab = 0;
        } else if (TAB_TRIP.equals(tabId)) {
            updateTab(tabId, R.id.tripTab);
            mCurrentTab = 1;
        }
    }

    private void updateTab(String tabId, int placeholder) {
        /*FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(tabId) == null) {
            fm.beginTransaction()
                    .replace(placeholder, new EnergyFragment(), tabId)
                    .commit();
        }*/
    }


    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                mSeries1.resetData(generateData());
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer1, 200);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries2.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 1000);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

    private DataPoint[] generateData() {
        int count = 50;
        DataPoint[] values = new DataPoint[count];
        for (int i = 0; i < count; i++) {
            double x = i;
            double f = mRand.nextDouble() * 0.15 + 0.3;
            double y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }
}
