package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;
import org.xfon.m.coc.SkillCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class SkillCategoryEditor extends LinearLayout implements OnClickListener {
	private Context context;
	private Button btnAddSkill;
	private SkillCategory category;
	int childrenCount;
	
	public SkillCategoryEditor(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SkillCategoryEditor(Context context, SkillCategory category ) {
		super(context);
		this.context = context;
		this.category = category;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.skill_category_editor, this, true);
        if ( !isEnabled() ) setEnabled( true );
                
        setName( category.getName() );        
        btnAddSkill = (Button)findViewById( R.id.btnAddSkill );
       	btnAddSkill.setOnClickListener( this );
       	
       	childrenCount = 0;
	}
	

	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.name );		
		tv.setText( title );
	}
	
	@Override
	public void onClick(View v) {
		if ( v != btnAddSkill ) return;
		int index = getIndexInParent();
		Skill newSkill = new Skill( category, childrenCount++ );		
		EditableSkillEditor editor = new EditableSkillEditor( context, newSkill );		
		TableLayout table = (TableLayout)this.getParent();
		table.addView( editor, index );		
	}
	
	private int getIndexInParent() {		
		ViewGroup parent = (ViewGroup)this.getParent();
		int count = parent.getChildCount();
		for ( int i = 0; i < count; i++ ) {
			View child = parent.getChildAt( 0 );
			if ( child == (View)this ) {				
				return i;
			}
		}
		return -1;
	}
	
 
	
}