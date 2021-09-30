package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import backendLogic.Player;
import buttons.AttackButton;
import buttons.ButtonBase;
import buttons.TransferButton;
import org.jetbrains.annotations.NotNull;
import playground.TileMap;
import playground.TileMenu;

public abstract class TileBase {
    private Color color, activColor, fontColor;
    static public final Color blankColor = Color.LIGHT_GRAY.darker();
    private Polygon body;
    protected int side;
    private final TileMap map;
    protected int value = 1;
    protected boolean isMenuOpen = false;
    protected TileMenu menu;
    private boolean isActive = false;
    private static Font font;
    private static int fontSize;
    private Player owner = null;
    public final int i, j;

    protected int maxValue = 99;
    protected float protection = 1;
    protected float captureMulti = 0.5f;
    protected float attackMulti = 1;

    public TileBase(int x, int y, int length, Color color, int i, int j, TileMap board) {
        map = board;

        resize(length);
        setPos(x, y);
        setColor(color);

        this.i = i;
        this.j = j;
    }

    public TileBase(@NotNull TileBase t) {
        this(t.getCenter().x, t.getCenter().y, t.getSide(), t.color, t.i, t.j, t.getMap());
        if (t.owner != null)
            changeOwner(t.getOwner());
    }

    public TileMap getMap() {
        return map;
    }

    public int getValue() {
        return value;
    }

    public float getAttackMulti() {
        return attackMulti;
    }

    public float getProtection() {
        return protection;
    }

    public float getCaptureMulti() {
        return captureMulti;
    }

    public Player getOwner() {
        return owner;
    }

    public Color getColor() {
        return color;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public Point getCenter() {
        return new Point(body.xpoints[0] - side, body.ypoints[0]);
    }

    public int getSide() {

        return side;
    }

    public void setFontSize() {
        fontSize = side / 3;
        font = new Font("TimesRoman", Font.PLAIN, fontSize);
    }

    public void setValue(int x) {
        value = x;
        if (value > maxValue)
            value = maxValue;
        else if (value < 0) {
            value = 0;
        }
    }
    public void setColor(Color in) {
        color = in;
        activColor = new Color(Math.min(color.getRed() + 60, 255),
                Math.min(color.getGreen() + 60, 255),
                Math.min(color.getBlue() + 60, 255));
        fontColor = color.darker();
    }
    public void setPos(int x, int y) {
        int[] xx = new int[6];
        int[] yy = new int[6];
        for (int i = 0; i < 6; i++) {
            xx[i] = (int) (x + (Math.cos(Math.PI / 3 * i) * side));
            yy[i] = (int) (y + (Math.sin(Math.PI / 3 * i) * side));
        }
        body = new Polygon(xx, yy, 6);
        if (isMenuOpen) {
            menu.setPos();
        }
    }
    public void setSize() {
        menu.setSize();
    }

    public ButtonBase tryPressingButton(Point p) {

        if (isMenuOpen()) {
            for (int i = 0; i < menu.getQuantity(); i++) {
                if (menu.getButton(i).contains(p)) {
                    return menu.getButton(i);
                }
            }
        }
        return null;
    }

    public boolean contains(Point p) {
        return body.contains(p);
    }
    public boolean isMenuOpen() {
        return isMenuOpen;
    }


    public void changeValue(int x) {
        value += x;
        if (value > maxValue)
            value = maxValue;
        else if (value < 0) {
            value = 0;
        }
    }


    public void changeOwner(Player newOwner) {
        Player oldOwner = owner;
        newOwner.changeTilesNumber(1);
        owner = newOwner;
        if (oldOwner != null) {
            oldOwner.changeTilesNumber(-1);
            if (countSurroundings(b -> b.owner == oldOwner) == 0)
                oldOwner.loseSight(i, j);
            applyToSurroundings((a) -> {
                if (((a.owner != oldOwner) && 0 == a.countSurroundings(b -> b.owner == oldOwner)))
                    oldOwner.loseSight(a.i, a.j);
            });

        }
        owner.find(i, j);
        setColor(owner.getColor());
        applyToSurroundings((a) -> owner.find(a.i, a.j));

    }

    public void drawMain(Graphics2D g) {
        if (isActive) {
            g.setColor(activColor);
        } else {
            g.setColor(color);
        }
        g.fill(body);
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(side / 20f));
        g.draw(body);
        drawValue(g);
    }

