package com.example.taboo;

import android.support.v7.app.ActionBarActivity;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateClass extends Activity
{
	private static final int DATE_DIALOG = 1;
	private static final int TIME_DIALOG = 2;
	private int day, month, year, hours, mins;
	private TextView textClassDate, textClassTime;
	private EditText editName, editPeriod,editRemarks, editTopics;
	private Button update, delete;
	private DbAdapter db = new DbAdapter(this);
	String code, oldname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_class);
		textClassDate = (TextView) this.findViewById(R.id.textClassDate);
		textClassTime = (TextView) this.findViewById(R.id.textClassTime);
		editName = (EditText) this.findViewById(R.id.editName);
		editPeriod = (EditText) this.findViewById(R.id.editPeriod);
		editRemarks = (EditText) this.findViewById(R.id.editRemarks);
		editTopics = (EditText) this.findViewById(R.id.editTopics);
		update = (Button) this.findViewById(R.id.updateoneclass);
		delete = (Button) this.findViewById(R.id.deleteoneclass);
		Bundle extras = getIntent().getExtras();
		code = extras.getString("code"); 
		oldname = extras.getString("cname");
		db.open();
		Cursor c = db.getClassDetails(Integer.parseInt(code), oldname);
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				editName.setText(oldname);
				textClassDate.setText(c.getString(0));
				textClassDate.setText(c.getString(1));
				editPeriod.setText(c.getString(2));
				editRemarks.setText(c.getString(3));
				editTopics.setText(c.getString(4));
				db.close();
			}
			else
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			    Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No such class", Toast.LENGTH_SHORT).show();
			Intent i = new Intent("android.intent.action.FLOORLIST");
			startActivity(i);
		}  
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.open();
				if((textClassDate.getText().toString().equals("")) ||
						   (textClassTime.getText().toString().equals("")) ||
						   (editPeriod.getText().toString().equals("")) ||
						   (editRemarks.getText().toString().equals("")) ||
						   (editTopics.getText().toString().equals("")))
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
					}
					else
					{
						String newname = editName.getText().toString();
						String dates = textClassDate.getText().toString();
						String times = textClassTime.getText().toString();
						int period = Integer.parseInt(editPeriod.getText().toString());
						String topics = editTopics.getText().toString();
						String remarks = editRemarks.getText().toString();
						if(db.updateClasses(Integer.parseInt(code), oldname, newname, dates, times, period, topics, remarks))
						{
							InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
							imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
							Toast.makeText(getApplicationContext(), "Class Updated", Toast.LENGTH_SHORT).show();
							Intent i = new Intent("android.intent.action.FLOORLIST");
							startActivity(i);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
							Intent i = new Intent("android.intent.action.FLOORLIST");
							startActivity(i);
						}
					}
				}
			});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.open();
				if(db.deleteClass(Integer.parseInt(code), oldname))
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				    Toast.makeText(getApplicationContext(), "Class Deleted", Toast.LENGTH_SHORT).show();
				    Intent i = new Intent("android.intent.action.FLOORLIST");
					startActivity(i);
				}
				else
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				    Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
				    Intent i = new Intent("android.intent.action.FLOORLIST");
					startActivity(i);
				}
				
			}
		});
		
		setDateToSysdate();
		updateDateDisplay(); 
	}
		private void setDateToSysdate() {
			Calendar c = Calendar.getInstance();
			day = c.get(Calendar.DAY_OF_MONTH);
			month = c.get(Calendar.MONTH);
			year = c.get(Calendar.YEAR);
		}
	
		public void showDatePicker(View v) {
			showDialog(DATE_DIALOG);
		}	
	
		public void showTimePicker(View v) {
			showDialog(TIME_DIALOG);
		}

		@Override
		protected Dialog onCreateDialog(int id) {
			super.onCreateDialog(id);

			switch (id) {
				case DATE_DIALOG:
					return new DatePickerDialog(this, dateSetListener, year, month, day);
				case TIME_DIALOG:
					return new TimePickerDialog(this, timeSetListener, hours,mins, false);			
			}
			return null;
		}

		private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int pYear, int pMonth, int pDay) {
				year = pYear;
				month = pMonth;
				day = pDay;
				updateDateDisplay();
			}
		};
	
	
		private TimePickerDialog.OnTimeSetListener timeSetListener = 
				new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					  hours = pHours;
					  mins = pMins;
					  updateTimeDisplay();
				}
	 
		};
	
	

		private void updateDateDisplay() {
			// Month is 0 based so add 1
			textClassDate.setText(String.format("%04d-%02d-%02d", year, month + 1,day));
		}
	
		private void updateTimeDisplay() {
			// Month is 0 based so add 1
			textClassTime.setText(String.format("%02d:%02d", hours,mins));
		}	 
	
}
