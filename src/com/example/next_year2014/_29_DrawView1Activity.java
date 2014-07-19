package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/7/18.
 */
public class _29_DrawView1Activity extends Activity {
    DrawLayout mDrawlayot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_29_draw_view);
        mDrawlayot = (DrawLayout) findViewById(R.id.drawLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"add");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case 0:
                mDrawlayot.setDrawCatchView();
                break;

        }
        return super.onMenuItemSelected(featureId, item);
    }
}
