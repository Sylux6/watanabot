package modules.blindtest;

import java.awt.Color;
import java.util.HashMap;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import utils.BotUtils;

public class BlindtestInstance {
    
    private BlindtestState state;
    HashMap<Long, Player> players; // list of players
    private User owner;
    private int limit;
    
    private Message mainMessage;
    private EmbedBuilder mainEmbed;
    private MessageChannel chan;
    
    public BlindtestInstance(User owner, int limit, MessageChannel chan) {
	this.players = new HashMap<>();
	this.state = BlindtestState.IDLE;
	this.owner = owner;
	this.players.put(Long.parseLong(owner.getId()), new Player(owner));
	this.limit = limit;
	this.chan = chan;
	this.mainEmbed = new EmbedBuilder();
	this.mainMessage = new MessageBuilder("Preparing a blindtest ...").sendTo(chan).complete();
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
    
    public EmbedBuilder getMainBuilder() {
        return mainEmbed;
    }
    
    public Message getmaibMessage() {
        return mainMessage;
    }
    
    // Adds a player to the game
    public boolean addPlayer(User user) {
	long userID = Long.parseLong(user.getId());
	if (players.containsKey(userID)) {
	    return false;
	}
	players.put(userID, new Player(user));
	return true;
    }
    
    // Removes a player from the game
    public boolean removePlayer(User user) {
	long userID = Long.parseLong(user.getId());
	if (!players.containsKey(userID)) {
	    return false;
	}
	players.remove(userID);
	// Electing a new owner
	if (user.equals(owner) && !players.isEmpty()) {
	    for(Long k : players.keySet()) {
		owner = players.get(k).getUser();
		break;
	    }
	}
	else if (players.isEmpty()) {
	    owner = null;
	}
	return true;
    }
    
    // Panel Message
//    public void sendMainMessage() {
//	mainMessage.editMessage(mainEmbed.build()).queue();
//    }
    
    public void updateEmbedPreparation() {
	mainEmbed.clear();
	StringBuilder playersList = new StringBuilder();
	for (Long k : players.keySet()) {
	    playersList.append(players.get(k).getUser().getName()+"\n");
	}
	mainEmbed.setColor(Color.CYAN)
	.setTitle("Blindtest")
	.appendDescription("Ohayousoro!~ (> ᴗ •)ゞ  I'm preparing a new blindtest game. Type `o7 b join` for playing")
	.addField("Leader", owner.getName(), false)
	.addField("Players", playersList.toString(), false);
	BotUtils.editMessage(mainMessage, mainEmbed.build());
	// TODO: game aborted case
    }
    
}
