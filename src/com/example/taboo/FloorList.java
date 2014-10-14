package com.example.taboo;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;


public class FloorList extends ListActivity
{
	DbAdapter db = new DbAdapter(this);
	ListView v;
	Cursor c;
	ArrayList<String> mylist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listfloors);
		db.open();
		ListView lv = getListView();
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setTextFilterEnabled(true);
		c = db.getFloorList();
		mylist = new ArrayList<>();
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				do
				{
					
					mylist.add(c.getString(0) + "-" + c.getString(1));
					
				}
				while(c.moveToNext());
			}
			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mylist));
		}
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		Intent i = new Intent("android.intent.action.FLOORDETAILS");
		String s = (String) parent.getItemAtPosition(position);
		i.putExtra("listvalue", s);
		startActivity(i);
	}
}