    public void drawBlank(Graphics2D g) {
        g.setColor(blankColor);
        g.fill(body);
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(side / 20f));
        g.draw(body);
    }

    public void drawInterface(Graphics2D g) {
        if (isMenuOpen)
            menu.draw(g);
    }

    public void drawValue(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(font);
        g.drawString(Integer.toString(value),
                (int) (getCenter().x - (float) fontSize / 3.8f * Integer.toString(value).length()),
                (int) (getCenter().y + fontSize / 2.5));
    }


    public void resize(float length) {
        side = (int) length;
    }


    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

    public void applyToSurroundings(TileMap.I myMethodsInterface) {
        boolean a = false, b = false;
        if (j + 1 < TileMap.rows) {
            myMethodsInterface.myMethod(getMap().board(i, j + 1));
            if (i + 1 < TileMap.columns) {
                myMethodsInterface.myMethod(getMap().board(i + 1, j));
                a = true;
                if (i % 2 != 0) {
                    myMethodsInterface.myMethod(getMap().board(i + 1, j + 1));
                }
            }

            if (i - 1 >= 0) {
                myMethodsInterface.myMethod(getMap().board(i - 1, j));
                b = true;
                if (i % 2 != 0) {
                    myMethodsInterface.myMethod(getMap().board(i - 1, j + 1));
                }
            }
        }

        if (j - 1 >= 0) {
            myMethodsInterface.myMethod(getMap().board(i, j - 1));

            if (i + 1 < TileMap.columns) {
                if (!a)
                    myMethodsInterface.myMethod(getMap().board(i + 1, j));
                if (i % 2 == 0) {
                    myMethodsInterface.myMethod(getMap().board(i + 1, j - 1));
                }
            }

            if (i - 1 >= 0) {
                if (!b)
                    myMethodsInterface.myMethod(getMap().board(i - 1, j));
                if (i % 2 == 0) {
                    myMethodsInterface.myMethod(getMap().board(i - 1, j - 1));
                }
            }
        }
    }

    public int countSurroundings(J myMethodsInterface) {

        int count = 0;
        boolean a = false, b = false;
        if (j + 1 < TileMap.rows) {
            if (myMethodsInterface.myMethod(getMap().board(i, j + 1)))
                count++;

            if (i + 1 < TileMap.columns) {
                if (myMethodsInterface.myMethod(getMap().board(i + 1, j))) {
                    count++;
                    a = true;
                }

                if (i % 2 != 0) {
                    if (myMethodsInterface.myMethod(getMap().board(i + 1, j + 1)))
                        count++;

                }
            }

            if (i - 1 >= 0) {
                if (myMethodsInterface.myMethod(getMap().board(i - 1, j))) {
                    count++;
                    b = true;
                }
                if (i % 2 != 0) {
                    if (myMethodsInterface.myMethod(getMap().board(i - 1, j + 1)))
                        count++;
                }
            }
        }


        if (j - 1 >= 0) {
            if (myMethodsInterface.myMethod(getMap().board(i, j - 1)))
                count++;


            if (i + 1 < TileMap.columns) {
                if (!a && myMethodsInterface.myMethod(getMap().board(i + 1, j))) {
                    count++;
                }
                if (i % 2 == 0) {
                    if (myMethodsInterface.myMethod(getMap().board(i + 1, j - 1)))
                        count++;

                }
            }

            if (i - 1 >= 0) {
                if (!b && myMethodsInterface.myMethod(getMap().board(i - 1, j))) {
                    count++;
                }
                if (i % 2 == 0) {
                    if (myMethodsInterface.myMethod(getMap().board(i - 1, j - 1)))
                        count++;
                }
            }

        }

        return count;
    }
    public ArrayList<TileBase> getNeighbours() {
        ArrayList<TileBase> neigh = new ArrayList<>();
        boolean c = false, b = false;
        if (j + 1 < TileMap.rows) {
            neigh.add(map.board(i, j + 1));
            if (i + 1 < TileMap.columns) {
                neigh.add(map.board(i + 1, j));
                c = true;
                if (i % 2 != 0) {
                    neigh.add(map.board(i + 1, j + 1));
                }
            }
            if (i - 1 >= 0) {
                neigh.add(map.board(i - 1, j));
                b = true;
                if (i % 2 != 0) {
                    neigh.add(map.board(i - 1, j + 1));
                }
            }
        }

        if (j - 1 >= 0) {
            neigh.add(map.board(i, j - 1));
            if (i + 1 < TileMap.columns) {
                if (!c) {
                    neigh.add(map.board(i + 1, j));
                }
                if (i % 2 == 0) {
                    neigh.add(map.board(i + 1, j - 1));
                }
            }

            if (i - 1 >= 0) {
                if (!b) {
                    neigh.add(map.board(i - 1, j));
                }
                if (i % 2 == 0) {
                    neigh.add(map.board(i - 1, j - 1));
                }
            }
        }
        return neigh;
    }

    public void closeMenu() {
        menu = null;
        isMenuOpen = false;

    }


    public double distance(TileBase tile) {

        return Point.distance(getCenter().x, getCenter().y, tile.getCenter().x, tile.getCenter().y);
    }

    public void openMenu(Player player) {

        if (player == owner) {
            openFriendlyMenu(player);
        } else {
            openHostileMenu(player);
        }
    }

    void openHostileMenu(Player player) {
        isMenuOpen = true;
        menu = new TileMenu(this);
        menu.addButton(new AttackButton(this, player.getColor().darker()));

    }

    public void openFriendlyMenu(Player player)
    {
        isMenuOpen = true;
        menu = new TileMenu(this);
        menu.addButton(new TransferButton(this, Color.cyan.darker(), 0.5f));
        menu.addButton(new TransferButton(this, Color.cyan.darker(), 1));
    }

    abstract public void nextTurn();

    public void prepareToDelete(){}




    @FunctionalInterface
    public
    interface J {
        boolean myMethod(TileBase tile);
    }

}
