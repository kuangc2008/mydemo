package com.example.demo.feb;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

public class PreferenceHeader extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hasHeaders()) {
            Button button = new Button(this);
            button.setText("Some action");
            setListFooter(button);
        }
    }
    
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(com.example.demo.R.xml.preference_headers, target);
    }
}
