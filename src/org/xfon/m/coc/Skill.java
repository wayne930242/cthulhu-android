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
	
	public Skill( String name ) {
		this( name, 1 );
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
	
	public boolean inSameCategoryAs( Skill skill ) {
		if ( category == null && skill.getCategory() == null ) return true;
		if ( category == null || skill.getCategory() == null ) return false;
		return category.getName().equals( skill.getCategory().getName() );
	}	

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

	@Override
	public int compareTo(ISkill another) {
		if ( another.isCategory() ) {
			return name.compareTo( another.getName() );
		}
		
		Skill sk = (Skill)another;
		SkillCategory cat = sk.getCategory();
		if ( this.inSameCategoryAs( sk ) ) {
			return name.compareTo( sk.getName() );
		}
		else {
			if ( cat == null ) {
				return getCategory().getName().compareTo( sk.getName() );
			}
			else {
				return name.compareTo( cat.getName() );
			}
		}
	}		
}
