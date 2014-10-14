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

public class AddFloor extends Activity
{
	private DbAdapter db;
	private static final int DATE_DIALOG = 1;
	private static final int TIME_DIALOG = 2;
	private int day, month, year, hours, mins;
	private EditText editBatchcode,editCourse,editRemarks;
	private Button add, update;
	private String course, remarks;
	private int code;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfloor);
		
		
		
		editBatchcode = (EditText) this.findViewById(R.id.editBatchCode) ;
		editCourse = (EditText) this.findViewById(R.id.editCourse) ;
		
		editRemarks = (EditText) this.findViewById(R.id.editRemarks);
		add = (Button) this.findViewById(R.id.addfloor);
		update = (Button) this.findViewById(R.id.updatefloor);
		db = new DbAdapter(this);
		
		add.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				if((editBatchcode.getText().toString().equals("")) || 
				   (editCourse.getText().toString().equals("")) ||
				   (editRemarks.getText().toString().equals(""))) 
				   {
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					Toast.makeText(getApplicationContext(), "Please enter all fields" , Toast.LENGTH_SHORT).show();
				}
				else
				{
					db.open();
					code = Integer.parseInt(editBatchcode.getText().toString());
					course = editCourse.getText().toString();
					remarks = editRemarks.getText().toString();
					long id = db.insertFloor(code, course, remarks);
					if(id !=-1)
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					    Toast.makeText(getApplicationContext(), "Floor added successfully", Toast.LENGTH_SHORT).show();
					    editBatchcode.setText("");
					    editCourse.setText("");
					    editRemarks.setText("");
					    db.close();
					    Intent i = new Intent("android.intent.action.FLOORLIST");
						startActivity(i);
					}
					else
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					    Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(editBatchcode.getText().toString().equals(""))
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				    Toast.makeText(getApplicationContext(), "Please enter the floor code", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent i =new Intent("android.intent.action.UPDATEFLOOR");
					i.putExtra("code", editBatchcode.getText().toString());
					startActivity(i);
							
				}
				
			}
		});
		
		
	}
	
	

}
