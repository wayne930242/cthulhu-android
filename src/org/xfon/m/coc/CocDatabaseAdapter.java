package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		saveAttributes( investigator, saveName );
		saveSkillCategories( investigator, saveName );
		saveSkills( investigator, saveName );
	}
	
	
	private void saveAttributes( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getWritableDatabase();
		String table = dbHelper.getAttributesTable();
		boolean exists = false;
		long result;
						
		// Check if exists
		String where = "name='" + saveName + "'";
		Cursor c = db.query( true, table, new String[] { "_id" }, where, null, null, null, null, null );
		if ( c.moveToFirst() ) exists = true;					
		c.close();
		
		ContentValues values = new ContentValues();
		values.put( "name", saveName );
		values.put( "age", investigator.getAge() );
		Attribute[] attributes = investigator.getBaseAttributes();
		
		for ( Attribute attr: attributes ) {			
			String name = attr.getName().toLowerCase();
			values.put( "base_" + name, attr.getUnmodifiedValue() );
			values.put( "mod_" + name, attr.getMod() );
		}			
		
		if ( !exists ) {			
			db.insert(table, null, values);
		}
		else {
			db.update( table, values, where, null );
		}
		db.close();
	}
	
	private void saveSkills( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getWritableDatabase();
		String table = dbHelper.getSkillsTable();
		boolean exists = false;

		/* Delete all added skills first */
		String where = "name='" + saveName + "' and is_added=1";
		db.delete( table, where, null );
		
		ContentValues values = new ContentValues();
		List<ISkill> skills = investigator.getSkills().list();
		for ( ISkill skill: skills ) {
			exists = false;
			if ( skill.isCategory() ) continue;
			
			// Check if exists
			where = "name='" + saveName + "' and skill_name='" + skill.getName() + "'";
			Cursor c = db.query( true, table, new String[] { "_id" }, where, null, null, null, null, null );
			if ( c.moveToFirst() ) exists = true;					
			c.close();
			
			Skill sk = (Skill)skill;
			if ( sk.getName().length() == 0 ) continue;
			values.put( "name", saveName );
			values.put( "skill_name", sk.getName() );
			values.put( "base_value", sk.getBaseValue() );
			values.put( "value", sk.getValue() );
			values.put( "category_name", sk.getCategory() == null ? null : sk.getCategory().getName() );
			values.put( "is_occupational", sk.isOccupational() );
			values.put( "is_added", sk.isAdded() );

			if ( !exists ) {			
				long result = db.insert(table, null, values);
			}
			else {
				long result = db.update( table, values, where, null );
			}
		}
		db.close();
	}
	
	private void saveSkillCategories( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getWritableDatabase();
		String table = dbHelper.getSkillCategoriesTable();
		boolean exists = false;

		ContentValues values = new ContentValues();
		List<ISkill> skills = investigator.getSkills().list();
		for ( ISkill skill: skills ) {
			exists = false;
			if ( !skill.isCategory() ) continue;
			
			// Check if exists
			String where = "name='" + saveName + "' and category_name='" + skill.getName() + "'";
			Cursor c = db.query( true, table, new String[] { "_id" }, where, null, null, null, null, null );
			if ( c.moveToFirst() ) exists = true;					
			c.close();
			
			SkillCategory cat = (SkillCategory)skill;
			values.put( "name", saveName );
			values.put( "category_name", cat.getName() );
			values.put( "base_value", cat.getBaseValue() );
			values.put( "is_occupational", cat.isOccupational() );

			if ( !exists ) {			
				db.insert(table, null, values);
			}
			else {
				db.update( table, values, where, null );
			}
		}
		db.close();
	}
	
	public synchronized void loadInvestigator( Investigator investigator, String saveName ) {
		loadAttributes( investigator, saveName );
		// ORDER IS IMPORTANT! Categories must be loaded first.
		Map<String,SkillCategory> categories = loadSkillCategories( investigator, saveName );
		loadSkills( investigator, saveName, categories );
	}
	
	private void loadAttributes( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getReadableDatabase();
		String table = dbHelper.getAttributesTable();
		List<String> columns = new ArrayList<String>();
				
		columns.add( "age" );
		Attribute[] attributes = investigator.getBaseAttributes();
		for ( Attribute attr: attributes ) {			
			String name = attr.getName().toLowerCase();
			columns.add( "base_" + name );
			columns.add( "mod_" + name );
		}
		
		Cursor c = db.query( table, columns.toArray( new String[0] ), "name='" + saveName + "'", null, null, null, null, null );
		boolean exists = c.moveToFirst();
		if ( !exists ) {
			c.close();
			db.close();
			return;
		}		
		
		for ( Attribute attr: attributes ) {			
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
	
	private void loadSkills( Investigator investigator, String saveName, Map<String,SkillCategory> categories ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getReadableDatabase();
		String table = dbHelper.getSkillsTable();		
		String[] colNames = { "skill_name", "base_value", "value", "category_name", "is_occupational", "is_added" };
		List<String> columns = new ArrayList<String>( Arrays.asList( colNames ) );
		
		Cursor c = db.query( table, columns.toArray( new String[0] ), "name='" + saveName + "'", null, null, null, null, null );
		boolean exists = c.moveToFirst();
		if ( !exists ) {
			c.close();
			db.close();
			return;
		}		
		
		Skills skills = Skills.emptySkills();
		do {
			int nameId = c.getColumnIndex( "skill_name" );
			int baseValueId = c.getColumnIndex( "base_value" );
			int valueId = c.getColumnIndex( "value" );
			int categoryNameId = c.getColumnIndex( "category_name" );
			int isOccupationalId = c.getColumnIndex( "is_occupational" );
			int isAddedId = c.getColumnIndex( "is_added" );
			
			String name = c.getString( nameId );
			int baseValue = c.getInt( baseValueId );
			int value = c.getInt( valueId );			
			String categoryName = c.getString( categoryNameId );
			boolean isOccupational = c.getInt( isOccupationalId ) == 0 ? false : true;
			boolean isAdded = c.getInt( isAddedId ) == 0 ? false : true;
			
			Skill skill = null;
			if ( categoryName != null ) {
				SkillCategory cat = categories.get( categoryName );
				/* 
				 * At this point we assume that new categories cannot be added, 
				 * therefore cat cannot be null
				 */
				skill = new Skill( cat );
				skill.setName( name );
			}
			else {
				skill = new Skill( name, baseValue );
			}
			skill.setValue( value );
			skill.setOccupational( isOccupational );
			skills.add( skill );
		} while ( c.moveToNext() );

		for ( SkillCategory cat: categories.values() ) {
			skills.add( cat );
		}
		skills.sort();
		investigator.setSkills( skills );
		c.close();
		db.close();
	}
	
	private Map<String,SkillCategory> loadSkillCategories( Investigator investigator, String saveName ) {
		dbHelper = new CocDatabaseHelper(context);				
		db = dbHelper.getReadableDatabase();
		String table = dbHelper.getSkillCategoriesTable();
		String[] colNames = { "category_name", "base_value", "is_occupational" };
		List<String> columns = new ArrayList<String>( Arrays.asList( colNames ) );
		Map<String,SkillCategory> categories = new HashMap<String, SkillCategory>(); 
				
		Cursor c = db.query( table, columns.toArray( new String[0] ), "name='" + saveName + "'", null, null, null, null, null );
		boolean exists = c.moveToFirst();
		if ( !exists ) {
			c.close();
			db.close();
			return categories;
		}		
		
		do {
			int nameId = c.getColumnIndex( "category_name" );
			int baseValueId = c.getColumnIndex( "base_value" );
			int isOccupationalId = c.getColumnIndex( "is_occupational" );
						
			String name = c.getString( nameId );
			int baseValue = c.getInt( baseValueId );
			boolean isOccupational = c.getInt( isOccupationalId ) == 0 ? false : true;
			
			SkillCategory cat = new SkillCategory( name, baseValue );
			cat.setOccupational( isOccupational );
			categories.put( name, cat );
			// TODO
		} while ( c.moveToNext() );
		c.close();
		db.close();
		return categories;
	}
}
