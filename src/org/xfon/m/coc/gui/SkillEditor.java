package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SkillEditor extends LinearLayout {
	private Context context;
	private Skill skill;
	private boolean isEditable;
	final private static int NAME_INDEX = 2;
	
	public SkillEditor(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SkillEditor(Context context, Skill skill ) {
		super(context);
		this.context = context;
		this.skill = skill;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.skill_editor, this, true);
        if ( !isEnabled() ) setEnabled( true );
                
        setValue( skill.getValue() );
        setName( skill.getName() );
        
        CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.skillValue );
       	picker.setRange( skill.getBaseValue(), 99 );
       	picker.setSpeed( 150 );
       	picker.setWrap( false );
       	
       	isEditable = false;
	}
	
	public void setEditable( boolean isEditable ) {
		if ( isEditable == this.isEditable ) return;
		final TextView tv = (TextView)this.findViewById( R.id.skillName );
		final EditText edit = (EditText)this.findViewById( R.id.skillEditableName );

		if ( this.isEditable == false ) {
			edit.setText( skill.getName() );
			tv.setVisibility( View.GONE );
			edit.setVisibility( View.VISIBLE );
		}
		else {
			String skillName = edit.getText().toString();
			skill.setName( skillName );
			tv.setText( skillName );
			tv.setVisibility( View.VISIBLE );
			edit.setVisibility( View.GONE );
		}
		this.isEditable = isEditable;
	}
	

	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.skillName );		
		tv.setText( title );
	}
	
	private void setValue( int value ) {
		CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.skillValue );		
		picker.setCurrent( value );
	}
	
}
