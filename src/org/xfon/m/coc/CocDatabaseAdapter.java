package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CocDatabaseAdapter {	
	private Context context;
	private SQLiteDatabase db;
	private CocDatabaseHelper dbHelper;
	
	public CocDatabaseAdapter( Context context ) {		
		this.context = context;			
	}
		
	
	public synchronized void saveInvestigator( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getWritableDatabase();
		String table = dbHelper.getAttributesTable();
		boolean exists = false;
		long result;
						
		// Check if exists
		Cursor c = db.query( true, table, new String[] { "_id" }, "name='" + saveName + "'", null, null, null, null, null );
		if ( c.moveToFirst() ) exists = true;					
		c.close();
		
		ContentValues values = new ContentValues();
		values.put( "name", saveName );
		values.put( "age", investigator.getAge() );
		Attribute[] attributes = investigator.getBaseAttributes();
		
		for ( int i = 0; i < attributes.length; i++ ) {
			Attribute attr = attributes[ i ];
			String name = attr.getName().toLowerCase();
			values.put( "base_" + name, attr.getUnmodifiedValue() );
			values.put( "mod_" + name, attr.getMod() );
		}
		if ( !exists ) {			
			result = db.insert(table, null, values);
		}
		else {
			result = db.update( table, values, "name='" + saveName + "'", null );
		}
		db.close();
	}
	
	public synchronized void loadInvestigator( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getReadableDatabase();
		String table = dbHelper.getAttributesTable();
		List<String> columns = new ArrayList<String>();
				
		columns.add( "age" );
		Attribute[] attributes = investigator.getBaseAttributes();
		for ( int i = 0; i < attributes.length; i++ ) {
			Attribute attr = attributes[ i ];
			String name = attr.getName().toLowerCase();
			columns.add( "base_" + name );
			columns.add( "mod_" + name );
		}
		
		Cursor c = db.query( table, columns.toArray( new String[0] ), "name='" + saveName + "'", null, null, null, null, null );
		boolean exists = c.moveToFirst();
		if ( !exists ) {
			db.close();
			return;
		}
		
		for ( int i = 0; i < attributes.length; i++ ) {
			Attribute attr = attributes[ i ];
			String name = attr.getName().toLowerCase();
			String baseName = "base_" + name;
			String modName = "mod_" + name;
			int baseId = c.getColumnIndex( baseName );
			int modId = c.getColumnIndex( modName );
			int baseValue = c.getInt( baseId );
			int modValue = c.getInt( modId );
			attr.setUnmodifiedValue( baseValue );
			attr.setMod( modValue );
		}
		int ageId = c.getColumnIndex( "age" );
		int age = c.getInt( ageId );
		investigator.setBaseAge();
		investigator.setAge( age );
		c.close();
		db.close();
	}
}
