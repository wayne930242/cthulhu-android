package org.xfon.m.coc;

import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class Skill implements ISkill {
	private String id;
	private String name;
	private int baseValue;
	private int value;
	private boolean occupational;
	private SkillCategory category;
	
	public Skill( String id, String name, int baseValue ) {
		this.id = id;
		this.name = name;
		this.baseValue = baseValue;
		this.value = baseValue;
		this.occupational = false;
		this.category = null;
	}
	
	public Skill( SkillCategory category, int index ) {
		this.id = category.getId() + "_" + index;
		this.name = " -NEW ";
		this.baseValue = category.getBaseValue();
		this.value = this.baseValue;
		this.occupational = false;
		this.category = category;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	@Override
	public int getBaseValue() {
		return baseValue;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue() {
		this.value = value;
	}

	@Override
	public void setOccupational(boolean occupational) {
		this.occupational = occupational;		
	}

	@Override
	public boolean isOccupational() {
		return occupational;
	}

	@Override
	public boolean isCategory() {
		return false;
	}

	@Override
	public List<ISkill> getSkills() {
		return null;
	}

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}		
}
