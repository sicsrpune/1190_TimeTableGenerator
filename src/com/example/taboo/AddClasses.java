package com.example.taboo;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class AddClasses extends Activity
{
	private static final int DATE_DIALOG = 1;
	private static final int TIME_DIALOG = 2;
	private int day, month, year, hours, mins;
	private TextView textClassDate, textClassTime, textBatchCode;
	private EditText editName, editPeriod,editRemarks, editTopics;
	private Button add;
	private DbAdapter db = new DbAdapter(this);
	String code; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addclasses);
	
		
		Bundle extras = getIntent().getExtras();
		code = extras.getString("code");
		Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
		textClassDate = (TextView) this.findViewById(R.id.textClassDate);
		textClassTime = (TextView) this.findViewById(R.id.textClassTime);
		editName = (EditText) this.findViewById(R.id.classname);
		editPeriod = (EditText) this.findViewById(R.id.editPeriod) ;
		editRemarks = (EditText) this.findViewById(R.id.editRemarks) ;
		editTopics = (EditText) this.findViewById(R.id.editTopics) ;
		add = (Button) this.findViewById(R.id.addoneclass);
		
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if((textClassDate.getText().toString().equals("")) ||
				   (textClassTime.getText().toString().equals("")) ||
				   (editPeriod.getText().toString().equals("")) ||
				   (editRemarks.getText().toString().equals("")) ||
				   (editTopics.getText().toString().equals("")))
				   {
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
				   }
				   else
				   {
					   db.open();
					   int incode = Integer.parseInt(code);
					   String name = editName.getText().toString();
					   String classdate = textClassDate.getText().toString();
					   String classtime = textClassTime.getText().toString();
					   int period = Integer.parseInt(editPeriod.getText().toString());
					   String remarks = editRemarks.getText().toString();
					   String topics = editTopics.getText().toString();
					   long id = db.insertClasses(incode, name, classdate, classtime, period, topics, remarks);
					   if(id != -1)
					   {
						   InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						   imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						   Toast.makeText(getApplicationContext(), "New class added", Toast.LENGTH_SHORT).show();
						   db.close();
						   Intent i = new Intent("android.intent.action.FLOORLIST");
						   startActivity(i);
						   
					   }
					   else
					   {
						   Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
					   }
				   
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
