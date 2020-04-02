import java.util.HashSet;
import java.util.Set;

public class Minimax {

	// public static double[] punish =
	// {0.01,0.1,1,100,1000,10000,100000,1000000,10000000,100000000};
	public static double max_punish = 1000000000;

	public static double eval(ChessBoard cb) {

		double res = roweval(cb.board, cb.mymove, cb.M) + roweval(cb.d1, cb.mymove, cb.M)
				+ roweval(cb.d2, cb.mymove, cb.M) + roweval(cb.v, cb.mymove, cb.M);
		return res;
	}

	public static double roweval(char[][] board, char m, int target) {

		double sum = 0;
		// r = rival move
		// m = my move
		char r = m == 'O' ? 'X' : 'O';
		char[] rend = new char[target];
		char[] mend = new char[target];
		for (int i = 0; i < target; i++) {
			rend[i] = r;
			mend[i] = m;
		}

		int itercnt = target;

		Set<String> rcheckset = new HashSet<String>();
		Set<String> mcheckset = new HashSet<String>();

		String mend_str = new String(mend);
		String rend_str = new String(rend);

		mcheckset.add(mend_str);
		rcheckset.add(rend_str);

		// my part weight
		System.out.println("checkstr");
		double cur_punish = max_punish;
		while (itercnt >= 1) {

			// update mcheckset
			Set<String> mcheckset_tmp = new HashSet<String>();

			for (String check_str : mcheckset) {
				// System.out.println(check_str);

				for (int i = 0; i < target; i++) {
					StringBuilder strbuilder = new StringBuilder(check_str);
					if (strbuilder.charAt(i) != '-') {
						strbuilder.setCharAt(i, '-');
						mcheckset_tmp.add(strbuilder.toString());
					}
				}
			}
			mcheckset = mcheckset_tmp;

			cur_punish /= (target * mcheckset.size() + 1);

			// calculate eval
			for (String str : mcheckset) {
				for (int i = 0; i < board.length; i++) {
					String row = new String(board[i]);

					int index = 0;
					while (index != -1) {
						index = str.indexOf(str, index);
						if (index != -1) {
							sum += cur_punish;
							index += 1;
						}
					}
				}
			}
			itercnt--;
		}

		// System.out.println("checkstr enf");

		// rival part weight
		itercnt = target;

		cur_punish = max_punish;
		while (itercnt >= 1) {
			// update rcheckset
			Set<String> rcheckset_tmp = new HashSet<String>();
			for (String check_str : rcheckset) {
				for (int i = 0; i < target; i++) {
					StringBuilder strbuilder = new StringBuilder(check_str);
					if (strbuilder.charAt(i) != '-') {
						strbuilder.setCharAt(i, '-');
						rcheckset_tmp.add(strbuilder.toString());
					}
				}
			}
			rcheckset = rcheckset_tmp;

			cur_punish /= (4 * target * rcheckset.size() + 1);

			// calculate eval
			for (String str : rcheckset) {
				for (int i = 0; i < board.length; i++) {
					String row = new String(board[i]);
					int index = 0;
					while (index != -1) {
						index = str.indexOf(str, index);
						if (index != -1) {
							sum -= cur_punish;
							index += 1;
						}
					}

				}
			}
			itercnt--;
		}

		return sum;
	}

	public static double value(ChessBoard cb, double a, double b, int depth) {
		char winner = cb.check_win();

		if (winner != '-') {
			return winner == cb.mymove ? Double.MAX_VALUE : -Double.MAX_VALUE;
		} else if (depth == 0) {
			return eval(cb);
		}
		if (cb.mymove == cb.nextmove) {
			return max_value(cb, a, b, depth - 1);
		} else {
			return min_value(cb, a, b, depth - 1);
		}
	}

	public static double max_value(ChessBoard cb, double a, double b, int depth) {
		double v = -Double.MAX_VALUE;

		for (int i = 0; i < cb.N; i++) {
			for (int j = 0; j < cb.N; j++) {
				if (cb.board[i][j] == '-' && check_neighbor(cb, i, j, 1)) {
					cb.move(i, j);
					v = Math.max(v, value(cb, a, b, depth - 1));
					cb.regret(i, j);

					if (v >= b) {
						return v;
					}
					// ab[0] = Math.max(ab[0], v);
					a = Math.max(a, v);
				}
			}
		}
		return v;
	}

	public static double min_value(ChessBoard cb, double a, double b, int depth) {
		double v = Double.MAX_VALUE;

		for (int i = 0; i < cb.N; i++) {
			for (int j = 0; j < cb.N; j++) {
				if (cb.board[i][j] == '-' && check_neighbor(cb, i, j, 1)) {
					cb.move(i, j);
					v = Math.min(v, value(cb, a, b, depth - 1));
					cb.regret(i, j);

					if (v <= a) {
						return v;
					}
					b = Math.min(b, v);
				}
			}
		}
		return v;
	}

	public static boolean check_neighbor(ChessBoard cb, int x, int y, int r) {
		int xstart = x - r < 0 ? 0 : x - r;
		int xend = x + r >= cb.N ? cb.N - 1 : x + r;
		int ystart = y - r < 0 ? 0 : y - r;
		int yend = y + r >= cb.N ? cb.N - 1 : y + r;

		for (int i = xstart; i <= xend; i++) {
			for (int j = ystart; j <= yend; j++) {
				if (cb.board[i][j] != '-') {
					return true;
				}
			}
		}
		return false;
	}

	public static void print(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) throws Exception {

		char[][] b = { { 'X', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
				{ '-', 'O', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
				{ '-', '-', 'X', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
				{ '-', '-', '-', 'O', '-', '-', '-', '-', '-', '-', '-', '-' },
				{ '-', '-', '-', '-', 'O', '-', '-', '-', '-', '-', '-', '-' },
				{ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
				{ '-', '-', '-', '-', '-', '-', '-', '-', '-', 'O', '-', 'O' },
				{ '-', '-', '-', '-', 'X', '-', '-', '-', '-', 'O', '-', 'X' },
				{ '-', '-', '-', 'O', '-', '-', '-', '-', '-', 'O', '-', 'X' },
				{ '-', '-', 'O', '-', '-', '-', '-', '-', '-', 'O', 'X', 'X' },
				{ '-', 'X', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
				{ 'X', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' } };

		ChessBoard cb = new ChessBoard(b, 12, 6, 'X', false);

		Play.play(cb, 2, "00");

	}

}
