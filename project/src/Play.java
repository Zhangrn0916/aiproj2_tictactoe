
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
			//String moveId = RpcMove.Move(gameId, movei, movej);
			System.out.print(movei);
			System.out.print(",");
			System.out.println(movej);
			System.out.println(max);
		}else {
			System.out.println("NO MOVES");
		}
	}

	public static void main(String[] args) {
		ChessBoard cb = RpcMove.GetBoardString("76");
		cb.print();
		cb.move(4, 8);
		cb.print();
		cb.regret(4, 8);
		cb.print();
		
	}

}
