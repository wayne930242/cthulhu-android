package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class EditableSkillEditor extends BaseSkillEditor implements OnClickListener {

	public EditableSkillEditor(Context context) {
		super(context, -1, null);
	}
	
	public EditableSkillEditor(Context context, final Skill skill ) {
		super(context, R.layout.editable_skill_editor, skill);		
                
        setValue( skill.getValue() );
        setName( skill.getName() );
        
        CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.value );
       	picker.setRange( skill.getBaseValue(), 99 );
       	picker.setSpeed( 150 );
       	picker.setWrap( false );
       	
       	final TextView tv = (TextView)this.findViewById( R.id.name );
		final EditText edit = (EditText)this.findViewById( R.id.editableName );
		final Button btn = (Button)this.findViewById( R.id.btnRemoveSkill );
		final CheckBox lockBox = (CheckBox)this.findViewById( R.id.isLocked );				
		lockBox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if ( isChecked ) {
					String skillName = edit.getText().toString();
					skill.setName( skillName );
					tv.setText( skillName );
					tv.setVisibility( View.VISIBLE );
					edit.setVisibility( View.GONE );
				}
				else {
					edit.setText( skill.getName() );
					tv.setVisibility( View.GONE );
					edit.setVisibility( View.VISIBLE );
				}
				
			}
		});
       			
		btn.setOnClickListener( this );
	}

	private void setName( String title ) {
      	final TextView tv = (TextView)this.findViewById( R.id.name );
		final EditText edit = (EditText)this.findViewById( R.id.editableName );
		tv.setText( title );
		edit.setText( title );
	}
	
	private void setValue( int value ) {
		CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.value );		
		picker.setCurrent( value );
	}
	
	public void setEditFocus() {
		final EditText edit = (EditText)this.findViewById( R.id.editableName );
		edit.requestFocus();
	}


	@Override
	public void onClick(View v) {			
		TableLayout table = (TableLayout)this.getParent();
		table.removeView( this );
	}
	
}
