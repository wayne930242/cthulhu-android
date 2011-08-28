package org.xfon.m.coc;

import java.util.Random;

final public class Dice {
	private int count;
	private int sides;
	private int plus;
	private static Random random = new Random();

	public Dice(int count, int sides, int plus) {
		super();
		this.count = count;
		this.sides = sides;
		this.plus = plus;
	}

	public Dice(int count, int sides) {
		super();
		this.count = count;
		this.sides = sides;
		this.plus = 0;
	}

	public int roll() {
		return roll( null );
	}
	
	public int roll( StringBuffer log ) {
		return Dice.roll( count, sides, plus, log );
	}

	public static int roll(int count, int sides, int plus, StringBuffer log ) {
		int sum = 0;
		String plusString = "";
		if (log != null ) {			
			log.append( "" + count + "d" + sides );
			if ( plus != 0 ) plusString = ( ( plus > 0 ) ? "+" : "" ) + plus; 
			log.append( plusString + ": " );			
		}
		if (count <= 0 || sides <= 0) return -1;		
		for (int i = 0; i < count; i++) {
			int res = random.nextInt(sides) + 1;
			if ( log != null ) log.append( ( i != 0 ? ", " : "" ) + res );
			sum += res;
		}
		if ( plus != 0 ) log.append( "  " +  plusString );
		sum += plus;
		log.append( " [" + sum + "]" );
		return sum;			
	}
}
