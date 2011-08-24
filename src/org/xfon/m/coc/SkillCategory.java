package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.List;

public class SkillCategory implements ISkill {
	private String name;
	private int baseValue;		
	private boolean occupational;
	private List<Skill> skills;
	
	public SkillCategory( String name, int baseValue ) {
		this.name = name;
		this.baseValue = baseValue;			
		this.occupational = false;
		skills = new ArrayList<Skill>();
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

	@Override
	public int compareTo(ISkill another) {
		if ( another.isCategory() ) {
			return name.compareTo( another.getName() );
		}
		
		Skill sk = (Skill)another;
		SkillCategory cat = sk.getCategory();
		if ( cat == null ) {
			return name.compareTo( sk.getName() );
		}
		else {
			return name.compareTo( cat.getName() );
		}
	}		
}
