import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Agent_Using_Brain {
	
	private Scanner userInput = new Scanner(System.in);
	
	private long YellowTotalTime;
	
	public long GetYellowTotalTime()
	{
		return YellowTotalTime;
	}
	
	public Node PlayGame(Node node, int Player)
	{
		long start = System.currentTimeMillis();
		
		int Column = userInput.nextInt();
		
		while(Column < 0 || Column >(node.GetState()[0].length-1)) 
		{
			System.out.print("Incorrect Input! Please try again... \nYour move [column 0-"+(node.GetState()[0].length-1)+"]? ");
			Column  = userInput.nextInt();
		}

		while(Problem.Action(node.GetState(), Column) != true)
		{
			System.out.print("Column is full! Select another Column...\nYour move [column 0-"+(node.GetState()[0].length-1)+"]? ");
			Column  = userInput.nextInt();
			
			while(Column < 0 || Column > (node.GetState()[0].length-1)) 
			{
				System.out.print("Incorrect Input! Please try again... \nYour move [column 0-"+(node.GetState()[0].length-1)+"]? ");
				Column  = userInput.nextInt();
			}
		}
		
		Node NewNode = Problem.Result(node.GetState(), Column, Player);
		
		NumberFormat formatter = new DecimalFormat("#0.00000");
		long end = System.currentTimeMillis();
		YellowTotalTime += (end - start) / 1000d;
		
		System.out.println(
				"Elapsed time: " + formatter.format((end - start) / 1000d) + " secs\r\n" + 
				"Yellow@" + Column+"\n");
		
		return NewNode;
	}
}
