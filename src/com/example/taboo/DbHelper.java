package com.example.taboo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper
{
	public DbHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static final String DATABASE_NAME ="timetable";
	static final int DATABASE_VERSION = 1;
	static final String TAG = "DbHelper";
	
	private static final String TABLE_CREATE1="create table floors(_id integer primary key autoincrement, floorcode int not null, course text not null, remarks text not null, UNIQUE(floorcode) ON CONFLICT REPLACE);";
	private static final String TABLE_CREATE2="create table classes(_id integer primary key autoincrement, floorcode int not null, name text not null, classdate text not null, classtime text not null, period integer not null, topics text not null, remarks text not null, UNIQUE(floorcode, name) ON CONFLICT REPLACE);";
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try
		{
			db.execSQL(TABLE_CREATE1);
			db.execSQL(TABLE_CREATE2);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Updating database from version " + oldVersion + " to " + newVersion + " which will destroy old data");
		db.execSQL("DROP TABLE IF EXISTS floors;");
		db.execSQL("DROP TABLE IF EXISTS classes;");
		onCreate(db);
		
	}                                   
	

	

}
