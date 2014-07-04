package com.genuine.prog.practice;

public class SquareRoot {
	
	public double squareRoot( double n) {
		double x = n;
		double y = 1;
		double e = 0.0000000001;
		while ( x - y > e) {
			x = (x + y) /2;
			y = n/x;
		}
		
		return x;
	}
	
	public static void main(String[] argvs) {
		SquareRoot sqrt = new SquareRoot();
		double n = 4;
		System.out.println("The squareRoot of " + n + " is " + sqrt.squareRoot(n));
	}
}

