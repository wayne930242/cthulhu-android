package org.xfon.m.coc.gui;

import org.xfon.m.coc.Skill;
import org.xfon.m.coc.SkillCategory;
import org.xfon.m.coc.Skills;

import android.content.Context;

public class SkillEditorFactory {

	public SkillEditor newSkillEditor( Context context, Skills skills, Skill skill ) {
		SkillEditor editor = new SkillEditor( context );
		editor.initialize(skills, skill);
		return editor;
	}
	
	public SkillCategoryEditor newSkillCategoryEditor( Context context, Skills skills, SkillCategory cat ) {
		SkillCategoryEditor editor = new SkillCategoryEditor( context, this );
		editor.initialize(skills, cat);
		return editor;
	}
	
	public EditableSkillEditor newEditableSkillEditor( Context context, Skills skills, Skill skill ) {
		EditableSkillEditor editor = new EditableSkillEditor( context );
		editor.initialize(skills, skill);
		return editor;
	}
}
