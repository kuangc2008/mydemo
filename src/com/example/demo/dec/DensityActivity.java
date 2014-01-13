package com.example.demo.dec;

import com.example.demo.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class DensityActivity extends Activity {

    View view1 = null;
    View view2 = null;
    View view3 = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LayoutInflater li  =  (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        this.setTitle("像素");

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout layout = new LinearLayout(this);
        view1 = addBitmapDrawable(layout, R.drawable.logo120dpi, true);   // 75 * 75
        view2 = addBitmapDrawable(layout, R.drawable.logo160dpi, true);   // 100 * 100
        view3 = addBitmapDrawable(layout, R.drawable.logo240dpi, true);   // 150 * 150
        LinearLayout.LayoutParams lp = (LayoutParams) view3.getLayoutParams();
        lp.leftMargin = 120;
        view3.setLayoutParams(lp);
        addLabelToRoot(root, "Prescaled bitmap in drawable");
        addchildToRoot(root, layout);

        Log.v("kc", "1-->" + getResources().getDisplayMetrics().density);
        Log.v("kc", "2-->" + getResources().getDisplayMetrics().densityDpi);
        Log.v("kc", "3-->" + getResources().getDisplayMetrics().scaledDensity);
        Log.v("kc", "4-->" + getResources().getDisplayMetrics().widthPixels);
        Log.v("kc", "5-->" + getResources().getDisplayMetrics().heightPixels);
        Log.v("kc", "6-->" + getResources().getDisplayMetrics().xdpi);
        Log.v("kc", "7-->" + getResources().getDisplayMetrics().ydpi);
        
        layout = new LinearLayout(this);
        addBitmapDrawable(layout, R.drawable.logo120dpi, false);
        addBitmapDrawable(layout, R.drawable.logo160dpi, false);
        addBitmapDrawable(layout, R.drawable.logo240dpi, false);
        addLabelToRoot(root, "Autoscaled bitmap in drawable");
        addchildToRoot(root, layout);

        setContentView(scrollWrap(root));
    }

    private View addBitmapDrawable(LinearLayout layout, int res, boolean scale) {
        Bitmap bitmap = null;
        bitmap = loadAndPrintDpi(res, scale);
        
        View view = new View(this);
        final BitmapDrawable d = new BitmapDrawable(getResources(), bitmap);
        if(!scale) {
            d.setTargetDensity(getResources().getDisplayMetrics());
        }
        view.setBackgroundDrawable(d);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                d.getIntrinsicWidth(),
                d.getIntrinsicHeight()));
        layout.addView(view);
        return view;
    }

    private Bitmap loadAndPrintDpi(int res, boolean scale) {
        Bitmap bitmap;
        if(scale) {
            bitmap = BitmapFactory.decodeResource(getResources(), res);
        } else {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;
            bitmap = BitmapFactory.decodeResource(getResources(), res, opts);
        }
        return bitmap;
    }

    private void addLabelToRoot(LinearLayout root, String string) {
        TextView lable = new TextView(this);
        lable.setText(string);
        root.addView(lable, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void addchildToRoot(LinearLayout root, LinearLayout layout) {
        root.addView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private View scrollWrap(View view) {
        ScrollView scroller = new ScrollView(this);
        scroller.addView(view , new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT
                ));
        return scroller;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "test");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Log.v("kc", "5-->" + view1.getWidth());
                Log.v("kc", "6-->" + view2.getWidth());
                Log.v("kc", "7-->" + view3.getWidth());
                Log.v("kc", "7-->" + getWindowManager().getDefaultDisplay().getWidth());
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
