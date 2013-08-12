
package com.example.demo;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new MainFragment(), null);
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static class MainFragment extends ListFragment {
        private PackageManager mPm;
        public MainFragment() {
            mPm = getActivity().getPackageManager();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Log.v(TAG, "onCreateView");
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        
        @Override
        public void onAttach(Activity activity) {
            Log.v(TAG, "OnAttach");
            super.onAttach(activity);
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Log.v(TAG, "OnActivityCreated");
            super.onActivityCreated(savedInstanceState);
        }

    }
}
