package org.xfon.m.coc;

import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class Skill {
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

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public int getBaseValue() {
		return baseValue;
	}

	public int getValue() {
		return value;
	}

	public void setValue() {
		this.value = value;
	}

	
	public void setOccupational(boolean occupational) {
		this.occupational = occupational;		
	}

	public boolean isOccupational() {
		return occupational;
	}

	public boolean isCategory() {
		return false;
	}


	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}		
}
