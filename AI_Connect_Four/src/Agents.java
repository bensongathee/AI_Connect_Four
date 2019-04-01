import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Agents implements Agents_Interface{
	
	private ArrayList<Node> frontiers = new ArrayList<>();
	private int stateCounter;
	private int depthCounter;
	private long RedTotalTime;
	private long YellowTotalTime;
	
	public long getRedTotalTime() {
		return RedTotalTime;
	}
	
	public long getYellowTotalTime() {
		return YellowTotalTime;
	}
	
	@Override
	public Node PlayGame(Node node, boolean player, int agent, int HDepth) {
		long start = System.currentTimeMillis();
		Node randomBestMove = null;
		
		if(agent != 1 && agent != 5)
			System.out.println("I'm thinking...");
		else if(agent != 5)
			System.out.println("I'm guessing...");
		
		double bestMoveUtility = 0;
		
		if(agent == 1)
			bestMoveUtility = Random(node, player);
		else if(agent == 2)
			bestMoveUtility = Minimax(node, player);
		else if(agent == 3)
			bestMoveUtility = Minimax_AlphaB(node, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
		else if(agent == 4)
			bestMoveUtility = H_Minimax(node, player, HDepth);
		else
			bestMoveUtility = Brain(node);
		
		NumberFormat formatter = new DecimalFormat("#0.00000");
		long end = System.currentTimeMillis();
		if(agent != 5)
			RedTotalTime += (end - start) / 1000d;
		else
			YellowTotalTime+=(end - start) /1000d;
			
		if(agent == 1) {
			randomBestMove = frontiers.get((int) bestMoveUtility);
			System.out.println("	Guessed "+1+" states\r\n" + 
					"	Random move: Red@" + randomBestMove .GetBestMove()+", value: Unknown\r\n" + 
					"Elapsed time: " + formatter.format((end - start) / 1000d) + " secs\r\n" + 
					"Red@" + randomBestMove .GetBestMove()+"\n");
		}else if(agent != 1 && agent != 5) {	
			randomBestMove = SelectBestMove(bestMoveUtility);
			System.out.println("	visited "+stateCounter+" states\r\n" + 
					"	best move: Red@" + randomBestMove.GetBestMove()+", value: "+ bestMoveUtility +"\r\n" + 
					"Elapsed time: " + formatter.format((end - start) / 1000d) + " secs\r\n" + 
					"Red@" + randomBestMove.GetBestMove()+"\n");
		}else {
			randomBestMove = Problem.Result(node.GetState(), (int) bestMoveUtility, 2);
			System.out.println(
					"Elapsed time: " + formatter.format((end - start) / 1000d) + " secs\r\n" + 
					"Yellow@" + bestMoveUtility+"\n");
		}
		stateCounter = 0;
		
		return randomBestMove;
	}
	
	@Override
	public double Random(Node node, boolean player) {
		frontiers = (ArrayList<Node>) node.Expand(false);
		Random random = new Random();
		return random.nextInt(node.Expand(player).size());
	}
	
	@Override
	public double Minimax(Node node, boolean player) {
		if (Problem.TerminalState(node.GetState()))
			return Problem.Utility();
			
		if (player){
			node.SetUtility(Integer.MIN_VALUE);
			for(Node n : node.Expand(player)){	
				stateCounter += 1; 
				depthCounter += 1;
				n.SetUtility(Minimax(n, false));
				node.SetUtility(Math.max(n.GetUtility(), node.GetUtility()));
				depthCounter-=1;
			}
			return node.GetUtility();
		}
		else{
			node.SetUtility(Integer.MAX_VALUE);
			for(Node n : node.Expand(player)){	
				stateCounter += 1;
				depthCounter+=1;
				n.SetUtility(Minimax(n, true));
				node.SetUtility(Math.min(n.GetUtility(), node.GetUtility()));
				if (depthCounter == 1)
					frontiers.add(n);
				depthCounter-=1;
			}
			return node.GetUtility();
		}
	}

	@Override
	public double Minimax_AlphaB(Node node, boolean player, double alpha, double beta) {
		if (Problem.TerminalState(node.GetState())) 
			return Problem.Utility();
			
		if (player){
			node.SetUtility(Integer.MIN_VALUE);
			for(Node n : node.Expand(player)){	
				stateCounter+=1; 
				depthCounter+=1;
				n.SetUtility(Minimax_AlphaB(n, false, alpha, beta));
				node.SetUtility(Math.max(n.GetUtility(), node.GetUtility()));
				depthCounter-=1;
				alpha = Math.max(alpha, n.GetUtility());
				if(beta <= alpha)
					break;
			}
			return node.GetUtility();
		}
		else{
			node.SetUtility(Integer.MAX_VALUE);
			for(Node n : node.Expand(player)){	
				stateCounter+=1;
				depthCounter+=1;
				n.SetUtility(Minimax_AlphaB(n, true, alpha, beta));
				node.SetUtility(Math.min(n.GetUtility(), node.GetUtility()));
				beta = Math.min(beta,n.GetUtility());
				if (depthCounter == 1 && (frontiers.isEmpty() || n.GetUtility() < frontiers.get(frontiers.size()-1).GetUtility()))
					frontiers.add(n);
				depthCounter-=1;
				if(beta <= alpha)
					break;
			}
			return node.GetUtility();
		}
	}

	@Override
	public double H_Minimax(Node node, boolean player, int HDepth) {
		if(Problem.TerminalState(node.GetState()))
			return Problem.Utility();
		else if (depthCounter == HDepth)
			return Problem.CutOffUtility(node.GetState());
		
		if (player)
		{
			node.SetUtility(Integer.MIN_VALUE);
			for(Node n : node.Expand(player))
			{	
				stateCounter+=1; 
				depthCounter+=1;
				n.SetUtility(H_Minimax(n, false, HDepth));
				node.SetUtility(Math.max(n.GetUtility(), node.GetUtility()));
				depthCounter-=1;
			}
			return node.GetUtility();
		}
		else
		{
			node.SetUtility(Integer.MAX_VALUE);
			for(Node n : node.Expand(player))
			{	
				stateCounter+=1;
				depthCounter+=1;
				n.SetUtility(H_Minimax(n, true, HDepth));
				node.SetUtility(Math.min(n.GetUtility(), node.GetUtility()));
				if (depthCounter == 1)
					frontiers.add(n);
				depthCounter-=1;
			}
			return node.GetUtility();
		}
	}

	@Override
	public double Brain(Node node) {
		
		Scanner userInput = new Scanner(System.in);
		int Column = userInput.nextInt();
		
		while(Column < 0 || Column >(node.GetState()[0].length-1)) {
			System.out.print("Incorrect Input! Please try again... \nYour move [column 0-"+(node.GetState()[0].length-1)+"]? ");
			Column  = userInput.nextInt();
		}

		while(Problem.Action(node.GetState(), Column) != true) {
			System.out.print("Column is full! Select another Column...\nYour move [column 0-"+(node.GetState()[0].length-1)+"]? ");
			Column  = userInput.nextInt();
			
			while(Column < 0 || Column > (node.GetState()[0].length-1)) {
				System.out.print("Incorrect Input! Please try again... \nYour move [column 0-"+(node.GetState()[0].length-1)+"]? ");
				Column  = userInput.nextInt();
			}
		}
		return Column;
	}

	@Override
	public Node SelectBestMove(double bestMoveUtility) {
		ArrayList<Node> bestMoves = new ArrayList<>();
		for(Node n : frontiers)
			if(n.GetUtility() == bestMoveUtility)
				bestMoves.add(n);
		frontiers.clear();
		depthCounter = 0;
		Random random = new Random();
		return bestMoves.get(random.nextInt(bestMoves.size()));
	}
}
