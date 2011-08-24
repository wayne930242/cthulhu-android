package org.xfon.m.coc.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.xfon.m.coc.ISkill;
import org.xfon.m.coc.OnSkillChangedListener;
import org.xfon.m.coc.R;
import org.xfon.m.coc.gui.CustomNumberPicker.OnChangedListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class BaseSkillEditor extends LinearLayout {
	private ISkill skill; 
	private Set<OnSkillChangedListener> onSkillChangedListeners;
	
	public BaseSkillEditor(Context context, int layoutId, ISkill skill) {
		super(context);
		this.skill = skill;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId, this, true);
        if ( !isEnabled() ) setEnabled( true );
		
		onSkillChangedListeners = new HashSet<OnSkillChangedListener>();
		CheckBox cb = (CheckBox)findViewById( R.id.isOccupational );
		if ( cb != null ) cb.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				notifyOnSkillChangedListeners();
			}
		} );	
		CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.value );
		if ( picker != null ) picker.setOnChangeListener( new OnChangedListener() {
			
			@Override
			public void onChanged(CustomNumberPicker picker, int oldVal, int newVal) {
				notifyOnSkillChangedListeners();
			}
		});
	}
	
	public void addOnSkillChangedListener( OnSkillChangedListener listener ) {
		onSkillChangedListeners.add( listener );
	}
	
	private void notifyOnSkillChangedListeners() {
		Iterator<OnSkillChangedListener> it = onSkillChangedListeners.iterator();
		while ( it.hasNext() ) it.next().skillChanged( skill );
	}
	
	protected ISkill getSkill() {
		return skill;
	}
}
