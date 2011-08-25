package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Skills {
	private List<ISkill> skills;
	
	public Skills( List<ISkill> skills ) {
		this.skills = skills;
	}	
	
	public List<ISkill> list() {
		return skills;
	}
	
	public int getOccupationalPoints() {
		int sum = 0;
		for ( ISkill skill: skills ) {			
			if ( skill.isCategory() ) {
				SkillCategory cat = (SkillCategory)skill;
				if ( !cat.isOccupational() ) continue;
				for ( Skill sk: cat.getSkills() ) {
					sum += sk.getValue() - sk.getBaseValue();
				}
				continue;
			}
			Skill sk = (Skill)skill;
			if ( sk.getCategory() != null ) continue; // don't count twice
			if ( !sk.isOccupational()) continue;
			sum += sk.getValue() - sk.getBaseValue();
		}
		return sum;
	}
	
	public int getPersonalPoints() {
		int sum = 0;
		for ( ISkill skill: skills ) {
			if ( skill.isCategory() ) {
				SkillCategory cat = (SkillCategory)skill;
				if ( cat.isOccupational() ) continue;
				for ( Skill sk: cat.getSkills() ) {
					sum += sk.getValue() - sk.getBaseValue();
				}
				continue;
			}
			Skill sk = (Skill)skill;
			if ( sk.getCategory() != null ) continue; // don't count twice
			if ( sk.isOccupational()) continue;
			sum += sk.getValue() - sk.getBaseValue();
		}
		return sum;
	}
	
	public int getTotalPoints() {
		int sum = 0;
		for ( ISkill skill: skills ) {
			if ( skill.isCategory() ) {
				SkillCategory cat = (SkillCategory)skill;				
				for ( Skill sk: cat.getSkills() ) {
					sum += sk.getValue() - sk.getBaseValue();
				}
				continue;
			}
			Skill sk = (Skill)skill;			
			if ( sk.getCategory() != null ) continue; // don't count twice
			sum += sk.getValue() - sk.getBaseValue();
		}
		return sum;
	}
	
	public Skills sort() {
		Collections.sort( skills );
		return this;
	}
	
	public Skills sortByName() {
		Collections.sort(skills, new Comparator<ISkill>() {

			@Override
			public int compare(ISkill obj1, ISkill obj2) {
				return obj1.getName().compareTo( obj2.getName() );
			}
			
		});
		return this;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public ISkill findSkill( String name ) {
		// TODO: Make sure skills are sorted by NAME		
		int pos = Collections.binarySearch( skills, (Object)name, new Comparator<Object>() {

			@Override
			public int compare(Object object1, Object object2) {
				String name;
				ISkill skill;
				
				if ( object1 instanceof String ) {
					name = (String)object1;
					skill = (ISkill)object2;
				}
				else {
					name = (String)object2;
					skill = (ISkill)object1;
				}
				return name.compareTo( skill.getName() );
			}			
		});
		if ( pos < 0 ) return null;
		return skills.get( pos );
	}
}
