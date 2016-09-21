package BattleShip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client
{
	final String NEWL = System.getProperty("line.separator");
	
	private String name = "Player";
	PrintWriter out = null;
	BufferedReader in = null;
	GameManager man = null;
	GameBoard board = new GameBoard(10,10); //BLUFOR
	GameBoard targets = new GameBoard(10,10); //OPFOR
	
	Client( BufferedReader in, PrintWriter out, GameManager manager )
	{
		this.in = in;
		this.out = out;
		this.man = manager;
	}

	private void drawBattlespace(){
		out.println("Target Board:\n" + this.targets.drawOpponent());
		out.println("Your Ships:\n" + this.board.draw());
	}

	public void playGame() throws IOException
	{
		this.out.println( NEWL + NEWL + "   Missiles Away! Game has begun" );
		this.out.println( "   To Launch a missle at your enemy:" );
		this.out.println( "F 2 4" );
		this.out.println( "Fires a missile at coordinate x=2, y=4." );
		
		while(true) //TODO: Put Code Here to process in game commands, after each command, print the target board and game board w/ updated state )
		{
			out.println("------------------------");
			this.drawBattlespace();
			out.println("\tSalvo complete...\n\n");
			out.flush();
			this.processCommand();

			//Perform test here to see if we have won or lost
			if(allEnemyShipsAreDestroyed() == true)
			{ out.println("All targets destroyed!"); }
			if(allMyShipsAreDestroyed() == true)
			{ out.println("All our ships have been destroyed!"); }
		}
	}

	boolean stillAlive(GameBoard b){
		for (Ship s : b.myShips
				) {
			if (s.isAlive() == true)
			{ return false; }//since the game is still on
		}
		return true; //All ships returned false on isAlive()... game over, man. GAME OVER!
	}

	//Returns a bool, true iff all of this client's ships are destroyed
	boolean allMyShipsAreDestroyed()
	{ return stillAlive(board); }

	//Returns a bool, true iff all of the opponent's ships are destroyed
	boolean allEnemyShipsAreDestroyed()
	{ return stillAlive(targets); }

	//"F 2 4" = Fire command
	//"C Hello world, i am a chat message"
	//"D" - Redraw the latest game and target boards
	boolean processCommand() throws IOException
	{
		boolean cmdSuccess = false;
		out.println("Awaiting case-sensitive instruction, " + this.name + ".");
		String input = in.readLine();
		String [] inputArr = input.split(" ");
		switch (inputArr[0]){
			case "F":
				cmdSuccess = processFireCmd(inputArr);
				break;
			case "C":
				cmdSuccess = processChatCmd(input);
				break;
			case "D":
				board.draw();
				cmdSuccess = true;
				break;
			default:
				out.println("Say again, command unclear.");
				break;
		}
		return cmdSuccess;
	}
	
	//When a fire command is typed, this method parses the coordinates and launches a missile at the enemy
	boolean processFireCmd( String [] s )
	{
		try{
			Integer x = Integer.parseInt(s[1]);
			Integer y = Integer.parseInt(s[2]);
			Position tgt = new Position(x, y);
			targets.fireMissile(tgt);
			System.out.println("Coordinated locked, firing at (" + x + "," + y + ")!");
			return true;
		}
		catch (Exception e) {System.out.println("Invalid input."); return false;}
	}
	
	//Send a message to the opponent
	boolean processChatCmd( String s )
	{
		//TODO: Implement Chat
		return false; //Placeholder for now.
	}
	
	GameBoard getGameBoard() { return this.board; }

	private HEADING findDirection(String s)
	{
		char in = s.charAt(0);
		for (HEADING h: HEADING.values())
		{
			if(in == (h.toString().charAt(0)))
			{ return h; }
		}
		return null;
	}

	public void initPlayer() throws IOException
	{
		out.println("Welcome, Commander. Please enter your name...");
		out.flush();
		this.name = in.readLine();
		out.println("Copy that Commander. The situation is as follows:");
		out.println("\tYou will now place 2 ships. You may choose between either a Cruiser (C) " );
		out.println("\tand Destroyer (D)...");
		out.println("\tEnter Ship info. An example input looks like:");
		out.println("\n\tD 2 4 S USS MyBoat\n");
		out.println("\tThe above line creates a Destroyer with the stern located at x=2 (col)," );
		out.println("\ty=4 (row) and the front of the ship will point to the SOUTH (valid" );
		out.println("\theadings are N, E, S, and W.\n\n" );
		out.println("\tthe name of the ship will be \"USS MyBoat\"");
		out.flush();

		//Get ship locations from the player for all 2 ships (or more than 2 if you're using more ships)
		for (int i = 0; i < 2; i++)
		{
			out.println("Enter ship " + (i + 1) + " of " + 2);
			out.flush();
			initShips(in);
		}

		//After all game state is input, draw the game board to the client
		this.drawBattlespace();
		
		out.println( "Waiting for other player to finish their setup, then war will ensue!" );
	}

	boolean initShips(BufferedReader in){
		String inLine = null;
		try	{ inLine = in.readLine(); }
		catch (IOException e)
		{ e.printStackTrace(); }
		String [] inLineArr = inLine.split(" ");
		String name = inLine.substring(8);
		Position newPos = new Position(Integer.parseInt(inLineArr[1]), Integer.parseInt(inLineArr[2]));
		switch (inLineArr[0]){
			case "D":
				Destroyer newDest = new Destroyer(name);
				board.addShip(newDest, newPos, findDirection(inLineArr[3]));
				return true;
			case "C":
				Cruiser newCruiser = new Cruiser(name);
				board.addShip(newCruiser, newPos, findDirection(inLineArr[3]));
				return true;
			default:
				return false;
		}
	}

	String getName() { return this.name; }
	
	public static void main( String [] args )
	{

	}
}
