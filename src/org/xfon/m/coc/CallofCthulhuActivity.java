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

import org.xfon.m.coc.CustomNumberPicker.OnChangedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class CallofCthulhuActivity extends Activity implements OnSeekBarChangeListener {
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
	private ModifiableAttribute mod_str = new ModifiableAttribute( this, R.id.tv_str, R.id.tv_mod_str, R.id.sb_mod_str );
	private ModifiableAttribute mod_con = new ModifiableAttribute( this, R.id.tv_con, R.id.tv_mod_con, R.id.sb_mod_con );
	private ModifiableAttribute mod_dex = new ModifiableAttribute( this, R.id.tv_dex, R.id.tv_mod_dex, R.id.sb_mod_dex );
	private ModifiableAttribute mod_app = new ModifiableAttribute( this, R.id.tv_app, R.id.tv_mod_app, R.id.sb_mod_app );
	private ModifiableAttribute[] mod_attrs = {
		mod_str, mod_con, mod_dex, mod_app
	};
	
	private SortedMap<Integer,String> strDamBonus;
	final static int MAX_AGE = 90;
	private String errorMessage = null; 
	
	private int mustDrop = -1;
	private int unmod_edu;	
	
	private MediaPlayer player = null;
	
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if ( item.getItemId() == R.id.menu_about ) {
    		Builder builder = new AlertDialog.Builder( this );    		
    		builder.setTitle( "About" );
    		String msg = "";
    		msg += "Call of Cthulhu character generator v.1.0";    		
    		msg += "\n\n" + "Created by: xpapad@gmail.com";
    		msg += "\n\n" + "In memory of \"Ruthless\" Derek Arthur Blackwell, R.I.P.";
    		msg += "\n\n\n" + "\"Reincarnation has never been easier!\" ";
    		msg += "\n\n"   + "                              - The Arkham Observer";
    		builder.setMessage( msg );
    		AlertDialog dlg = builder.show();
    		TextView textView = (TextView)dlg.findViewById(android.R.id.message);
    		textView.setTextSize( 14 );
    		return true;
    	}
    	else {
    		return super.onOptionsItemSelected(item);
    	}
    }
            
    public void rerollAttributes( View view ) {
    	int i;    	
    	
    	if ( view.getId() != R.id.btn_roll ) return;
    	notifyUser();
    	rerollBasicAttributes();
    	calculateDerivedAttributes();  
    	initializeAge();
    	initializeSeekBars();
    	updateMustDrop();
    	clearErrors();  
    	((Button)findViewById( R.id.btn_roll )).setText( "Reroll" );    	
    }
    
    private void notifyUser() {
    	Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    	v.vibrate( 500 );
    	if ( player == null ) player = MediaPlayer.create( this, R.raw.dice );
    	player.start();    	
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
    	
    	((TextView)findViewById( R.id.tv_edu )).setTextColor( Color.LTGRAY );
    	findViewById( R.id.tv_age ).setVisibility( View.VISIBLE );
    	mustDrop = -1;
    	mod_str.setUnmodifiedValue( getIntValue( R.id.tv_str ) );
    	mod_con.setUnmodifiedValue( getIntValue( R.id.tv_con ) );
    	mod_dex.setUnmodifiedValue( getIntValue( R.id.tv_dex ) );
    	mod_app.setUnmodifiedValue( getIntValue( R.id.tv_app ) );
    	
    	unmod_edu = getIntValue( R.id.tv_edu );    	
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
    	
    	setIntValue( R.id.tv_ageStart, baseAge );
    	CustomNumberPicker agePicker = (CustomNumberPicker)findViewById( R.id.tv_age );    	
    	agePicker.setCurrent( baseAge );
    	agePicker.setRange( baseAge, MAX_AGE );
    	final Activity activity = this;
    	agePicker.setOnChangeListener( new OnChangedListener() {

    		@Override
			public void onChanged(CustomNumberPicker picker, int oldVal, int newVal) {
				int selectedAge = newVal;
				int ageDiff = selectedAge - baseAge;
				int extraEdu = ageDiff / 10;
				if ( extraEdu > 0 ) {
					((TextView)findViewById( R.id.tv_edu )).setTextColor( Color.GREEN );
				}
				else {
					((TextView)findViewById( R.id.tv_edu )).setTextColor( Color.LTGRAY );
				}
				setIntValue( R.id.tv_edu, unmod_edu + extraEdu );
				calculateDerivedAttributes();
				int newMustDrop = Math.max( 0, ( selectedAge / 10 ) - 3 );
				if ( newMustDrop != mustDrop ) {
					mustDrop = newMustDrop;
					updateMustDrop();
				}								
			}
		});    	    
    }
    
    public void updateMustDrop() {
    	// TODO: optimize
    	setDebug( "Called updateMustDrop" );
    	int totalMods = getTotalMods();
    	
    	// TODO: Use dropped
    	if ( mustDrop <= 0 ) {
    		initializeSeekBars();
    		((LinearLayout)findViewById( R.id.modAttributesLayout )).setVisibility( View.INVISIBLE );
    		clearErrors();
    	}
    	else if ( mustDrop == totalMods ) {
    		clearErrors();
    	}
    	else if ( mustDrop > totalMods ){    		
    		((LinearLayout)findViewById( R.id.modAttributesLayout )).setVisibility( View.VISIBLE );
    		setError( "You must drop " + ( mustDrop - totalMods ) + " attribute(s)" );    	
    	}
    	else if ( mustDrop < totalMods ){    		
    		((LinearLayout)findViewById( R.id.modAttributesLayout )).setVisibility( View.VISIBLE );
    		setError( "You must raise " + ( totalMods - mustDrop ) + " attribute(s)" );
    	}
    }
    
    private int getTotalMods() {
    	int sum = 0;
    	for ( int i = 0; i < mod_attrs.length; i++ ) sum += mod_attrs[ i ].getMod();
    	return sum;
    }
    
    private void initializeSeekBars ( ) {
    	for ( int i = 0; i < mod_attrs.length; i++ ) {
    		SeekBar sb = (SeekBar)findViewById( mod_attrs[ i ].getSeekBarId() );
    		sb.setMax( 5 );
    		sb.setProgress( 0 );
    		sb.setOnSeekBarChangeListener( this );
    		mod_attrs[ i ].setMod( 0 );
    	}
    }

    
    private int getIntValue( int id ) {
    	return Integer.parseInt(((TextView)findViewById( id )).getText().toString());
    }
    
    private void setIntValue( int id, int value ) {
    	((TextView)findViewById( id )).setText( "" + value );
    }
    
    private void setDebug( String value ) {
    	//((TextView)findViewById( R.id.debug )).setText( value );
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
    		msg.setText( "Your investigator is ready!" );
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int id = seekBar.getId();
		int pos;
		for ( pos = 0; pos < mod_attrs.length; pos++ ) {
			if ( mod_attrs[ pos ].getSeekBarId() == id ) break;
		}
		mod_attrs[ pos ].setMod( progress );
		if ( fromUser ) {
			calculateDerivedAttributes();
			updateMustDrop();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}