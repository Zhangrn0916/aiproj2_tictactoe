
public class ChessBoard {
	public int N = 12;
	public int M = 6;
	
	public char[][] board;
	public char mymove;
	public char nextmove;
	
	//检查是否是空棋盘 true时 棋盘为空
	public boolean nomove;
	
	
	
	public ChessBoard(char[][] b,int n,int m,char nextmove,boolean nomove) {
		this.board = b;
		this.mymove = nextmove;
		this.nextmove = nextmove;
		this.nomove = nomove;
		this.M = m;
		this.N = n;
	}
	
	public void move(int i,int j) {
		board[i][j] = nextmove;
		nextmove = (nextmove == 'O')?'X':'O';
	}
	
	public void regret(int i,int j) {
		this.nextmove= board[i][j];
		board[i][j] = '-'; 
	}
	
	public void print() {
		System.out.println("=================================");
		System.out.println("Nextmove:"+ String.valueOf(nextmove)+" || Mymove:"+ String.valueOf(mymove));
		System.out.println("N:"+String.valueOf(N)+" || M:"+String.valueOf(M));
		
		System.out.println("ChessBoard:");
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				System.out.print(board[i][j]);
			}
			System.out.println(" ");
		}
	}
	
}
