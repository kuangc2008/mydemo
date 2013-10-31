package com.example.demo.demo1.mergedCursor;

import java.util.zip.Inflater;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MergerCursorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new MyFragment());
            ft.commit();
        }
    }

    public static class MyFragment extends ListFragment implements LoaderCallbacks<Cursor>{
        private Context mContext = null;
        private static final String[] coloums = new String[] {
            "_id", Phone.NUMBER, ContactsContract.Contacts.DISPLAY_NAME
        };
        private MyAdapter myAdapter = null;
        
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mContext = getActivity();
            getLoaderManager().initLoader(0, null, this);
            myAdapter = new MyAdapter(mContext);
            setListAdapter(myAdapter);
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(mContext,
                    ContactsContract.Data.CONTENT_URI,
                    coloums, 
                    ContactsContract.Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE +"'", 
                    null, 
                    null);
        }
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            myAdapter.swapCursor(data);
            Log.v("kc", "data.count-->" + data.getCount());
        }
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            myAdapter.swapCursor(null);
        }
      
        public class MyAdapter extends CursorAdapter {

            public MyAdapter(Context context) {
                super(context, null, 0);
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2, null, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                TextView tv2 = (TextView) view.findViewById(android.R.id.text2);
                tv1.setText(cursor.getString(1));
                tv2.setText(cursor.getString(2));
            }
            
        }

    }
}
