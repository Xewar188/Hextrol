package backendLogic;

import java.util.ArrayList;

public class PlayerLogic {

    private final ArrayList<Player> players = new ArrayList<>();

    public PlayerLogic() {
    }

    public Player getPlayer(int nr) {
        return players.get(nr);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public int getPlayerNumber() {
        return players.size();
    }

    public void removePlayers() {
        players.removeIf(p -> p.getTilesNumber() == 0);
    }

    public boolean checkPlayer(int i) {
        return players.get(i).getTilesNumber() == 0;
    }
}
