package com.example.taboo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity
{

	Button viewfloors, addfloors, aboutus; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewfloors = (Button) findViewById(R.id.play);
		addfloors = (Button) findViewById(R.id.add);
		aboutus = (Button) findViewById(R.id.aboutus);
		viewfloors.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("android.intent.action.FLOORLIST");
				startActivity(i);
				
			}
		});
		
		addfloors.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i =new Intent("android.intent.action.ADDFLOOR");
				startActivity(i);
			}
		});
		
		aboutus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
				alert.setTitle("About Us");
				Context context = getApplicationContext();
				LinearLayout layout = new LinearLayout(context);
				layout.setOrientation(LinearLayout.VERTICAL);
				final TextView mnames = new TextView(context); 
				mnames.setText("Developer : ");
				mnames.setTextColor(Color.parseColor("#FFFFFF"));
				final TextView bline1 = new TextView(context);
				bline1.setText("          ");
				final TextView names = new TextView(context);
				names.setText("Nikhil Anand Master ");
				names.setTextColor(Color.parseColor("#FFFFFF"));
				final TextView bline2 = new TextView(context);
				bline2.setText("          ");
				final TextView mdesc = new TextView(context);
				mdesc.setText("Project : ");
				final TextView bline3 = new TextView(context);
				bline3.setText("          ");
				mdesc.setTextColor(Color.parseColor("#FFFFFF"));
				final TextView desc = new TextView(context);
				desc.setText("Teacher's Timetable");
				desc.setTextColor(Color.parseColor("#FFFFFF"));
				layout.addView(bline1);
				layout.addView(mnames);
			
				layout.addView(names);
				layout.addView(bline2);
				layout.addView(mdesc);
				
				layout.addView(desc);
				layout.addView(bline3);
				alert.setView(layout);
				alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
					}
				});
				alert.show();
			}
		});
		
	}
	
	

	
}
