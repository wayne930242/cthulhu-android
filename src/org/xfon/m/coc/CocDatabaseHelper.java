package org.xfon.m.coc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CocDatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private static final String DATABASE_NAME = "activedata";
	private static final int DATABASE_VERSION = 5;
	private static final String TABLE_ATTRIBUTES = "attributes";
	private static final String TABLE_SKILLS = "skills";
	private static final String TABLE_SKILL_CATEGORIES = "skill_categories";
	
	public CocDatabaseHelper( Context context ) {
		super( context, DATABASE_NAME, null, DATABASE_VERSION );
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL( context.getString( R.string.sqlCreateAttributes ) );
		db.execSQL( context.getString( R.string.sqlCreateSkills ) );
		db.execSQL( context.getString( R.string.sqlCreateSkillCategories ) );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w( CocDatabaseHelper.class.getName(), "Upgrading activedata database from " + oldVersion + " to " + newVersion );
		db.execSQL( "DROP TABLE IF EXISTS " + TABLE_ATTRIBUTES );
		db.execSQL( "DROP TABLE IF EXISTS " + TABLE_SKILLS );
		db.execSQL( "DROP TABLE IF EXISTS " + TABLE_SKILL_CATEGORIES );
		onCreate( db );
	}
	
	public String getAttributesTable() {
		return TABLE_ATTRIBUTES;
	}
	
	public String getSkillsTable() {
		return TABLE_SKILLS;
	}
	
	public String getSkillCategoriesTable() {
		return TABLE_SKILL_CATEGORIES;
	}
}
