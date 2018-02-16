package modules.blindtest;

import java.util.HashMap;

import net.dv8tion.jda.core.entities.User;

public class BlindtestInstance {
    
    private BlindtestState state;
    HashMap<Long, Player> players;
    private User owner;
    
    public BlindtestInstance(User owner) {
	this.players = new HashMap<>();
	this.state = BlindtestState.IDLE;
	this.owner = owner;
	this.players.put(Long.parseLong(owner.getId()), new Player(owner));
    }

    public BlindtestState getState() {
        return state;
    }

    public void setState(BlindtestState state) {
        this.state = state;
    }

    public User getOwner() {
        return owner;
    }
    
    public boolean addPlayer(User user) {
	long userID = Long.parseLong(user.getId());
	if (players.containsKey(userID)) {
	    return false;
	}
	players.put(userID, new Player(user));
	return true;
    }
    
    public boolean removePlayer(User user) {
	long userID = Long.parseLong(user.getId());
	if (!players.containsKey(userID)) {
	    return false;
	}
	players.remove(userID);
	if (user.equals(owner) && !players.isEmpty()) {
	    for(Long l : players.keySet()) {
		owner = players.get(l).getUser();
		break;
	    }
	}
	return true;
    }
    
}
