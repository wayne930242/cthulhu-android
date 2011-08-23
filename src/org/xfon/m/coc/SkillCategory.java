package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.List;

public class SkillCategory {
	private String id;
	private String name;
	private int baseValue;		
	private boolean occupational;
	private List<Skill> skills;
	
	public SkillCategory( String id, String name, int baseValue ) {
		this.id = id;
		this.name = name;
		this.baseValue = baseValue;			
		this.occupational = false;
		skills = new ArrayList<Skill>();
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getBaseValue() {
		return baseValue;
	}

	public int getValue() {
		return 0;
	}

	public void setValue() {
		
	}

	public void setOccupational(boolean occupational) {		
		this.occupational = occupational;
	}

	public boolean isOccupational() {
		return occupational;
	}

	public boolean isCategory() {
		return true;
	}

	public List<Skill> getSkills() {
		return skills;
	}

}
