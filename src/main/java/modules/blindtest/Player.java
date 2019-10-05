package modules.blindtest;


import net.dv8tion.jda.api.entities.User;

public class Player {
    
    private User user;
    private int score;
    
    public Player(User user) {
	this.user = user;
	this.score = 0;
    }

    public User getUser() {
        return user;
    }
    
    public int getScore() {
	return score;
    }

}
