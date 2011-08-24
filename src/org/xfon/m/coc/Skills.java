package org.xfon.m.coc;

import java.util.Collections;
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
}
