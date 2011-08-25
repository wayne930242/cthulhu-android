package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Skills {
	private List<ISkill> skills;
	
	private Skills() {
		skills = new ArrayList<ISkill>();
	}
	
	public static Skills defaultSkills( Investigator investigator ) {
		Skills s = new Skills();		
		
		s.skills.add(new Skill("Accounting", 10));
		s.skills.add(new Skill("Anthropology"));
		s.skills.add(new Skill("Archaeology"));
		s.skills.add(new SkillCategory("Art", 5));
		s.skills.add(new Skill("Astronomy"));
		s.skills.add(new Skill("Bargain", 5));
		s.skills.add(new Skill("Biology"));
		s.skills.add(new Skill("Chemistry"));
		s.skills.add(new Skill("Climb", 40));
		s.skills.add(new Skill("Computer User"));
		s.skills.add(new Skill("Conceal", 15));
		s.skills.add(new SkillCategory("Craft", 5));
		s.skills.add(new Skill("Credit Rating", 15));
		s.skills.add(new Skill("Cthulhu Mythos", 0));
		s.skills.add(new Skill("Disguise"));
		// s.skills.add(new Skill("Dodge", TODO)); 
		s.skills.add(new Skill("Drive Auto", 20));
		s.skills.add(new Skill("Electr. Repair", 10));
		s.skills.add(new Skill("Electronics"));
		s.skills.add(new Skill("Fast Talk", 5));
		s.skills.add(new Skill("First Aid", 30));
		s.skills.add(new Skill("Geology"));
		s.skills.add(new Skill("Hide", 10));
		
		s.skills.add(new Skill("History", 20));
		s.skills.add(new Skill("Jump", 25));
		s.skills.add(new Skill("Law", 5));
		s.skills.add(new Skill("Library Use", 25));
		s.skills.add(new Skill("Listen", 25));
		s.skills.add(new Skill("Locksmith"));
		s.skills.add(new Skill("Martial Arts"));
		s.skills.add(new Skill("Mech. Repair", 20));
		s.skills.add(new Skill("Medicine", 5));
		s.skills.add(new Skill("Natural History", 10));
		s.skills.add(new Skill("Navigate", 10));
		s.skills.add(new Skill("Occult", 5));
		s.skills.add(new Skill("Operate Hv. Mch."));
		s.skills.add(new SkillCategory("Other Language",1));
		//s.skills.add(new Skill("Own Language", TODO ));
		s.skills.add(new Skill("Persuade", 15));
		s.skills.add(new Skill("Pharmacy"));
		s.skills.add(new Skill("Photography", 10));
		s.skills.add(new Skill("Physics"));
		s.skills.add(new SkillCategory("Pilot", 1));
		s.skills.add(new Skill("Psychoanalysis"));
		
		s.skills.add(new Skill("Psychology", 5));
		s.skills.add(new Skill("Ride", 5));
		s.skills.add(new Skill("Sneak", 10));
		s.skills.add(new Skill("Spot Hidden", 25));
		s.skills.add(new Skill("Swim", 25));
		s.skills.add(new Skill("Throw", 25));
		s.skills.add(new Skill("Track", 10));
		
		s.skills.add(new Skill("Firearm: Handgun", 20 ) );
		s.skills.add(new Skill("Firearm: Machine Gun", 15 ) );
		s.skills.add(new Skill("Firearm: Rifle", 25 ) );
		s.skills.add(new Skill("Firearm: Shotgun", 30 ) );
		s.skills.add(new Skill("Firearm: SMG", 15 ) );
		
		s.skills.add(new Skill("Weapon: Fist", 50 ) );
		s.skills.add(new Skill("Weapon: Grapple", 25 ) );
		s.skills.add(new Skill("Weapon: Head", 10 ) );
		s.skills.add(new Skill("Weapon: Kick", 25 ) );
		
		return s;
	}	
	
	public static Skills emptySkills() {
		return new Skills();
	}
	
	public void add( ISkill skill ) {
		skills.add( skill );
	}
	
	public Skill newSkill( SkillCategory category ) {
		Skill skill = new Skill( category );
		add( skill );
		return skill;		
	}
	
	public List<ISkill> list() {
		return Collections.unmodifiableList( skills );
	}
	
	public int getOccupationalPoints() {
		int sum = 0;
		for ( ISkill skill: skills ) {			
			if ( skill.isCategory() ) continue;
			Skill sk = (Skill)skill;
			if ( !sk.isOccupational()) continue;
			sum += sk.getValue() - sk.getBaseValue();
		}
		return sum;
	}
	
	public int getPersonalPoints() {
		int sum = 0;
		for ( ISkill skill: skills ) {
			if ( skill.isCategory() ) continue;
			Skill sk = (Skill)skill;
			if ( sk.isOccupational()) continue;
			sum += sk.getValue() - sk.getBaseValue();
		}
		return sum;
	}
	
	public int getTotalPoints() {
		int sum = 0;
		for ( ISkill skill: skills ) {
			if ( skill.isCategory() ) continue;
			Skill sk = (Skill)skill;			
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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for ( ISkill skill: list() ) {
			String type = skill.isCategory() ? " (C) " : " (S) ";
			String occ = skill.isOccupational() ? " [O] " : " [ ] ";
			buffer.append( type + occ + skill.getName() + ": " );
			if ( skill.isCategory() ) {
				SkillCategory cat = (SkillCategory)skill;
				buffer.append( cat.getBaseValue() );				
			}
			else {
				Skill sk = (Skill)skill;
				buffer.append( sk.getBaseValue() + " --> " + sk.getValue() );
			}
			buffer.append( "\n" );
		}
		return buffer.toString();
	}
}
