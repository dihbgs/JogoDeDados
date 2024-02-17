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

	public int getvalueerior() {
		return value;
	}

	public void roll() {
		this.value = 1 + (int) (Math.random() * 5);
	}

	public String toString() {
		return Integer.toString(value);
	}
}