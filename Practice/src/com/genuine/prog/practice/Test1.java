package com.genuine.prog.practice;

public class Test1 {

	public static void main ( String [] argvs) {
	   Double t = 1.0;
	   increment(t);
	   System.out.println("Main: " + t);
	   t++;
	   System.out.println("Main: " + t);
	   increment(t);
	}
	
	static void increment(Double t){
	     t++; 
	     System.out.println("Method: " + t);
	  }
}
