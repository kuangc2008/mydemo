package com.example.demo.Nov;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MoreMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(this);
		textView.setText("哈哈哈哈哈哈哈哈和, so good a day!");
		setContentView(textView);
	}
	
}
