package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.ErrorManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class CallofCthulhuActivity extends Activity {
	private Random random;
	private int[] d3_6 = {
			R.id.tv_str, R.id.tv_con, R.id.tv_pow, R.id.tv_dex, R.id.tv_app 
	};
	private int[] d2_6_p6 = {
			R.id.tv_siz, R.id.tv_int     	
	};
	private int[] d3_6_p3 = {
			R.id.tv_edu
	};
	private SortedMap<Integer,String> strDamBonus;
	final static int MAX_AGE = 90;
	private String errorMessage = null; 
	
	private int mustDrop = 0;
	private int unmod_edu;
	private int unmod_str;
	private int unmod_con;
	private int unmod_dex;
	private int unmod_app;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
        strDamBonus = new TreeMap<Integer,String>();
        strDamBonus.put( 12, "-1d6" );
        strDamBonus.put( 16, "-1d4" );
        strDamBonus.put( 24, "+0" );
        strDamBonus.put( 32, "+1d4" );
        strDamBonus.put( 40, "+1d6" );
        strDamBonus.put( 56, "+2d6" );
        strDamBonus.put( 72, "+3d6" );
        strDamBonus.put( 88, "+4d6" );    
        clearErrors();
        setContentView(R.layout.main);        
    }    
    
    public void rerollAttributes( View view ) {
    	int i;    	
    	
    	if ( view.getId() != R.id.btn_roll ) return;
    	rerollBasicAttributes();
    	calculateDerivedAttributes();  
    	initializeAge();
    	((Button)findViewById( R.id.btn_roll )).setText( "Reroll" );
    	clearErrors();    	
    }
    
    private void rerollBasicAttributes() {
    	int i;
    	for ( i = 0; i < d3_6.length; i++ ) {
    		TextView tv = (TextView)findViewById( d3_6[ i ] );
    		tv.setText( "" + roll( 3, 6, 0 ) );
    	}
    	for ( i = 0; i < d2_6_p6.length; i++ ) {
    		TextView tv = (TextView)findViewById( d2_6_p6[ i ] );
    		tv.setText( "" + roll( 2, 6, 6 ) );
    	}
    	for ( i = 0; i < d3_6_p3.length; i++ ) {
    		TextView tv = (TextView)findViewById( d3_6_p3[ i ] );
    		tv.setText( "" + roll( 3, 6, 3 ) );
    	}   	
    	
    	mustDrop = 0;
    	unmod_edu = getIntValue( R.id.tv_edu );
    	unmod_str = getIntValue( R.id.tv_str );
    	unmod_con = getIntValue( R.id.tv_con );
    	unmod_dex = getIntValue( R.id.tv_dex );
    	unmod_app = getIntValue( R.id.tv_app );
    }
    
    private void calculateDerivedAttributes() {
    	int val_pow = getIntValue( R.id.tv_pow );
    	int val_int = getIntValue( R.id.tv_int );
    	int val_edu = getIntValue( R.id.tv_edu );
    	int val_con = getIntValue( R.id.tv_con );
    	int val_siz = getIntValue( R.id.tv_siz );
    	int val_str = getIntValue( R.id.tv_str );    	   
    	
    	setIntValue( R.id.tv_san, 5 * val_pow );
    	setIntValue( R.id.tv_idea, 5 * val_int );
    	setIntValue( R.id.tv_luck, 5 * val_pow );
    	setIntValue( R.id.tv_know, 5 * val_edu );

    	// Hit points
    	setIntValue( R.id.tv_hp, roundUpDiv( val_siz + val_con, 2 ) );
    	
    	// Str bonus
    	Iterator<Integer> it = strDamBonus.keySet().iterator();
    	while ( it.hasNext() ) {
    		int value = it.next();
    		if ( val_str + val_siz <= value ) {
    			String bonus = strDamBonus.get( value );
    			((TextView)findViewById( R.id.tv_dam ) ).setText( bonus );
    			break;
    		}
    	}
    	
    	// Points
    	setIntValue( R.id.tv_points_occ, 20 * val_edu );
    	setIntValue( R.id.tv_points_per, 10 * val_int );
    }
    
    private void initializeAge() {
    	int val_edu = getIntValue( R.id.tv_edu );
    	final int baseAge = 6 + val_edu;
    	int size = MAX_AGE - baseAge + 1;
    	String[] ages = new String[ size ];
    	for ( int i = 0; i < size; i++ ) ages[ i ] = "" + (baseAge + i);    	    	
    	ArrayAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item, ages );
        Spinner spinnerAge = (Spinner)findViewById( R.id.tv_age );
    	spinnerAge.setAdapter( adapter );
    	final Activity activity = this;
    	spinnerAge.setOnItemSelectedListener( new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				int selectedAge = baseAge + position;
				int ageDiff = selectedAge - baseAge;
				int extraEdu = ageDiff / 10;
				if ( extraEdu > 0 ) {
					((TextView)findViewById( R.id.tv_edu )).setTextColor( Color.GREEN );
				}
				else {
					((TextView)findViewById( R.id.tv_edu )).setTextColor( Color.WHITE );
				}
				setIntValue( R.id.tv_edu, unmod_edu + extraEdu );
				mustDrop = ( selectedAge / 10 ) - 3;
				if ( mustDrop > 0 ) {					
					setError( "You must lower " + mustDrop + " attribute(s)." );
					findViewById( R.id.modAttributes ).setVisibility( View.VISIBLE );
					Spinner mod_str = (Spinner)findViewById( R.id.mod_str );
					String[] str_values = new String[ mustDrop + 1 ];
					for ( int i = 0; i <= mustDrop; i++ ) str_values[ i ] = "" + ( unmod_str - mustDrop + i );
			    	ArrayAdapter adapterStr = new ArrayAdapter( activity, android.R.layout.simple_spinner_dropdown_item, str_values );
					mod_str.setAdapter( adapterStr );
				}
				else {
					clearErrors();
					findViewById( R.id.modAttributes ).setVisibility( View.INVISIBLE );
				}
				calculateDerivedAttributes();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}    		
		} );
    }
    
    private int getIntValue( int id ) {
    	return Integer.parseInt(((TextView)findViewById( id )).getText().toString());
    }
    
    private void setIntValue( int id, int value ) {
    	((TextView)findViewById( id )).setText( "" + value );
    }
    
    private int roll( int count, int sides, int plus ) {
    	int sum = 0;
    	if ( count <= 0 || sides <= 0 ) return -1;
    	for ( int i = 0; i < count; i++ ) {
    		sum += random.nextInt( sides ) + 1;
    	}
    	return sum + plus;
    }
    
    private int roundUpDiv( int x, int y ) {
    	int ret = x / y;
    	if ( x % y > 0 ) ret++;
    	return ret;
    }
    
    private void updateMessage() {
    	TextView msg = (TextView)findViewById( R.id.message );
    	if ( msg == null ) return;
    	if ( errorMessage == null ) {
    		msg.setText( "Your character is ready!" );
    		msg.setTextColor( Color.GREEN );
    	}
    	else {
    		msg.setText( errorMessage );
    		msg.setTextColor( Color.RED );
    	}
    }
    
    private void clearErrors() {
    	errorMessage = null;
    	updateMessage();
    }
    
    private void setError( String msg ) {
    	errorMessage = msg;
    	updateMessage();
    }
}