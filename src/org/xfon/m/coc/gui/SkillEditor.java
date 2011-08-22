package org.xfon.m.coc.gui;

import org.xfon.m.coc.ISkill;
import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SkillEditor extends LinearLayout implements OnClickListener {	
	private Button btnAddSkill;
	private ISkill skill;	
	
	public SkillEditor(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SkillEditor(Context context, ISkill skill ) {
		super(context);
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
        
        btnAddSkill = (Button)findViewById( R.id.skillBtnAddSkill );
        if ( skill.isCategory() ) {
        	btnAddSkill.setOnClickListener( this );
        }
        else {
        	btnAddSkill.setVisibility( View.INVISIBLE );
        }
	}
	

	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.skillName );		
		tv.setText( title );
	}
	
	private void setValue( int value ) {
		CustomNumberPicker picker = (CustomNumberPicker)findViewById( R.id.skillValue );		
		picker.setCurrent( value );
	}

	@Override
	public void onClick(View v) {
		if ( v != btnAddSkill ) return;
	}
	
 
	
}
