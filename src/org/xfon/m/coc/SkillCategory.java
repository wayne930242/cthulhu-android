package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.List;

public class SkillCategory implements ISkill {
	private String id;
	private String name;
	private int baseValue;		
	private boolean occupational;
	private List<ISkill> skills;
	
	public SkillCategory( String id, String name, int baseValue ) {
		this.id = id;
		this.name = name;
		this.baseValue = baseValue;			
		this.occupational = false;
		skills = new ArrayList<ISkill>();
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBaseValue() {
		return baseValue;
	}

	@Override
	public int getValue() {
		return 0;
	}

	@Override
	public void setValue() {
		
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
		return true;
	}

	@Override
	public List<ISkill> getSkills() {
		return skills;
	}

}
