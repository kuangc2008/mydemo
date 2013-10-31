
package com.example.demo;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new MainFragment(), "ff");
            ft.commit();
        }
    }
    
    @Override
    public void onBackPressed() {
        MainFragment fragment = (MainFragment) getFragmentManager().findFragmentById(android.R.id.content);
        fragment.pressBack();
    }

    public static class MainFragment extends ListFragment {
        private List<AppInfo> appinfos = null;
        private MainAdapter adapter;
        
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
            appinfos = AllActivityManager.getInstance(getActivity()).getActivityList("");
            adapter = new MainAdapter(getActivity(), android.R.layout.simple_list_item_1, appinfos);
            adapter.setPrefix("");
            setListAdapter(adapter);
        }
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            AppInfo info = adapter.getItem(position);
            String nowPrefix = adapter.getPrefix();
            
            if(info.isLastPrefix(nowPrefix)) {
                
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(info.getActivityInfo().packageName, 
                        info.getActivityInfo().name));
                startActivity(intent);
            } else {
                String nextPre = info.getNextPrefix(nowPrefix);
                refreshList(nextPre);
            }
        }

        public void pressBack() {
            String nowPrefix = adapter.getPrefix();
            if(!TextUtils.isEmpty(nowPrefix)) {
                String prefix = getPrePrefix(nowPrefix);
                refreshList(prefix);
            } else {
                getActivity().finish();
            }
        }

        private String getPrePrefix(String prefix) {
            int lastCharacter = prefix.lastIndexOf('/');
            if(lastCharacter == -1) {
                return "";
            }
            return prefix.substring(0, lastCharacter);
        }
        
        private void refreshList(String prefix) {
            adapter.setPrefix(prefix);
            adapter.setInfos(AllActivityManager.getInstance(getActivity()).getActivityList(prefix));
            adapter.notifyDataSetChanged();
        }

        public class MainAdapter extends BaseAdapter {
            private LayoutInflater inflater = null;
            int layoutRes = 0;
            List<AppInfo> infos = null;
            // the prefix don't contain the current show label. for exsample, 
            // the content is c, and the prefix maybe a/b
            private String prefix = null;
            
            public MainAdapter(Context context, int layoutRes, List<AppInfo> infos) {
                this.layoutRes = layoutRes;
                this.inflater = LayoutInflater.from(context);
                this.infos = infos;
            }
            
            public void setInfos(List<AppInfo> infos) {
                this.infos = infos;
            }

            @Override
            public int getCount() {
                return infos.size();
            }

            @Override
            public AppInfo getItem(int position) {
                return infos.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
            
            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }
            
            public String getPrefix() {
                return prefix;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = null;
                if(convertView == null) {
                    View view = inflater.inflate(layoutRes, parent, false);
                    v = (TextView) view.findViewById(android.R.id.text1);
                } else {
                    v = (TextView) convertView;
                }

                AppInfo appInfo = getItem(position);
                String title = appInfo.getTitle(prefix);
                v.setText(title);
                return v;
            }
        }
    }
}
