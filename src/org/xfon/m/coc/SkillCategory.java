package org.xfon.m.coc;

public class SkillCategory implements ISkill {
	private String name;
	private int baseValue;		
	private boolean occupational;
	
	public SkillCategory( String name, int baseValue ) {
		this.name = name;
		this.baseValue = baseValue;			
		this.occupational = false;
	}

	public String getName() {
		return name;
	}

	public int getBaseValue() {
		return baseValue;
	}
	
	public void setBaseValue( int baseValue ) {
		this.baseValue = baseValue;
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
		else if ( cat == this ) {
			return -1;
		}
		else {
			return name.compareTo( cat.getName() );
		}
	}		
}
