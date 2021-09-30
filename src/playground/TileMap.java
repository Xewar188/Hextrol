package playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import backendLogic.InputLogic;
import backendLogic.Player;
import backendLogic.PlayerLogic;
import backendLogic.RoundLogic;
import tiles.Tile;
import tiles.TileBase;

public class TileMap {
    public static final int rows = 40, columns = 40;
    private int sideLength;
    private final int minLength;
    private final int maxLength;
    private final TileBase[][] board;
    private int xoff = 0, yoff = 0;
    private InputLogic input;
    private RoundLogic round;
    private PlayerLogic players;
    private final Rectangle initScreen;
    private int winner = 0;

    public TileMap(Rectangle s) {

        initScreen = s;
        sideLength = 70;
        minLength = 60;
        maxLength = 250;

        board = new TileBase[columns][rows];
       reset();
    }

    public RoundLogic getRound() {
        return round;
    }

    public PlayerLogic getPlayers() {
        return players;
    }

    public InputLogic getInputLogic() {
        return input;
    }

    public int getXoff() {
        return xoff;
    }

    public int getYoff() {
        return yoff;
    }

    public int getSide() {
        return sideLength;
    }

    public void setXoff(int a) {
        xoff = Math.min(a, 3 * getSide());
        xoff = (int) Math.max(xoff, -columns * sideLength * 1.5 + initScreen.width - 6 * sideLength);
    }

    public void setYoff(int a) {
        yoff = Math.min(a, 3 * getSide());
        yoff = (int) Math.max(yoff, -rows * sideLength * Math.sqrt(3) + initScreen.height - 6 * sideLength);
    }

    public void setSide(int a) {
        sideLength = a;
    }

    public void setPos(double x, double d) {
        xoff = (int) x;
        yoff = (int) d;
    }
    public void createNewPlayer(Player p, int startX, int startY, Rectangle size) {
        players.addPlayer(p);
        board[startX][startY].changeOwner(p);
        board[startX][startY].setValue(50);
        int lastXoff = (int) (size.width / 2 - sideLength - startX * (sideLength) * 1.5);
        int lastYoff = (int) (size.height / 2 - sideLength / 2 * Math.sqrt(3) - startY * sideLength * Math.sqrt(3));
        p.setParameters(lastXoff, lastYoff, sideLength);
    }

    public void replaceTile(TileBase tile, int i, int j) {
        board[i][j].prepareToDelete();
        board[i][j] = tile;
    }

