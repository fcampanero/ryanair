package com.ryanair.views;

public interface Views {

	public static interface Flight {
	}

	public static interface List {
	}
	
	public static interface Ryanair {		
		public static interface Listar extends Ryanair {}		
	}
}
