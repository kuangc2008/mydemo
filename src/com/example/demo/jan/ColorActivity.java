package com.example.demo.jan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.demo.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ColorActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ColorItem> colors = getColors();
        
        Collections.sort(colors, new Comparator<ColorItem>() {
            @Override
            public int compare(ColorItem first, ColorItem second) {
                return first.value.compareTo(second.value);
            }
        });
        for(ColorItem item : colors) {
            Log.v("kc", "colorItem-->" + item);
        }
        MyAdapter myAdapter = new MyAdapter(this, R.layout.colors, colors);
        getListView().setAdapter(myAdapter);
    }

    private List<ColorItem> getColors() {
        List<ColorItem> colors = new ArrayList<ColorItem>();
        Field[] fields = R.color.class.getFields();
        for(int i=0; i<fields.length; i++) {
            try {
                ColorItem item = new ColorItem();
                item.name = fields[i].getName();
                item.valueInR = fields[i].getInt(null);
                int color = getResources().getColor(item.valueInR);
                item.value  = Integer.toHexString(color);
                colors.add(item);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return colors;
    }

    private class ColorItem {
        private String name;
        private String value;
        private int valueInR;
        @Override
        public String toString() {
            return "ColorItem [name=" + name + ", value=" + value + ", valueInR=" + valueInR + "]";
        }
    }
    
    private static class ViewHoler {
        TextView text1;
        TextView text2;
        ViewGroup bg;
        
    }

    public class MyAdapter extends BaseAdapter {
        private List<ColorItem> colors = null;
        private Context context;
        private int layout = 0;
        
        public MyAdapter(Context context, int layout,List<ColorItem> colors) {
            this.context = context;
            this.colors = colors;
            this.layout = layout;
        }
        
        @Override
        public int getCount() {
            return colors.size();
        }

        @Override
        public ColorItem getItem(int position) {
            return colors.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoler viewHolder = null;
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layout, null);
                viewHolder = new ViewHoler();
                viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
                viewHolder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
                viewHolder.bg = (ViewGroup) convertView.findViewById(R.id.color_bg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHoler) convertView.getTag();
            }
            ColorItem item = colors.get(position);
            viewHolder.bg.setBackgroundResource(item.valueInR);
            viewHolder.text1.setText(item.name);
            viewHolder.text2.setText(item.value);
            return convertView;
        }
        
    }
}
