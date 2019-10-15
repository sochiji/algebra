package tester;

import java.util.LinkedList;

import expression.Monomial;

public class Tester1 {
	public static void main(String[] args) {
		LinkedList<Monomial> r1 = new LinkedList<Monomial>();
		r1.add(new Monomial(1, 0));
		r1.add(new Monomial(2, 1));
		r1.add(new Monomial(3, 2));
		LinkedList<Monomial> r2 = new LinkedList<Monomial>();
		r2.add(new Monomial(1, 1));
		r2.add(new Monomial(4, 4));

	}
}
