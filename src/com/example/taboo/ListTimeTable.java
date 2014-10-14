package com.example.taboo;

import android.support.v7.app.ActionBarActivity;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;


public class ListTimeTable extends Activity
{
	String s;
	TableLayout tableClasses;
	DbAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_time_table);
		Bundle extras = getIntent().getExtras();
		s = extras.getString("code");
		tableClasses = (TableLayout) this.findViewById( R.id.tableClasses);
		db = new DbAdapter(this);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		deleteRowsFromTable();
		addRowsToTable(tableClasses,s);
	}
	
	public void deleteRowsFromTable() {
		if ( tableClasses.getChildCount() > 2) 
		    tableClasses.removeViews(2,tableClasses.getChildCount() - 2);
    }
	
	public void addRowsToTable(TableLayout table, String code) {
			
			int mcode = Integer.parseInt(code);
			db.open();
			Cursor c = db.getClassList(mcode);
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			
			if((c != null) && (c.getCount() > 0))
			{
				if(c.moveToFirst())
				{
					do
					{
						TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.classrow, null);
						((TextView)row.findViewById(R.id.textName)).setText(c.getString(1));
						((TextView)row.findViewById(R.id.textDate)).setText(c.getString(2));
						((TextView)row.findViewById(R.id.textTime)).setText(c.getString(3));
						
						table.addView(row);
						TableRow line = new TableRow(this);
	            	    TextView tv = new TextView(this);
	            	    tv.setBackgroundColor(Color.WHITE);
	            	    TableRow.LayoutParams lp = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,3);
	            	    lp.span = 3;
	            	    tv.setLayoutParams(lp);
	            	    
	            	    line.addView(tv);
	            	    
	            	    table.addView(line);
					}
					while(c.moveToNext());
				}
			} 
			db.close();
		} 

	
}
