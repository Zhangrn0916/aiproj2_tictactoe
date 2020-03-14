
public class ChessBoard {
	public static final int X = 0;
	public static final int Y = 20;
	
	public char[][] board;
	public char mymove;
	public char nextmove;
	
	//检查是否是空棋盘 true时 棋盘为空
	public boolean nomove;
	
	public ChessBoard(char[][] b,char nextmove,boolean nomove) {
		this.board = b;
		this.mymove = nextmove;
		this.nextmove = nextmove;
		this.nomove = nomove;
	}
	
	public void move(int i,int j) {
		board[i][j] = nextmove;
		nextmove = (nextmove == 'O')?'X':'O';
	}
	
	public void regret(int i,int j) {
		this.nextmove= board[i][j];
		board[i][j] = '-';
	}
	
}
