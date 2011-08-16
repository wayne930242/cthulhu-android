package org.xfon.m.coc;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class Skill {
	private String id;
	private String name;
	private int base;
	private int value;
	
	public Skill( String id, String name, int base ) {
		this.id = id;
		this.name = name;
		this.base = base;
	}
	
	public void addToView( Context context, ViewGroup vg ) {
		vg.addView( new SkillEditor( context ) );
		vg.requestLayout();
	}
	
	private class SkillEditor extends TableRow {
		private Context context;
		
		public SkillEditor(Context context) {
			super(context);
			this.context = context;
			
			TextView title = new TextView( context );
			title.setText( name );			
		}
	}
		
}
