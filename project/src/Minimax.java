
public class Minimax {
	
	public double eval(ChessBoard cb) {
		//TODO: eval func
		return 0;
	}
	
	public double value(ChessBoard cb,double[] ab,int depth) {
		if(depth == 0) {
			return eval(cb);
		}
		if(cb.mymove == cb.nextmove) {
			return max_value(cb,ab,depth-1);
		}else {
			return min_value(cb,ab,depth-1);
		}
			
	}
	
	public double max_value(ChessBoard cb,double[] ab,int depth){
		char nextmove = cb.nextmove;
		double v =  Double.MIN_VALUE;
		
		for(int i=0;i<ChessBoard.X;i++) {
			for(int j=0;j<ChessBoard.Y;j++) {
				if(cb.board[i][j] == '-' && check_neighbor(cb,i,j,3)) {
					cb.move(i, j);
					v = Math.max(v, value(cb,ab,depth-1));
					cb.regret(i, j);
					
					if(v>=ab[1]){
						return v;
					}
					ab[0] = Math.max(ab[0], v);	
				}
			}
		}
		return v;
	}
	
	public double min_value(ChessBoard cb,double[] ab,int depth){
		char nextmove = cb.nextmove;
		double v =  Double.MIN_VALUE;
		
		for(int i=0;i<ChessBoard.X;i++) {
			for(int j=0;j<ChessBoard.Y;j++) {
				if(cb.board[i][j] == '-' && check_neighbor(cb,i,j,3)) {
					cb.move(i, j);
					v = Math.min(v, value(cb,ab,depth-1));
					cb.regret(i, j);
					
					if(v<=ab[0]){
						return v;
					}
					ab[1] = Math.min(ab[1], v);	
				}
			}
		}
		return v;
	}

	public boolean check_neighbor(ChessBoard cb,int x,int y,int r) {
		int xstart = x-r<0? 0:x-r;
		int xend = x+r>=ChessBoard.X? ChessBoard.X-1:x+r;
		int ystart = y-r<0? 0:y-r;
		int yend = y+r>=ChessBoard.Y? ChessBoard.Y-1:y+r;
		
		for(int i=xstart;i<xend;i++) {
			for(int j=ystart;j<=yend;j++) {
				if(cb.board[i][j] != '-') {
					return true;
				}
			}
		}
		return false;

	}
	
}
