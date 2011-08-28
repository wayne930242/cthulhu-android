package org.xfon.m.coc.gui;

import java.util.ArrayList;
import java.util.List;

import org.xfon.m.coc.Skill;
import org.xfon.m.coc.SkillCategory;
import org.xfon.m.coc.Skills;

import android.content.Context;
import android.util.Log;

public class SkillEditorFactory {

	private List<BaseSkillEditor> editors = new ArrayList<BaseSkillEditor>();
	private Context context;
	private Class[] sigContext;
	private Class[] sigContextThis;
	private Object[] argsContext;
	private Object[] argsContextThis;
	
	public SkillEditorFactory( Context context ) {
		this.context = context;
		sigContext = new Class[] { Context.class };
		sigContextThis = new Class[] { Context.class, SkillEditorFactory.class };
		argsContext = new Object[] { context };
		argsContextThis = new Object[] { context, this };
		
	}
	
	private synchronized <T extends BaseSkillEditor> T findAvailableEditor( Class<T> cl, Class[] cnstrSignature, Object[] cnstrArgs  ) {
		Log.i( getClass().getName(), "Requesting editor: " + cl.getName() );
		for ( BaseSkillEditor editor: editors ) {
			if ( editor.isAvailable() && cl.isAssignableFrom( editor.getClass() ) ) {
				//Log.i( getClass().getName(), "Returning existing: " + editor.toString() );
				return (T)editor;
			}
		}
		T ret = null;
		try {
			ret = cl.getConstructor( cnstrSignature ).newInstance( cnstrArgs );
			//Log.i( getClass().getName(), "Adding new: " + ret.toString() );
			editors.add( ret );
		}
		catch ( Exception e ) {
			Log.e( getClass().getName(), "findAvailableEditor error instantiating: " + cl.getName() );
		}
		return ret;			
	}
	
	public SkillEditor newSkillEditor( Skills skills, Skill skill ) {
		SkillEditor editor = findAvailableEditor( SkillEditor.class, sigContext, argsContext ); 		
		editor.initialize(skills, skill);
		return editor;
	}
	
	public SkillCategoryEditor newSkillCategoryEditor( Context context, Skills skills, SkillCategory cat ) {
		SkillCategoryEditor editor = findAvailableEditor( SkillCategoryEditor.class, sigContextThis, argsContextThis );
		editor.initialize(skills, cat);
		return editor;
	}
	
	public EditableSkillEditor newEditableSkillEditor( Context context, Skills skills, Skill skill ) {
		EditableSkillEditor editor = findAvailableEditor( EditableSkillEditor.class, sigContext, argsContext );
		editor.initialize(skills, skill);
		return editor;
	}
	
	public synchronized void resetEditors() {
		for ( BaseSkillEditor editor: editors ) {
			editor.reset();
		}
	}
}
