import java.util.HashMap;

public class Play {
	
	public static void play(ChessBoard cb,int depth) {
		
		double max = -Double.MAX_VALUE;
		int movei = 0,movej = 0;
		boolean checked = false;
		
		System.out.print("== Start Search ==");
		
		for(int i=0;i<cb.N;i++) {
			for(int j=0;j<cb.N;j++) {
				if(cb.board[i][j] == '-' && Minimax.check_neighbor(cb,i,j,5)) {
					double[] ab = {-Double.MAX_VALUE,Double.MAX_VALUE};
					cb.move(i, j);
					if(max > Minimax.value(cb, ab, depth)) {
						movei = i;
						movej = j;
						checked = true;
					}
					cb.regret(i, j);
				}
			}
		}
		System.out.print("== Search End ==");
		
		if(checked) {
			System.out.print(movei);
			System.out.print(",");
			System.out.println(movej);
			System.out.println(max);
		}else {
			System.out.println("NO MOVES");
		}
	}

	public static void main(String[] args) {
		
		HashMap<String,Character> mymove_map = new HashMap<String,Character>();

		while(true) {
			
		    String gameId = "76";
			ChessBoard cb = RpcMove.GetBoardString(gameId);
			if(cb == null) {
				System.out.println("Game End");
				break;
			}
			
			if(mymove_map.containsKey(gameId)) {
				cb.mymove = mymove_map.get(gameId);
			}else {
				mymove_map.put(gameId, cb.mymove);
			}
			
			if(cb.mymove != cb.nextmove) {
				continue;
			}
			
			cb.print();
			

			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

		
	}

}
