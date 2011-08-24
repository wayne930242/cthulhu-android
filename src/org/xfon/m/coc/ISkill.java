package org.xfon.m.coc;

public interface ISkill extends Comparable<ISkill> {
	public String getName();
	public boolean isCategory();
	public void setOccupational( boolean isOccupational );
}
