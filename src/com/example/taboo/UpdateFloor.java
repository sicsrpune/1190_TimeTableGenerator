package com.example.taboo;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class UpdateFloor extends Activity
{
	private DbAdapter db;
	private static final int DATE_DIALOG = 1;
	private static final int TIME_DIALOG = 2;
	private int day, month, year, hours, mins;
	private EditText editCourse, editRemarks;
	private Button update, delete;
	private String course, remarks, icode;
	private int code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatefloor);
		editCourse = (EditText) this.findViewById(R.id.editCourse) ;
		editRemarks = (EditText) this.findViewById(R.id.editRemarks);
		update = (Button) this.findViewById(R.id.updatefloor);
		delete = (Button) this.findViewById(R.id.deletefloor);
		db = new DbAdapter(this);
		Bundle extras = getIntent().getExtras();
		icode = extras.getString("code"); 
		db.open();
		Cursor c = db.getFloorInfo(Integer.parseInt(icode));
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				editCourse.setText(c.getString(0));
				editRemarks.setText(c.getString(1)); 
			}
		} 
		else
		{
			Toast.makeText(getApplicationContext(), "Floor code does not exist", Toast.LENGTH_SHORT).show();
			Intent i =new Intent("android.intent.action.ADDFLOOR");
			startActivity(i);
		}
		db.close();
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if((editCourse.getText().toString().equals("")) ||
				   (editRemarks.getText().toString().equals("")))
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Please enter all fields" , Toast.LENGTH_SHORT).show();
					}
					else
					{
						db.open();
						course = editCourse.getText().toString();
						remarks = editRemarks.getText().toString();
						if(db.updateFloor(Integer.parseInt(icode), course, remarks))
						{
							InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
							imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
							Toast.makeText(getApplicationContext(), "Floor updated successfully", Toast.LENGTH_SHORT).show();
							db.close();
							Intent i =new Intent("android.intent.action.ADDFLOOR");
							startActivity(i);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
						}
					}
				
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.open();
				if(db.deleteFloor(Integer.parseInt(icode)))
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					Toast.makeText(getApplicationContext(), "Floor deleted successfully", Toast.LENGTH_SHORT).show();
					db.close();
					editCourse.setText("");
				    editRemarks.setText("");
				    Intent i =new Intent("android.intent.action.ADDFLOOR");
					startActivity(i);
				}
				else
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
					   
		
		
	}
	
	

	

}
