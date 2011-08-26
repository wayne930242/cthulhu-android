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

public class BaseSkillEditor extends LinearLayout {
	private Skills skills;
	private ISkill skill;
	private Set<OnSkillChangedListener> onSkillChangedListeners;

	public BaseSkillEditor(Context context, int layoutId, final Skills skills, final ISkill skill) {
		super(context);
		this.skills = skills;
		this.skill = skill;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutId, this, true);
		if (!isEnabled()) setEnabled(true);

		onSkillChangedListeners = new HashSet<OnSkillChangedListener>();
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
			picker.setCurrent( sk.getValue() );
	       	picker.setRange( sk.getBaseValue(), 99 );
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

	public void addOnSkillChangedListener(OnSkillChangedListener listener) {
		onSkillChangedListeners.add(listener);
	}

	public void addOnSkillChangedListeners(Set<OnSkillChangedListener> listeners) {
		onSkillChangedListeners.addAll(listeners);
	}
	
	public void updateBaseValue() {
		NumberPicker picker = (NumberPicker) findViewById(R.id.value);
		if (picker != null) {			
			int baseValue = getSkill().getBaseValue();
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
		return skill == getSkill();
	}

	private void notifyOnSkillChangedListeners() {
		Iterator<OnSkillChangedListener> it = onSkillChangedListeners.iterator();
		while (it.hasNext()) it.next().skillChanged(skill);
	}

	protected Set<OnSkillChangedListener> getOnSkillChangedListeners() {
		return onSkillChangedListeners;
	}

	protected ISkill getSkill() {
		return skill;
	}
	
	protected Skills getSkills() {
		return skills;
	}
}
