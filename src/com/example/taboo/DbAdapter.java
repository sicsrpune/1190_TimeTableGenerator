package com.example.taboo;

import java.lang.Character.UnicodeBlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DbAdapter {

	private static final String FLOOR_TABLE_NAME = "floors";
	private static final String FLOOR_ID = "_id";
	private static final String FLOOR_FLOORCODE = "floorcode";
	private static final String FLOOR_COURSE = "course";
	private static final String FLOOR_CLASSESPERWEEK = "classesperweek";
	private static final String FLOOR_REMARKS = "remarks";

	private static final String CLASSES_TABLE_NAME = "classes";
	private static final String CLASSES_CLASSES_ID = "_id";
	private static final String CLASSES_FLOORCODE = "floorcode";
	private static final String CLASSES_NAME = "name";
	private static final String CLASSES_CLASSDATE = "classdate";
	private static final String CLASSES_CLASSTIME = "classtime";
	private static final String CLASSES_CLASSPERIOD = "period";
	private static final String CLASSES_TOPICS = "topics";
	private static final String CLASSES_REMARKS = "remarks";
	
	final Context context;
	DbHelper dbhelper;
	SQLiteDatabase db;
	
	public DbAdapter(Context context)
	{
		this.context = context;
		dbhelper = new DbHelper(context);
	}
	
	public DbAdapter open() throws SQLException
	{
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		dbhelper.close();
	}
	
	public long insertFloor(int floorcode, String course, String remarks)
	{
		ContentValues cv = new ContentValues();
		cv.put(FLOOR_FLOORCODE,floorcode);
		cv.put(FLOOR_COURSE, course);
		cv.put(FLOOR_REMARKS, remarks);
		return db.insert(FLOOR_TABLE_NAME, null , cv);
	}
	
	public long insertClasses(int floorcode, String name, String classdate, String classtime, int period, String topics, String remarks)
	{
		ContentValues cv = new ContentValues();
		cv.put(CLASSES_FLOORCODE, floorcode);
		cv.put(CLASSES_NAME, name);
		cv.put(CLASSES_CLASSDATE, classdate);
		cv.put(CLASSES_CLASSTIME, classtime);
		cv.put(CLASSES_CLASSPERIOD, period);
		cv.put(CLASSES_TOPICS, topics);
		cv.put(CLASSES_REMARKS, remarks);
		return db.insert(CLASSES_TABLE_NAME, null, cv);
	}
	
	public Cursor getFloorInfo(int code) throws SQLException
	{
		Cursor cur = db.query(true, FLOOR_TABLE_NAME, new String[] {FLOOR_COURSE, FLOOR_REMARKS} , FLOOR_FLOORCODE + "=" + code, null, null, null, null, null);                                 
		if(cur != null)
		{
			if(cur.moveToFirst())
			{
				return cur;
			}
		}
		return null;
	}
	
	public boolean updateFloor(int floorcode, String course, String remarks) throws SQLException
	{
		ContentValues cv = new ContentValues();
		cv.put(FLOOR_COURSE, course);
		cv.put(FLOOR_REMARKS, remarks);
		return db.update(FLOOR_TABLE_NAME, cv, FLOOR_FLOORCODE + "=" + floorcode, null) > 0;
	}
	
	public boolean deleteFloor(int code) throws SQLException
	{
		return db.delete(FLOOR_TABLE_NAME, FLOOR_FLOORCODE + "=" + code, null) > 0;
	}
	
	public Cursor getFloorList() throws SQLException
	{
		return db.query(FLOOR_TABLE_NAME, new String[] {FLOOR_FLOORCODE, FLOOR_COURSE}, null, null, null, null, null);
	}
	
	public Cursor getClassList(int code) throws SQLException
	{
		Cursor cur = db.query(true, CLASSES_TABLE_NAME, new String[] {CLASSES_CLASSES_ID, CLASSES_NAME, CLASSES_CLASSDATE, CLASSES_CLASSTIME}, CLASSES_FLOORCODE + "=" + code, null, null, null, null, null);  
		if(cur != null)
		{
			if(cur.moveToFirst())
			{
				return cur;
			}
		}
		return null;
	}
	
	public Cursor getClassDetails(int code, String name) throws SQLException
	{
		Cursor c = db.query(true, CLASSES_TABLE_NAME, new String[] {CLASSES_CLASSDATE, CLASSES_CLASSTIME, CLASSES_CLASSPERIOD, CLASSES_TOPICS, CLASSES_REMARKS}, CLASSES_FLOORCODE + "=" + code + " AND " + CLASSES_NAME + "=" + "'" + name + "'" , null, null, null, null, null);            
		if(c != null)
		{
			if(c.moveToFirst())
			{
				return c;
			}
		}
		return null;
	}
	
	
	public boolean updateClasses(int floorcode, String nameold, String namenew, String classdate, String classtime, int period, String topics, String remarks) throws SQLException
	{
		ContentValues cv = new ContentValues();
		cv.put(CLASSES_NAME, namenew);
		cv.put(CLASSES_CLASSDATE, classdate);
		cv.put(CLASSES_CLASSTIME, classtime);
		cv.put(CLASSES_CLASSPERIOD, period);
		cv.put(CLASSES_TOPICS, topics);
		cv.put(CLASSES_REMARKS, remarks);
		return db.update(CLASSES_TABLE_NAME, cv, CLASSES_FLOORCODE + "=" + floorcode + " AND " + CLASSES_NAME + "=" + "'" + nameold + "'" , null) > 0;
	}
	
	public boolean deleteClass(int code, String name) throws SQLException
	{
		return db.delete(CLASSES_TABLE_NAME, CLASSES_FLOORCODE + "=" + code + " AND " + CLASSES_NAME + "=" + "'" + name + "'", null) > 0;
	}
	
}
