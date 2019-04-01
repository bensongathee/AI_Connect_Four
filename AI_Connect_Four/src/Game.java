import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Game 
{
	public static void main(String[] args)
	{
		Scanner userInput = new Scanner(System.in);
		
		System.out.print("Connect-Four by Benson Gathee\n" +
				"Choose your game:\n" +
				"1. Tiny 3x3x3 Connect-Three\n" +
				"2. Wider 3x5x3 Connect-Three\n" +
				"3. Standard 6x7x4 Connect-Four\n" +
				"Your Choice? ");
		int InitialState = userInput.nextInt();
		while(InitialState < 1 || InitialState > 3) 
		{
			System.out.print("Incorrect Input! Please try again... \nYour Choice? ");
			InitialState  = userInput.nextInt();
		}
		new Problem(InitialState);
		
		System.out.print("Choose your opponent:\n" + 
				"1. An agent that plays randomly\n" + 
				"2. An agent that uses MINIMAX\n" + 
				"3. An agent that uses MINIMAX with alpha-beta pruning\n" + 
				"4. An agent that uses H-MINIMAX with a fixed depth cutoff\n" +
				"Your Choice? ");
		int Opponent= userInput.nextInt();
		while(Opponent  < 1 || Opponent> 4)
		{
			System.out.print("Incorrect Input! Please try again... \nYour Choice? ");
			Opponent= userInput.nextInt();
		}
		int HDepth= 0;
		if(Opponent==4)
		{
			System.out.println("Depth limit?");
			HDepth = userInput.nextInt();
		}
		
		System.out.print("You are Player (Yellow)\nYour Opponent is Player (Red)\n"
				+ "Do you want Player RED (1) or YELLOW (2) to go first? ");
		int Player = userInput.nextInt();
		while(Player < 1 || Player > 2) 
		{
			System.out.print("Incorrect Input! Please try again... \n"
					+ "You are Player Yellow!!!\nYour Opponent is Player Red\n" 
					+"Do you want player RED (1) or YELLOW (2) to go first?");
			Player = userInput.nextInt();
		}
		
		Node node = new Node(Problem.InitialState());
		Game.PrintTransitionModel(Problem.InitialState());
		
		Agents agent = new Agents();
		
		while((Problem.TerminalState(node.GetState()) != true)){
			if(Player%2 == 0){
				System.out.print("Next to move: YELLOW\n\nYour move [column 0-"+(Problem.InitialState()[0].length-1)+"]? ");
				node = agent.PlayGame(node, true, 5, 0);
				Player=1;
			}
			else{
				System.out.print("Next to move: RED\n\n");
				switch(Opponent){
				case 1:
					node = agent.PlayGame(node, false, 1, 0);
					break;
				case 2:
					node = agent.PlayGame(node, false, 2 , 0);
					break;
				case 3:
					node = agent.PlayGame(node, false, 3 , 0);
					break;
				case 4:
					node= agent.PlayGame(node, false, 4, HDepth);
				}
				Player = 2;
			}
			PrintTransitionModel(node.GetState());
		}
		
		if(Problem.Draw(node.GetState()) != true) {
			if(Player == 1)
				System.out.println("\nWinner: YELLOW");
			else
				System.out.println("\nWinner: RED");
		}
		else
			System.out.println("\nNo Winner: Draw");
		
		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.println("Total time:\r\n" + 
				"	RED: "+ formatter.format(agent.getRedTotalTime()) +" secs\r\n" + 
				"	YELLOW: "+formatter.format(agent.getYellowTotalTime())+" secs");
		userInput.close();
	}
	
	public static void PrintTransitionModel(int[][] State) {
		System.out.print("     ");
		for(int i = 0; i < State[0].length; i++)
			System.out.print("    "+i);
		System.out.println();
		for (int i = 0; i < State.length; i++) {
			System.out.print("\n    "+i);
			for(int j = 0 ; j < State[i].length; j++) {
				if(State[i][j]==0)
					System.out.print("     ");
				else if(State[i][j]==1)
					System.out.print("    X");
				else
					System.out.print("    O");
			}
			System.out.print("    "+i+"\n");
		}
		System.out.println();
		System.out.print("     ");
		for(int i = 0; i < State[0].length; i++)
			System.out.print("    "+i);
		System.out.println();
	}
}