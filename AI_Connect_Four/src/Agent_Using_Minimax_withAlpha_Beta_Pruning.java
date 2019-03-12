import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Agent_Using_Minimax_withAlpha_Beta_Pruning {

	private Node RandomBestMove = new Node(Problem.InitialState());
	
	private ArrayList<Node> Frontiers = new ArrayList<>();
	
	private ArrayList<Node> BestMoves = new ArrayList<>();
	
	private long RedTotalTime;
	
	private int States;
	
	private double BestMoveUtility;
	
	public long GetRedTotalTime()
	{
		return RedTotalTime;
	}
	public Node PlayGame(Node node, boolean Player)
	{
		long start = System.currentTimeMillis();
		System.out.println("I'm thinking...");
		
		BestMoveUtility = Minimax(node, (Integer.MIN_VALUE), Integer.MAX_VALUE, Player);
		SelectBestMove();
		Frontiers.clear();
		BestMoves.clear();
		
		NumberFormat formatter = new DecimalFormat("#0.00000");
		long end = System.currentTimeMillis();
		RedTotalTime += (end - start) / 1000d;
		
		System.out.println("	visited "+States+" states\r\n" + 
				"	best move: Red@" + RandomBestMove.GetBestMove()+", value: "+ BestMoveUtility +"\r\n" + 
				"Elapsed time: " + formatter.format((end - start) / 1000d) + " secs\r\n" + 
				"Red@" + RandomBestMove.GetBestMove()+"\n");
		States = 0;
		return RandomBestMove;
	}
	private int Depth;
	
	public double Minimax(Node node, double alpha, double beta, boolean Player)
	{
		if (Problem.TerminalState(node.GetState())) 
			return Problem.Utility();
			
		if (Player)
		{
			node.SetUtility(Integer.MIN_VALUE);
			for(Node n : node.Expand(Player))
			{	
				States+=1; Depth+=1;
				n.SetUtility(Minimax(n, alpha, beta, false));
				node.SetUtility(Math.max(n.GetUtility(), node.GetUtility()));
				Depth-=1;
				alpha = Math.max(alpha, n.GetUtility());
				if(beta <= alpha)//use { if(beta < alpha) } if we also want children which are equal in utility with the last one in the frontier
					break;
			}
			return node.GetUtility();
		}
		else
		{
			node.SetUtility(Integer.MAX_VALUE);
			for(Node n : node.Expand(Player))
			{	
				States+=1;Depth+=1;
				n.SetUtility(Minimax(n, alpha, beta, true));
				node.SetUtility(Math.min(n.GetUtility(), node.GetUtility()));
				beta = Math.min(beta,n.GetUtility());
				if (Depth == 1 && (Frontiers.isEmpty() || n.GetUtility() < Frontiers.get(Frontiers.size()-1).GetUtility()))// modify to { n.GetUtility() < Frontiers.get(Frontiers.size()-1).GetUtility() } if we also want children which are equal in utility with the last one in the frontier
					Frontiers.add(n);
				Depth-=1;
				if(beta <= alpha)//use { if(beta < alpha) } if we also want children which are equal in utility with the last one in the frontier
					break;
			}
			return node.GetUtility();
		}
	}
	public void SelectBestMove()
	{
		System.out.println(Frontiers.size());
		RandomBestMove.SetUtility(Integer.MAX_VALUE);
		for(Node n : Frontiers)
		{
			if(n.GetUtility() == BestMoveUtility)
				BestMoves.add(n);
			Game.PrintTransitionModel(n.GetState());
			System.out.println(n.GetUtility());
		}
		RandomBestMove = BestMoves.get(randomNumberInRange(0, (BestMoves.size()-1)));
	}
	public int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
