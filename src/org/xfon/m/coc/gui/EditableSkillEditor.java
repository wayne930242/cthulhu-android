package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;
import org.xfon.m.coc.Skills;

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

	private TextView tv;
	private EditText edit;
	private CheckBox lockBox;
		
	public EditableSkillEditor(Context context) {
		super(context, -1, null, null);
	}
	
	public EditableSkillEditor(Context context, final Skills skills, final Skill skill ) {
		super(context, R.layout.editable_skill_editor, skills, skill);		
                
        setValue( skill.getValue() );
        setName( skill.getName() );
        
        tv = (TextView)this.findViewById( R.id.name );
		edit = (EditText)this.findViewById( R.id.editableName );
		lockBox = (CheckBox)this.findViewById( R.id.isLocked );
        
		final Button btn = (Button)this.findViewById( R.id.btnRemoveSkill );
		lockBox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if ( isChecked ) lock();				
				else unlock();								
			}
		});
       			
		btn.setOnClickListener( this );
	}
	
	public void lock() {        					
		String skillName = edit.getText().toString();
		((Skill)mSkill).setName( skillName );
		tv.setText( skillName );
		tv.setVisibility( View.VISIBLE );
		edit.setVisibility( View.GONE );		
	}
	
	public void unlock() {
		edit.setText( mSkill.getName() );
		tv.setVisibility( View.GONE );
		edit.setVisibility( View.VISIBLE );		
	}

	private void setName( String title ) {
      	final TextView tv = (TextView)this.findViewById( R.id.name );
		final EditText edit = (EditText)this.findViewById( R.id.editableName );
		tv.setText( title );
		edit.setText( title );
	}
	
	private void setValue( int value ) {
		NumberPicker picker = (NumberPicker)findViewById( R.id.value );		
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
