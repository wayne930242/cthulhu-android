package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;
import org.xfon.m.coc.SkillCategory;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class SkillCategoryEditor extends BaseSkillEditor implements OnClickListener {
	private Button btnAddSkill;
	
	public SkillCategoryEditor(Context context) {
		super(context, -1, null);
	}
	
	public SkillCategoryEditor(Context context, SkillCategory category ) {
		super(context, R.layout.skill_category_editor, category);
		                
        setName( category.getName() );        
        btnAddSkill = (Button)findViewById( R.id.btnAddSkill );
       	btnAddSkill.setOnClickListener( this );
	}
	

	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.name );		
		tv.setText( title );
	}
	
	@Override
	public void onClick(View v) {
		if ( v != btnAddSkill ) return;
		int index = getIndexInParent();
		Skill newSkill = new Skill( (SkillCategory)getSkill() );		
		EditableSkillEditor editor = new EditableSkillEditor( getContext(), newSkill );		
		TableLayout table = (TableLayout)this.getParent();
		table.addView( editor, index + 1 );
		editor.setEditFocus();
	}
	
	private int getIndexInParent() {		
		ViewGroup parent = (ViewGroup)this.getParent();
		int count = parent.getChildCount();
		for ( int i = 0; i < count; i++ ) {
			View child = parent.getChildAt( i );
			if ( child == (View)this ) {				
				return i;
			}
		}
		return -1;
	}
	
 
	
}
