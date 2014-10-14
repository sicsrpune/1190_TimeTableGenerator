package com.example.taboo;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class FloorDetails extends Activity
{
	private DbAdapter db = new DbAdapter(this);
	private TextView editCode, editCourse, editRemarks;
	private Button add, list, update;
	private String s, code;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_floordetails);
		editCourse = (TextView) this.findViewById(R.id.editCourse) ;
		editRemarks = (TextView) this.findViewById(R.id.editRemarks);
		editCode = (TextView) this.findViewById(R.id.editBatchCode);
		add = (Button) this.findViewById(R.id.addclasses);
		list = (Button) this.findViewById(R.id.listclasses);
		update = (Button) this.findViewById(R.id.updateclassbut);
		Bundle extras = getIntent().getExtras();
		s = extras.getString("listvalue"); 
		String[] str = s.split("-");
		code = str[0];
		db.open(); 
		int n = Integer.parseInt(code);
		Toast.makeText(getApplicationContext(), "n = " + n, Toast.LENGTH_SHORT).show();
		Cursor c = db.getFloorInfo(n);
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				editCode.setText(code);
				editCourse.setText(c.getString(0));
				editRemarks.setText(c.getString(1));  
			}
		} 
		else
		{
			Toast.makeText(getApplicationContext(), "Returning null", Toast.LENGTH_SHORT).show();
		}
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("android.intent.action.ADDCLASSES");
				i.putExtra("code", code);
				startActivity(i);
				
			}
		}); 
		
		list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("android.intent.action.LISTTIMETABLE");
				i.putExtra("code", code);
				startActivity(i);
			}
		});
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(FloorDetails.this);
				alert.setTitle("Update details");
				Context context = getApplicationContext();
				LinearLayout layout = new LinearLayout(context);
				layout.setOrientation(LinearLayout.VERTICAL);
				final TextView mnames = new TextView(context); 
				mnames.setText("Enter class name");
				mnames.setTextColor(Color.parseColor("#FFFFFF"));
				final EditText names = new EditText(context);
				layout.addView(mnames);
				layout.addView(names);
				alert.setView(layout);
				alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String n = names.getText().toString();
						if(!(n.equals("")))
						{
							Intent i = new Intent("android.intent.action.UPDATECLASS");
							i.putExtra("cname", n);
							i.putExtra("code", code);
							startActivity(i);
						}
						else
						{
							InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
							Toast.makeText(getApplicationContext(), "Please enter class name", Toast.LENGTH_SHORT).show();
						}
						
					}
				});
				alert.show();
			}
		});
		
		
	}
	
	

}
