package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class AttributeReducer extends TableRow implements OnSeekBarChangeListener {
	private Attribute linkedAttribute;

	private TextView mTitle;
	private TextView mValue;
	private SeekBar mSeekBar;	
	
	public Set<OnAttributeChangedListener> onAttributeChangedListeners;

	public AttributeReducer(Context context, Attribute linkedAttribute ) {
		super(context);
		this.linkedAttribute = linkedAttribute;
		onAttributeChangedListeners = new HashSet<OnAttributeChangedListener>();
		 
		int value = - linkedAttribute.getMod();
				
		mTitle = new TextView( context );
		mTitle.setText( linkedAttribute.getName() + ": " );
		mTitle.setTypeface( Typeface.DEFAULT_BOLD );
		mTitle.setLayoutParams( new LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT ) );
			
		mValue = new TextView( context );
		mValue.setText( "" + value );
		mValue.setPadding( 20, 0, 0, 0 );
		mValue.setLayoutParams( new LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT ) );
			
		mSeekBar = new SeekBar( context );
		mSeekBar.setMax( 5 );
		mSeekBar.setProgress( value );
		mSeekBar.setPadding( 20, 0, 0, 0 );
		LayoutParams lp = new LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT );
		lp.setMargins( 0, 0, 40, 0 );
		mSeekBar.setLayoutParams( lp );
		
		mSeekBar.setOnSeekBarChangeListener( this );
		
		setGravity( Gravity.CENTER_VERTICAL );
		setLayoutParams(new LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT ) );
		addView( mTitle );
		addView( mValue );
		addView( mSeekBar );		
	}
	
	public void addOnAttributeChangedListener( OnAttributeChangedListener listener ) {
		onAttributeChangedListeners.add( listener );
	}
	
	private void notifyAttributeChangedListeners( ) {
		Iterator<OnAttributeChangedListener> it = onAttributeChangedListeners.iterator();
		while ( it.hasNext() ) it.next().attributeChanged( linkedAttribute );
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if ( seekBar != this.mSeekBar ) return;
		// Do not reach (or exceed) zero in any attribute
		if ( progress >= linkedAttribute.getUnmodifiedValue() ) {
			seekBar.setProgress( linkedAttribute.getUnmodifiedValue() - 1 );
			return;
		}
		int value = - progress;
		mValue.setText( "" + value );
		linkedAttribute.setMod( value );		
		notifyAttributeChangedListeners( );
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
