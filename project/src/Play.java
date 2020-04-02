import java.util.HashMap;

public class Play {
    public static void play(ChessBoard cb,int depth, String gameId) {
    	
        double max = -Double.MAX_VALUE;
        int movei = 0,movej = 0;
        boolean checked = false;

        System.out.println("== Start Search ==");
        if(cb.nomove) {
        	   RpcMove.Move(gameId, cb.N/2, cb.N/2);
        	   return;
        }

        for(int i=0;i<cb.N;i++) {
            for(int j=0;j<cb.N;j++) {
                if(cb.board[i][j] == '-' && Minimax.check_neighbor(cb,i,j,1)) {
                    //double[] ab = {-Double.MAX_VALUE,Double.MAX_VALUE};
                    cb.move(i, j);
                    double v = Minimax.value(cb, -Double.MAX_VALUE,Double.MAX_VALUE, depth);
                    if(max <= v) {
                        max = v;
                        movei = i;
                        movej = j;
                        checked = true;
                    }
                    cb.regret(i, j);
                }
            }
        }

        System.out.println("== Searc¡¤	h End ==");

        if(checked) {
            System.out.print("\n");
            System.out.print(movei);
            System.out.print(",");
            System.out.println(movej);
            System.out.println(max);
            //RpcMove.Move(gameId, movei, movej);
        }else {
            System.out.println("NO MOVES");
        }
    }

    public static void main(String[] args) {

        RpcMove rpc = new RpcMove();

        HashMap<String,Character> mymove_map = new HashMap<String,Character>();//

        String gameId = "650";
        System.out.println(gameId);
        while(true) {

            ChessBoard cb = RpcMove.GetBoardString(gameId);
            
            if(cb == null) {
                System.out.println("Game End");
                break;
            }
            
            Minimax.print(cb.board);

            if(mymove_map.containsKey(gameId)) {
                cb.mymove = mymove_map.get(gameId);
            }else {
                mymove_map.put(gameId, cb.mymove);//
            }

            if(cb.mymove != cb.nextmove) {
            	System.out.println("Rival's Turn Now");
                continue;
            }
            
            play(cb, 4, gameId);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
