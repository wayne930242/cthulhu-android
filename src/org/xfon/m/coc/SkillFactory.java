package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.List;

public class SkillFactory {
	public static List<ISkill> getCoreSkills( Investigator investigator ) {
		List<ISkill> skills = new ArrayList<ISkill>();

		skills.add(new Skill("Accounting", 10));
		skills.add(new Skill("Anthropology"));
		skills.add(new Skill("Archaeology"));
		skills.add(new SkillCategory("Art", 5));
		skills.add(new Skill("Astronomy"));
		skills.add(new Skill("Bargain", 5));
		skills.add(new Skill("Biology"));
		skills.add(new Skill("Chemistry"));
		skills.add(new Skill("Climb", 40));
		skills.add(new Skill("Computer User"));
		skills.add(new Skill("Conceal", 15));
		skills.add(new SkillCategory("Craft", 5));
		skills.add(new Skill("Credit Rating", 15));
		skills.add(new Skill("Cthulhu Mythos", 0));
		skills.add(new Skill("Disguise"));
		// skills.add(new Skill("Dodge", TODO)); 
		skills.add(new Skill("Drive Auto", 20));
		skills.add(new Skill("Electr. Repair", 10));
		skills.add(new Skill("Electronics"));
		skills.add(new Skill("Fast Talk", 5));
		skills.add(new Skill("First Aid", 30));
		skills.add(new Skill("Geology"));
		skills.add(new Skill("Hide", 10));
		
		skills.add(new Skill("History", 20));
		skills.add(new Skill("Jump", 25));
		skills.add(new Skill("Law", 5));
		skills.add(new Skill("Library Use", 25));
		skills.add(new Skill("Listen", 25));
		skills.add(new Skill("Locksmith"));
		skills.add(new Skill("Martial Arts"));
		skills.add(new Skill("Mech. Repair", 20));
		skills.add(new Skill("Medicine", 5));
		skills.add(new Skill("Natural History", 10));
		skills.add(new Skill("Navigate", 10));
		skills.add(new Skill("Occult", 5));
		skills.add(new Skill("Operate Hv. Mch."));
		skills.add(new SkillCategory("Other Language",1));
		//skills.add(new Skill("Own Language", TODO ));
		skills.add(new Skill("Persuade", 15));
		skills.add(new Skill("Pharmacy"));
		skills.add(new Skill("Photography", 10));
		skills.add(new Skill("Physics"));
		skills.add(new SkillCategory("Pilot", 1));
		skills.add(new Skill("Psychoanalysis"));
		
		skills.add(new Skill("Psychology", 5));
		skills.add(new Skill("Ride", 5));
		skills.add(new Skill("Sneak", 10));
		skills.add(new Skill("Spot Hidden", 25));
		skills.add(new Skill("Swim", 25));
		skills.add(new Skill("Throw", 25));
		skills.add(new Skill("Track", 10));
		
		skills.add(new Skill("Firearm: Handgun", 20 ) );
		skills.add(new Skill("Firearm: Machine Gun", 15 ) );
		skills.add(new Skill("Firearm: Rifle", 25 ) );
		skills.add(new Skill("Firearm: Shotgun", 30 ) );
		skills.add(new Skill("Firearm: SMG", 15 ) );
		
		skills.add(new Skill("Weapon: Fist", 50 ) );
		skills.add(new Skill("Weapon: Grapple", 25 ) );
		skills.add(new Skill("Weapon: Head", 10 ) );
		skills.add(new Skill("Weapon: Kick", 25 ) );
		
		// skills.add(new SkillCategory( "Other", 1 ) ); TODO
		
		return skills;
	}
}
