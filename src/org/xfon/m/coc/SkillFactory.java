package org.xfon.m.coc;

import java.util.ArrayList;
import java.util.List;

public class SkillFactory {
	public static List<ISkill> getCoreSkills( Investigator investigator ) {
		List<ISkill> skills = new ArrayList<ISkill>();

		skills.add(new Skill("Accounting", 10));
		skills.add(new Skill("Anthropology", 1));
		skills.add(new Skill("Archaeology", 1));
		skills.add(new SkillCategory("Art", 5));
		skills.add(new Skill("Astronomy", 1));
		skills.add(new Skill("Bargain", 5));
		skills.add(new Skill("Biology", 1));
		skills.add(new Skill("Chemistry", 1));
		skills.add(new Skill("Climb", 40));
		skills.add(new Skill("Computer User", 1));
		skills.add(new Skill("Conceal", 15));
		skills.add(new SkillCategory("Craft", 5));
		return skills;
	}
}
