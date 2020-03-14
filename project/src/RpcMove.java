import java.util.Iterator;

import org.json.JSONObject;

public class RpcMove {
	
	public String GetGame() {
		return RequestHelper.sendGet("type=myGames");
	}

	public String GetMoves(String gameId,int cnt) {
		return RequestHelper.sendGet("type=moves&gameId"+gameId+"count="+String.valueOf(cnt));
	}
	
	
	public ChessBoard GetBoardMap(String gameId) {
		String result = RequestHelper.sendGet("type=boardMap&gameId"+gameId);
		JSONObject json = new JSONObject(result);
		
		if(json.getString("code").equals("OK")) {
			JSONObject output_obj = (JSONObject) json.get("output");
			
			char[][] board= new char[20][20];
			for(int i=0;i<ChessBoard.X;i++) {
				for(int j=0;j<ChessBoard.Y;j++) {
					board[i][j] = '-';
				}
			}
			
			Iterator<String> sIterator = output_obj.keys();
			int cnto =0;
			int cntx =0;
			while(sIterator.hasNext()){
				String pos = sIterator.next();
				String[] pos1 = pos.split(",");
				String value = output_obj.getString(pos);
				board[Integer.valueOf(pos1[0])][Integer.valueOf(pos1[1])] = value.charAt(0);
				
				if(value.charAt(0)=='O') {
					cnto++;
				}else {
					cntx++;
				}
			}
			
			if(cnto == 0 && cntx == 0) {
				return new ChessBoard(board,'O',true);
			}else {
				return new ChessBoard(board, cnto<cntx? 'O':'X',false);
			}
			
			
		}else {
			System.out.print("Get Board Map Failed");
			return null;
		}
	}
	
	public String Move(String gameId,int move1,int move2) {
		String result = RequestHelper.sendPost("type=move&teamId=1206&gameId="+gameId+"&move="+String.valueOf(move1)+","+String.valueOf(move2));
		JSONObject json = new JSONObject(result);
		if(json.getString("code").equals("OK")) {
			return json.getString("moveId");
		}else {
			return "Move Failed";
		}
	}
	
	public String CreateGame(String rival) {
		String result = RequestHelper.sendPost("type=game&gameType=TTT&teamId1=1206&teamId2="+rival);
		JSONObject json = new JSONObject(result);
		if(json.getString("code").equals("OK")) {
			return json.getString("gameId");
		}else {
			return "Create Game Failed";
		}
	}
	
}
