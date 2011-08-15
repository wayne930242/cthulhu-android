package org.xfon.m.coc;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class StrengthDamageBonus {
	private SortedMap<Integer,String> strDamBonus;

	public StrengthDamageBonus() {
		strDamBonus = new TreeMap<Integer,String>();
        strDamBonus.put( 12, "-1d6" );
        strDamBonus.put( 16, "-1d4" );
        strDamBonus.put( 24, "+0" );
        strDamBonus.put( 32, "+1d4" );
        strDamBonus.put( 40, "+1d6" );
        strDamBonus.put( 56, "+2d6" );
        strDamBonus.put( 72, "+3d6" );
        strDamBonus.put( 88, "+4d6" );   
	}

	public String getBonus( int attrStr, int attrSiz ) {    	
    	Iterator<Integer> it = strDamBonus.keySet().iterator();
    	while ( it.hasNext() ) {
    		int value = it.next();
    		if ( attrStr + attrSiz <= value ) {
    			String bonus = strDamBonus.get( value );
    			return bonus;
    		}
    	}
    	return "overflow"; //TODO apply overflow rule
	}
}
