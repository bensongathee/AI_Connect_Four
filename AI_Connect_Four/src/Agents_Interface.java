public interface Agents_Interface {
	
	public Node PlayGame(Node node, boolean player, int agent, int HDepth);
	
	public double Random(Node node, boolean player);
	
	public double Minimax(Node node, boolean player);
	
	public double Minimax_AlphaB(Node node, boolean player, double alpha, double beta);
	
	public double H_Minimax(Node node, boolean player, int HDepth);
	
	public double Brain(Node node);
	
	public Node SelectBestMove(double bestMoveUtility);
}