    public void checkForWinner() {
        players.removePlayers();
        if (players.getPlayerNumber() == 1)
            winner = round.getCurPlayerNr() + 1;
    }
    public void nextTurn() {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++) {
                board[i][j].nextTurn();
            }
      checkForWinner();
    }

    public void draw(Graphics2D g) {

        int begc, endc, begr, endr; //calculating visible cells boundaries
        begc = (int) Math.max((-xoff) / 1.5 / sideLength - 1, 0);
        begr = (int) Math.max((-yoff - sideLength * Math.sqrt(3) / 2) / Math.sqrt(3) / sideLength, 0);
        endc = (int) Math.min((g.getClipBounds().width - xoff) / 1.5f / sideLength + 2, columns);
        endr = (int) Math.min((g.getClipBounds().height - yoff) / Math.sqrt(3) / sideLength + 2, rows);

        for (int i = begc; i < endc; i++)
            for (int j = begr; j < endr; j++) {
                if (round.getCurPlayer().found(i, j)) {
                    board[i][j].resize(sideLength);
                    board[i][j].setPos((int) (xoff + i * (1.5 * sideLength)),
                            (int) (yoff + sideLength * j * Math.sqrt(3) + i % 2 * sideLength * Math.sqrt(3) / 2));
                    board[i][j].drawMain(g);
                } else {
                    board[i][j].resize(sideLength);
                    board[i][j].setPos((int) (xoff + i * (1.5 * sideLength)),
                            (int) (yoff + sideLength * j * Math.sqrt(3) + i % 2 * sideLength * Math.sqrt(3) / 2));
                    board[i][j].drawBlank(g);
                }
            }
        if (input.isMenuOpen()) {
            input.curActiveTile().drawInterface(g);
        }
    }

    public void translate(int x, int y) {
        setXoff(xoff + x);
        setYoff(yoff + y);
    }


    public void resize(float x, float y, float scale) {
        if ((sideLength * scale < minLength && scale < 1) || (maxLength < sideLength * scale && scale > 1))
            return;

        sideLength *= scale;
        //moving tiles to minimise movement while changing size
        setPos((scale * ((float) xoff) + x * (1.0f - scale)), (((float) yoff) * scale + y * (1.0f - scale)));
        if (input.isMenuOpen()) {
            input.curActiveTile().setSize();
        }
    }

    public void activateTile(MouseEvent e, Window c) {


        Dimension screenSize = c.getSize();
        int begc, endc, begr, endr; //calculating visible cells boundaries
        begc = (int) Math.max((-xoff) / 1.5 / sideLength - 1, 0);
        begr = (int) Math.max((-yoff - sideLength * Math.sqrt(3) / 2) / Math.sqrt(3) / sideLength, 0);
        endc = (int) Math.min((screenSize.width - xoff) / 1.5f / sideLength + 2, columns);
        endr = (int) Math.min((screenSize.height - yoff) / Math.sqrt(3) / sideLength + 2, rows);


        boolean hasClicked = input.tryPressingButton(round.getCurPlayer(), e.getPoint());
        if (!hasClicked)
            for (int i = begc; i < endc; i++)
                for (int j = begr; j < endr; j++) {
                    if (board[i][j].contains(e.getPoint())) {
                        if (round.getCurPlayer().found(i, j)) {
                            input.pressTile(i, j, round.getCurPlayer());
                            hasClicked = true;
                            break;
                        }

                    }
                }
        if (!hasClicked) {
            input.resetActions();
        }
        round.update();
    }

    public TileBase board(int x, int y) {
        return board[x][y];
    }

    public int getWinner() {
        return winner;
    }

    public void reset() {
        winner = 0;
        players = new PlayerLogic();
        input = new InputLogic(this);
        round = new RoundLogic(this);
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++) {

                board[i][j] = new Tile(0, 0, sideLength, Color.blue, i, j, this);
                board[i][j].setValue(Math.abs((i * (columns - i) + j * (rows - j)) / (columns + rows) * 10 + (int) (Math.random() * 10 - 5)));

            }
        board[0][0].setFontSize();

        createNewPlayer(new Player(Color.RED), columns - 2, rows - 2, initScreen);
        createNewPlayer(new Player(Color.YELLOW), columns - 1, rows - 1, initScreen);
        round.setCurPlayer(0);
    }

    @FunctionalInterface
    public
    interface I {

        void myMethod(TileBase tile);

    }

    public boolean areTilesConnected(TileBase tile, TileBase targ) {
        ArrayList<TileBase> closed = new ArrayList<>();
        PriorityQueue<TileBase> open = new PriorityQueue<>(1, new TileConnectComparator(tile));
        open.add(tile);
        while (!open.isEmpty()) {

            TileBase temp = open.remove();
            if (temp == targ)
                return true;
            ArrayList<TileBase> neigh = temp.getNeighbours();
            for (TileBase tileBase : neigh) {

                if (tileBase.getOwner() == tile.getOwner() && !closed.contains(tileBase)) {

                    open.add(tileBase);
                }
            }
            closed.add(temp);

        }
        return false;
    }

    static class TileConnectComparator implements Comparator<TileBase> {
        private final TileBase a;

        TileConnectComparator(TileBase a) {
            this.a = a;
        }
        @Override
        public int compare(TileBase o1, TileBase o2) {
            return Double.compare(o1.distance(a), o2.distance(a));
        }

    }
}