import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class Agent_Playing_Random 
{
	private Node RandomFrontierNode = new Node(Problem.InitialState());
	
	private long RedTotalTime;
	
	public long GetRedTotalTime()
	{
		return RedTotalTime;
	}
	public Node PlayGame(Node node)
	{
		long start = System.currentTimeMillis();
		System.out.println("I'm thinking...");
		
		RandomFrontierNode  = node.Expand(false).get(randomNumberInRange(0, (node.Expand(false).size()-1)));
		
		NumberFormat formatter = new DecimalFormat("#0.00000");
		long end = System.currentTimeMillis();
		RedTotalTime += (end - start) / 1000d;
		
		System.out.println("	Guessed "+1+" states\r\n" + 
				"	Random move: Red@" + RandomFrontierNode .GetBestMove()+", value: Unknown\r\n" + 
				"Elapsed time: " + formatter.format((end - start) / 1000d) + " secs\r\n" + 
				"Red@" + RandomFrontierNode .GetBestMove()+"\n");
		
		return RandomFrontierNode ;
	}
	public int randomNumberInRange(int min, int max) 
	{
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
