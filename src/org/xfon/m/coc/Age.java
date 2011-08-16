package org.xfon.m.coc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.xfon.m.coc.gui.CustomNumberPicker;

import android.app.Activity;
import android.widget.TextView;

public class Age {
	private Activity activity;
	private int baseAgeTextViewId;
	private int ageNumberPickerId;
	
	private int baseAge;
	private int age;
	private Set<OnAgeChangedListener> ageChangedListeners;
	
	public static final int MAX_AGE = 90;
	
	public Age(Activity activity, int baseAgeTextViewId, int ageNumberPickerId) {
		super();
		this.activity = activity;
		this.baseAgeTextViewId = baseAgeTextViewId;
		this.ageNumberPickerId = ageNumberPickerId;
		baseAge = age = 0;
		ageChangedListeners = new HashSet<OnAgeChangedListener>();
	}
	
	public void addAgeChangedListener( OnAgeChangedListener listener ) {
		ageChangedListeners.add( listener );
	}
	
	private void notifyAgeChangedListeners() {
		Iterator<OnAgeChangedListener> it = ageChangedListeners.iterator();
		while ( it.hasNext() ) it.next().ageChanged( age );
	}
		
	public void setBaseAge( int baseAge ) {
		this.baseAge = baseAge;
		age = baseAge;
		setIntValue( baseAgeTextViewId, baseAge );
		CustomNumberPicker picker = (CustomNumberPicker)activity.findViewById( ageNumberPickerId );
		picker.setCurrent( baseAge );
	}
	
	public int getBaseAge() {
		return baseAge;
	}
	
	public void setAge( int age ) {
		this.age = age;
		notifyAgeChangedListeners();
		//CustomNumberPicker picker = (CustomNumberPicker)activity.findViewById( ageNumberPickerId );
		//picker.setCurrent( baseAge );		
		//notifyAgeChangedListeners();
	}
	
	public int getAge() {
		return age;
	}
	
    private void setIntValue( int id, int value ) {
    	((TextView)activity.findViewById( id )).setText( "" + value );
    }
    
    public boolean isAgeNumberPicker( CustomNumberPicker picker ) {
    	return picker.getId() == this.ageNumberPickerId;
    }
    
    public CustomNumberPicker getAgeNumberPicker() {
    	return (CustomNumberPicker)activity.findViewById( ageNumberPickerId );
    }
}
