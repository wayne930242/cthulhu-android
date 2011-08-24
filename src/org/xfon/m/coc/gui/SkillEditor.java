package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;

import android.content.Context;
import android.widget.TextView;

public class SkillEditor extends BaseSkillEditor {
	public SkillEditor(Context context) {
		super(context, -1, null); 
	}
	
	public SkillEditor(Context context, Skill skill ) {
		super(context, R.layout.skill_editor, skill);

        setValue( skill.getValue() );
        setName( skill.getName() );
        
        CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.value );
       	picker.setRange( skill.getBaseValue(), 99 );
       	picker.setSpeed( 150 );
       	picker.setWrap( false );
    }
		
	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.name );		
		tv.setText( title );
	}
	
	private void setValue( int value ) {
		CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.value );		
		picker.setCurrent( value );
	}
	
}
