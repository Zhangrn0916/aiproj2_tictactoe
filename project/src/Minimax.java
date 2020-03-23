
public class Minimax {

	public static double eval(ChessBoard cb) {
		char[][] d1 = getDiagonal1(cb);
		char[][] d2 = getDiagonal2(cb);
		char[][] v = getVertical(cb);
		double res = roweval(cb.board,cb.mymove)+roweval(d1,cb.mymove)+roweval(d2,cb.mymove)+roweval(v,cb.mymove);
		return res;
	}
	
	public static double roweval(char[][] board,char m) {
		double sum = 0;
		//r = rival move
		//m = my move
		char r = m == 'O'?'X':'O';
		
		for(int i=0;i<board.length;i++) {
			String row = new String(board[i]);
			
			if(row.contains("-"+r+r+r+r+"-")) {
				sum-=10000;
			}
			if(row.contains(r+r+r+r+"-"+r)) {
				sum-=10000;
			}
			if(row.contains(r+r+r+"-"+r+r)) {
				sum-=10000;
			}
			if(row.contains(r+r+"-"+r+r+r)) {
				sum-=10000;
			}
			
			if(row.contains("-"+m+m+m+m+"-")) {
				sum+=10000;
			}
			if(row.contains(m+m+m+m+"-"+m)) {
				sum+=10000;
			}
			if(row.contains(m+m+m+"-"+m+m)) {
				sum+=10000;
			}
			if(row.contains(m+"-"+m+m+m+m)) {
				sum+=10000;
			}
			
		}
		return sum;
	}
	
	public static char[][] getVertical(ChessBoard cb){
		char[][] res = new char[cb.N][cb.N];
		for(int i=0;i<cb.N;i++) { 
			for(int j=0;j<cb.N;j++) {
				res[j][i] = cb.board[i][j];
			}
		}
		return res;
	}
	
	public static char[][] getDiagonal1(ChessBoard cb){
		char[][] d = new char[cb.N+cb.N][cb.N];
		
		int i=0;
		int x=cb.N-1,y=0;
		
		while(y<cb.N) {
			int xi = x,yi = y;
			int j=0;
			while( xi < cb.N && yi < cb.N ) {
				d[i][j++] = cb.board[xi++][yi++];
			}
			while(j<cb.N) {
				d[i][j++] = '*';
			}
			if(x==0) {
				y++;
			}else {
				x--;
			}
			i++;
		}
		
		return d;
	}
	
	public static char[][] getDiagonal2(ChessBoard cb){
		char[][] d = new char[cb.N+cb.N][cb.N];
		
		int i=0;
		int x=0,y=0;
		
		while(x<cb.N ) {
			int xi = x,yi = y;
			int j=0;
			while( xi < cb.N && yi >= 0 ) {
				d[i][j++] = cb.board[xi++][yi--];
			}
			while(j<cb.N) {
				d[i][j++] = '*';
			}
			if(y==cb.N-1) {
				x++;
			}else {
				y++;
			}
			i++;
		}
		
		return d;
	}
	

	public static double value(ChessBoard cb,double[] ab,int depth) {
		if(depth == 0) {
			return eval(cb);
		}
		if(cb.mymove == cb.nextmove) {
			return max_value(cb,ab,depth-1);
		}else {
			return min_value(cb,ab,depth-1);
		}
	}
	
	public static double max_value(ChessBoard cb,double[] ab,int depth){
		double v =  -Double.MAX_VALUE;
		
		for(int i=0;i<cb.N;i++) {
			for(int j=0;j<cb.N;j++) {
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
	
	public static double min_value(ChessBoard cb,double[] ab,int depth){
		double v =  Double.MAX_VALUE;
		
		for(int i=0;i<cb.N;i++) {
			for(int j=0;j<cb.N;j++) {
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

	public static boolean check_neighbor(ChessBoard cb,int x,int y,int r) {
		int xstart = x-r<0? 0:x-r;
		int xend = x+r>=cb.N? cb.N-1:x+r;
		int ystart = y-r<0? 0:y-r;
		int yend = y+r>=cb.N? cb.N-1:y+r;
		
		for(int i=xstart;i<=xend;i++) {
			for(int j=ystart;j<=yend;j++) {
				if(cb.board[i][j] != '-') {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void print(char[][] board) {
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		char[][] b = {	{'-','-','-','-','-','-','-','-','-','-','-','-'},
						{'-','-','O','-','O','-','-','-','-','-','-','-'},
						{'-','-','-','X','-','O','-','-','-','-','-','-'},
						{'-','-','X','-','O','-','-','-','-','-','-','-'},
						{'-','X','-','X','-','-','-','-','-','-','-','-'},
						{'-','O','-','-','X','-','-','-','-','-','-','-'},
						{'-','-','-','-','-','-','-','-','-','-','-','-'},
						{'-','-','O','-','O','-','-','-','-','-','-','-'},
						{'-','X','-','X','-','O','-','-','-','-','-','-'},
						{'-','-','X','-','O','-','-','-','-','-','-','-'},
						{'-','-','-','X','-','-','-','-','-','-','-','-'},
						{'-','O','-','-','-','-','-','-','-','-','-','-'}};
		
		ChessBoard cb = new ChessBoard(b,12,6,'X',false);
		
	    char[][] d1 = getDiagonal1(cb);
		char[][] d2 = getDiagonal2(cb);
		char[][] v = getVertical(cb);
		
		print(d1);
		System.out.println("**************");
		print(d2);
		System.out.println("**************");
		print(v);

    }
	
}
