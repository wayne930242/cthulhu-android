package org.xfon.m.coc;


public class Skill implements ISkill {
	private String name;
	private int baseValue;
	private int value;
	private boolean occupational;
	private SkillCategory category;
	
	public Skill( String name, int baseValue ) {
		this.name = name;
		this.baseValue = baseValue;
		this.value = baseValue;
		this.occupational = false;
		this.category = null;
	}
	
	public Skill( SkillCategory category ) {
		this.name = "";
		this.baseValue = category.getBaseValue();
		this.value = this.baseValue;
		this.occupational = false;
		this.category = category;
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
		return occupational || ( category != null && category.isOccupational() );
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
