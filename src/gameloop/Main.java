package gameloop;

import libraries.StdDraw;

public class Main
{
	public static void main(String[] args)
	{
		MainMenu game = new MainMenu();
		game.launch();
		while (!game.getStop())
		{
			game.processGameStep();
		}
		
		StdDraw.dispose();
		System.exit(0); //to shut down the application
		
	}
}
