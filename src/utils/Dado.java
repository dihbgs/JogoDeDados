package src.utils;

import java.io.Serializable;

public class Dado implements Serializable {
	private int value;

	public Dado() {
		this(1);
	}

	public Dado(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void roll() {
		this.value = (int) (Math.random() * 6 + 1);
	}

	public String toString() {
		return Integer.toString(value);
	}
}