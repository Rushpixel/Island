package com.endoplasmdoesthiswork;

import com.endoplasm.Endogen;

public class Main {
	
	public static void main(String args[]){
		Game game = new Game();
		Endogen endogen = new Endogen(game);
		endogen.Begin();
	}

}
