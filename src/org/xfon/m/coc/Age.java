package org.xfon.m.coc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.xfon.m.coc.gui.NumberPicker;

import android.app.Activity;

public class Age {
	private Activity activity;	
	private int ageNumberPickerId;
	
	private int baseAge;
	private int age;
	private Set<OnAgeChangedListener> ageChangedListeners;
	
	public static final int MAX_AGE = 90;
	
	public Age(Activity activity, int ageNumberPickerId) {
		super();
		this.activity = activity;		
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
		NumberPicker picker = (NumberPicker)activity.findViewById( ageNumberPickerId );
		picker.setCurrent( baseAge );
		picker.setRange( baseAge, MAX_AGE );
	}
	
	public int getBaseAge() {
		return baseAge;
	}
	
	public void setAge( int age ) {
		this.age = age;		
		NumberPicker picker = (NumberPicker)activity.findViewById( ageNumberPickerId );
		picker.setCurrent( age );		
		notifyAgeChangedListeners();
	}
	
	public int getAge() {
		return age;
	}
    
    public boolean isAgeNumberPicker( NumberPicker picker ) {
    	return picker.getId() == this.ageNumberPickerId;
    }
    
    public NumberPicker getAgeNumberPicker() {
    	return (NumberPicker)activity.findViewById( ageNumberPickerId );
    }
}
