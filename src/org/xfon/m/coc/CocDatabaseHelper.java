package org.xfon.m.coc;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CocDatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private static final String DATABASE_NAME = "activedata";
	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_ATTRIBUTES = "attributes";
	
	public CocDatabaseHelper( Context context ) {
		super( context, DATABASE_NAME, null, DATABASE_VERSION );
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL( context.getString( R.string.sqlCreateAttributes ) );		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w( CocDatabaseHelper.class.getName(), "Upgrading activedata database from " + oldVersion + " to " + newVersion );
		db.execSQL( "DROP TABLE IF EXISTS attributes" );
		onCreate( db );
	}
	
	public String getAttributesTable() {
		return TABLE_ATTRIBUTES;
	}
	
}
