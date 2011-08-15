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
		return Dice.roll( count, sides, plus );
	}

	public static int roll(int count, int sides, int plus) {
		int sum = 0;
		if (count <= 0 || sides <= 0)
			return -1;
		for (int i = 0; i < count; i++) {
			sum += random.nextInt(sides) + 1;
		}
		return sum + plus;
	}
}
