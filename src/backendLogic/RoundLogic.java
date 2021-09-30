package backendLogic;

import playground.TileMap;

public class RoundLogic {
    private int curPlayerNr = 0, moves = 0;
    private Player curPlayer;
    private final TileMap map;

    public RoundLogic(TileMap m) {
        map = m;
    }

    public void changeMoves(int i) {
        moves += i;
        update();
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int i) {
        moves = i;
        update();
    }

    public Player getCurPlayer() {
        return curPlayer;
    }
    public int getCurPlayerNr() {
        return curPlayerNr;
    }

    public void setCurPlayer(int n) {
        curPlayer = map.getPlayers().getPlayer(n);
        moves = map.getPlayers().getPlayer(n).getMaxMoves();
        map.setXoff(curPlayer.getLastX());
        map.setYoff(curPlayer.getLastY());
        map.setSide(curPlayer.getLastSideLength());
    }

    public void update() {


        if (moves == 0) {
            getCurPlayer().setParameters(map.getXoff(), map.getYoff(), map.getSide());
            int prev = curPlayerNr;
            do {
                curPlayerNr++;
                if (curPlayerNr >= map.getPlayers().getPlayerNumber()) {
                    curPlayerNr = 0;
                }
            }
            while (map.getPlayers().checkPlayer(curPlayerNr));
            setCurPlayer(curPlayerNr);
            if (prev >= curPlayerNr)
                map.nextTurn();

        }

    }
}
