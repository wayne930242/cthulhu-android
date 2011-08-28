package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;
import org.xfon.m.coc.Skills;

import android.content.Context;
import android.widget.TextView;

public class SkillEditor extends BaseSkillEditor {
	public SkillEditor(Context context) {
		super(context, R.layout.skill_editor ); 
	}
	
	public void initialize( Skills skills, Skill skill ) {
		super.initialize( skills, skill );
        setValue( skill.getValue() );
        setName( skill.getName() );
    }
		
	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.name );		
		tv.setText( title );
	}
	
	private void setValue( int value ) {
		NumberPicker picker = (NumberPicker)findViewById( R.id.value );		
		picker.setCurrent( value );
	}
	
}
