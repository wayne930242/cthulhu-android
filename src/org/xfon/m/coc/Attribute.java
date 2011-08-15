package org.xfon.m.coc;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

final public class Attribute {
	private Activity activity;
	
	private String name;	
	
	private int baseTextViewId;
	
	private int unmodifiedValue;
	private int mod;
	
	private Dice rollingDice;
	//private AttributeReducer reducer;
	
	public Attribute(Activity activity, String name, int baseTextViewId, Dice rollingDice ) {
		super();
		this.activity = activity;
		this.name = name;
		this.baseTextViewId = baseTextViewId;
		this.rollingDice = rollingDice;
		
		//this.reducer = null;
		this.unmodifiedValue = 0;
		this.mod = 0;
	}
		
	public int getUnmodifiedValue() {
		return unmodifiedValue;
	}

	public void setUnmodifiedValue(int unmodifiedValue) {
		this.unmodifiedValue = unmodifiedValue;
	}
	
	public void setRollingDice( Dice rollingDice ) {
		this.rollingDice = rollingDice;
	}

	public void setMod( int mod ) {
		int oldMod = this.mod;
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
    
    public int roll() {
    	if ( rollingDice == null ) return -1;
    	int result = rollingDice.roll();
    	setUnmodifiedValue( result );
    	setMod( 0 );
    	return result;
    }       

}
