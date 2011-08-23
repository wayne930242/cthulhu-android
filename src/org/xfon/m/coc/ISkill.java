package org.xfon.m.coc;

import java.util.List;

public interface ISkill {
	public String getName();
	public String getId();
	public int getBaseValue();
	public int getValue();
	public void setValue();
	public void setOccupational( boolean occupational );
	public boolean isOccupational();
	public boolean isCategory();
	public List<ISkill> getSkills();
}
