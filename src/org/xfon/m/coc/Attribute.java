package org.xfon.m.coc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

final public class Attribute {
	private Activity activity;
	
	private Set<OnAttributeChangedListener> onAttributeChangedListeners;
	
	private String name;	
	
	private int baseTextViewId;
	
	private int unmodifiedValue;
	private int mod;
	
	private Dice rollingDice;
	
	public Attribute(Activity activity, String name, int baseTextViewId, Dice rollingDice ) {
		super();
		this.activity = activity;
		this.name = name;
		this.baseTextViewId = baseTextViewId;
		this.rollingDice = rollingDice;
		
		//this.reducer = null;
		this.unmodifiedValue = 0;
		this.mod = 0;
		this.onAttributeChangedListeners = new HashSet<OnAttributeChangedListener>();
	}
		
	public int getUnmodifiedValue() {
		return unmodifiedValue;
	}

	public void setUnmodifiedValue(int unmodifiedValue) {
		if ( this.unmodifiedValue != unmodifiedValue ) notifyAttributeChangedListeners();
		this.unmodifiedValue = unmodifiedValue;
	}
	
	public void setRollingDice( Dice rollingDice ) {
		this.rollingDice = rollingDice;
	}

	public void setMod( int mod ) {
		int oldMod = this.mod;
		if ( this.mod != mod ) notifyAttributeChangedListeners();
		this.mod = mod;		
		if ( baseTextViewId != 0 ) setIntValue( baseTextViewId, unmodifiedValue + mod );
		
		TextView tv = (TextView)activity.findViewById( baseTextViewId );
		if ( tv == null ) return;
		
		if ( mod == 0 && oldMod != 0 ) {
			tv.setTextColor( Color.LTGRAY );
		}		
		else if ( mod > 0 ) tv.setTextColor( Color.GREEN );
		else if ( mod < 0 ) tv.setTextColor( Color.RED ); 		
	}
	
	public int getMod() {
		return this.mod;
	}
	
    public String getName() {
    	return this.name;
    }
	
    private void setIntValue( int id, int value ) {
    	((TextView)activity.findViewById( id )).setText( "" + value );
    }
    
    public int getTotal() {
    	return unmodifiedValue + mod;
    }
    
    public int roll( StringBuffer log ) {
    	if ( rollingDice == null ) return -1;
    	if ( log != null ) log.append( name + " " );
    	int result = rollingDice.roll( log );
    	if ( log != null ) log.append( "\n" );
    	setUnmodifiedValue( result );
    	setMod( 0 );
    	notifyAttributeChangedListeners();
    	return result;
    }       
    
    public int roll() {
    	return roll( null );
    }
    
	public void addOnAttributeChangedListener( OnAttributeChangedListener listener ) {
		onAttributeChangedListeners.add( listener );
	}
	
	private void notifyAttributeChangedListeners( ) {
		Iterator<OnAttributeChangedListener> it = onAttributeChangedListeners.iterator();
		while ( it.hasNext() ) it.next().attributeChanged( this );
	}
}
