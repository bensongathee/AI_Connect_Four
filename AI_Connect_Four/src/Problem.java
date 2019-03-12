public class Problem {
	
	private static int K_game;
	private static int Utility;
	private static int[][] InitialState;
	private static Check check = new Check();
	
	public Problem(int Case)
	{
		switch (Case) 
		{
		case 1:
			K_game = 3;
			InitialState = new int[3][3];
			break;
			
		case 2:
			K_game = 3;
			InitialState = new int[3][5];
			break;
			
		case 3:
			K_game = 4;
			InitialState = new int[6][7];
			break;
		}
	}
	public static int[][] InitialState()
	{
		return InitialState;
	}
	public static boolean Action(int[][] State,int j) 
	{
		if(check.TestAction(State, j))
			return true;
		return false;
	}

	public static boolean TerminalState(int[][] State) 
	{
		if(check.IsWinner(State,K_game))
		{
			if (Check.WinnerPlayer == 1)
				Utility = -1;
			else
				Utility = 1;
			
			return true;
		}
		else if(check.IsFull(State))
		{
			Utility = 0;
			return true;
		}
		
		return false;
	}
	public static int Utility()
	{
		return Utility;
	}
	
	public static Node Result(int[][] GridState, int Column, int Color)
	{
		return check.PutaMark(GridState, Column, Color);
	}
	public static boolean Draw(int[][] State)
	{
		if(check.IsWinner(State, Problem.K_game))
			return false;
		else if(check.IsFull(State))
			return true;
		return false;
	}
	public static double CutOffUtility(int[][] State)
	{
		return check.CutOff(State);
	}
}

class Check
{
	static int WinnerPlayer; //Change
	
	public double CutOff(int[][] State)
	{
		double Max = 0, Min = 0; 
		for(int i = 0; i < State.length; i++)
		{
			for(int j = 0; j < State[0].length; j++)
			{
				if(i != 0)
				{
					if(State[i-1][j] == 1)
						Min -=1; 
					else if(State[i-1][j] == 2)
						Max +=1;
				}
				else if(i != (State.length-1))
				{
					if(State[i+1][j] == 1)
						Min -=1;
					else if(State[i+1][j] == 2)
						Max +=1;
				}
				else if(j != 0)
				{
					if(State[i][j-1] == 1)
						Min -=1;
					else if(State[i][j-1] == 2)
						Max +=1;
				}
				else if(j != (State[0].length-1))
				{
					if(State[i][j+1] == 1)
						Min -=1;
					else if(State[i][j+1] == 2)
						Max +=1;
				}
				else if(i != 0 && j != 0)
				{
					if(State[i-1][j-1] == 1)
						Min -=1;
					else if(State[i-1][j-1] == 2)
						Max +=1;
				}
				else if(i != (State.length-1) && j != (State[0].length-1))
				{
					if(State[i+1][j+1] == 1)
						Min -=1;
					else if(State[i+1][j+1] == 2)
						Max +=1;
				}
				else if(i != 0 && j != (State[0].length-1))
				{
					if(State[i-1][j+1] == 1)
						Min -=1;
					else if(State[i-1][j+1] == 2)
						Max +=1;
				}
				else if(i != State.length-1 && j != 0)
				{
					if(State[i+1][j-1] == 1)
						Min -=1;
					else if(State[i+1][j-1] == 2)
						Max +=1;
				}
			}
		}
		double average = ((Max+Min)/2);
		return average;
	}
	
	public Node PutaMark(int[][] State, int Column, int Color)
	{
		for(int i = (State.length-1); i >= 0; i--) 
		{
			if(State[i][Column]==0) 
			{
				State[i][Column] = Color;
				break;
			}	
		}
		return new Node(State);
	}
	public boolean TestAction(int[][] State, int j) 
	{
		if(State[0][j]==0)
			return true;
		return false;
	}
	
	public Boolean IsFull(int[][] State) 
	{		
		for(int j=0; j<State[0].length; j++) 
		{
			if(State[0][j]==0)
				return false;
		}
		return true;
	}
	public boolean IsWinner(int[][] State, int k_game)
	{
		int i,j;
		
		for(i=0; i<State.length; i++) 
		{
			for(j=0; j<=State[0].length-k_game; j++) 
			{
				if(State[i][j] == State[i][j+1] &&
				   State[i][j] == State[i][j+2] &&
				   State[i][j] == State[i][j+k_game-1] &&
				   State[i][j]!=0) 
				{
					if(State[i][j] == 1)
						WinnerPlayer = 1;
					else 
						WinnerPlayer = 2;
					return true;	
				}
			}
		}
		
		for(i=0; i<=State.length-k_game; i++) 
		{
			for(j=0; j<State[0].length; j++) 
			{
				if(State[i][j] == State[i+1][j] &&
				   State[i][j] == State[i+2][j] &&
				   State[i][j] == State[i+k_game-1][j] &&
				   State[i][j]!=0)
				{
					if(State[i][j] == 1)
						WinnerPlayer = 1;
					else 
						WinnerPlayer = 2;
					return true;
				}
			}
		}
		
		for(i=0; i<=State.length-k_game; i++) 
		{
			for(j=0; j<=State[0].length-k_game; j++) 
			{
				if(State[i][j] == State[i+1][j+1] &&
				   State[i][j] == State[i+2][j+2] &&
				   State[i][j] == State[i+k_game-1][j+k_game-1] &&
				   State[i][j]!=0)
				{
					if(State[i][j] == 1)
						WinnerPlayer = 1;
					else 
						WinnerPlayer = 2;
					return true;
				}
			}
		}
		
		for(i=State.length-1; i>=k_game-1; i--) 
		{
			for(j=0; j<=State[0].length-k_game; j++) 
			{
				if(State[i][j] == State[i-1][j+1] &&
				   State[i][j] == State[i-2][j+2] &&
				   State[i][j] == State[i-(k_game-1)][j+k_game-1] &&
				   State[i][j]!=0)
				{
					if(State[i][j] == 1)
						WinnerPlayer = 1;
					else 
						WinnerPlayer = 2;
					return true;
				}
			}
		}
		
		return false;
	}
}


