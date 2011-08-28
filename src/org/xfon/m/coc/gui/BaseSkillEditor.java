package org.xfon.m.coc.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.xfon.m.coc.ISkill;
import org.xfon.m.coc.OnSkillChangedListener;
import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;
import org.xfon.m.coc.Skills;
import org.xfon.m.coc.gui.NumberPicker.OnChangedListener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseSkillEditor extends LinearLayout {
	private Context context;
	protected Skills mSkills;
	protected ISkill mSkill;
	private Set<OnSkillChangedListener> onSkillChangedListeners;
	private boolean mAvailable;

	public BaseSkillEditor(Context context, int layoutId ) {
		super(context);		
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutId, this, true);
		if (!isEnabled()) setEnabled(true);
		
		this.mAvailable = true;
	}
	
	public void initialize( final Skills skills, final ISkill skill ) {
		this.mSkills = skills;
		this.mSkill = skill;
		this.mAvailable = false;

		onSkillChangedListeners = new HashSet<OnSkillChangedListener>();
		
		TextView tv = (TextView) findViewById(R.id.name);
		tv.setText( skill.getName() );
		
		CheckBox cb = (CheckBox) findViewById(R.id.isOccupational);
		if (cb != null) {
			cb.setChecked( skill.isOccupational() );			
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					skill.setOccupational(isChecked);
					notifyOnSkillChangedListeners();
				}
			});
		}
		NumberPicker picker = (NumberPicker) findViewById(R.id.value);
		if (picker != null) {
			final Skill sk = (Skill)skill;			
	       	picker.setRangeAndCurrent( sk.getBaseValue(), 99, sk.getValue() );
	       	picker.setSpeed( 150 );
	       	picker.setWrap( false );	       			
			picker.setOnChangeListener(new OnChangedListener() {

				@Override
				public void onChanged(NumberPicker picker, int oldVal, int newVal) {
					//Log.i( "BaseSkillEditor.picker.onChange", "Skill: " + sk.getValue() + ", oldVal: " + oldVal + ", newVal: " + newVal );
					if (oldVal == newVal) return;
					if (!skill.isCategory()) {
						Skill sk = (Skill) skill;
						sk.setValue(newVal);
					}
					notifyOnSkillChangedListeners();
				}
			});
		}
	}
	
	public void reset() {
		// Just ignore this for now, as the editor will be removed anyway
		mAvailable = true;
	}

	public void addOnSkillChangedListener(OnSkillChangedListener listener) {
		onSkillChangedListeners.add(listener);
	}

	public void addOnSkillChangedListeners(Set<OnSkillChangedListener> listeners) {
		onSkillChangedListeners.addAll(listeners);
	}
	
	public void updateBaseValue() {
		NumberPicker picker = (NumberPicker) findViewById(R.id.value);
		if (picker != null) {			
			int baseValue = mSkill.getBaseValue();
			int currentValue = picker.getCurrent();
	       	//Log.i( "BaseSkillEditor.updateBaseValue", " - BEFORE: " + picker.getCurrent() );
			picker.setRange( baseValue, 99 );
	       	//Log.i( "BaseSkillEditor.updateBaseValue", " - AFTER RANGE: " + picker.getCurrent() );
	       	if ( currentValue < baseValue ) picker.setCurrent( baseValue );
	       	else picker.setCurrent( currentValue );
	       	//Log.i( "BaseSkillEditor.updateBaseValue", " - AFTER: " + picker.getCurrent() );
	       	notifyOnSkillChangedListeners();
		}
	}
	
	public boolean isEditorFor( ISkill skill ) {
		return skill == mSkill;
	}

	private void notifyOnSkillChangedListeners() {
		Iterator<OnSkillChangedListener> it = onSkillChangedListeners.iterator();
		while (it.hasNext()) it.next().skillChanged(mSkill);
	}

	protected Set<OnSkillChangedListener> getOnSkillChangedListeners() {
		return onSkillChangedListeners;
	}
	
	public boolean isAvailable() {
		return mAvailable;
	}
}
