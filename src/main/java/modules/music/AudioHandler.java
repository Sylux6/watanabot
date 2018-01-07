package modules.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import core.BotUtils;
import net.dv8tion.jda.core.entities.MessageChannel;

public class AudioHandler implements AudioLoadResultHandler {

    private GuildMusicManager musicManager;
    private MessageChannel channel;
    private String track;

    public AudioHandler(GuildMusicManager musicManager, MessageChannel channel, String track) {
	super();
	this.musicManager = musicManager;
	this.channel = channel;
	this.track = track;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
	BotUtils.sendMessage(channel, "Adding to queue " + track.getInfo().title);
	musicManager.scheduler.queue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
	AudioTrack track = playlist.getSelectedTrack();

	if (track == null) {
	    track = playlist.getTracks().get(0);
	}

	BotUtils.sendMessage(channel,
		"Adding to queue " + track.getInfo().title + " (first track of playlist " + playlist.getName() + ")");

	musicManager.scheduler.queue(track);
    }

    @Override
    public void noMatches() {
	BotUtils.sendMessage(channel, "Nothing found by " + track);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
	BotUtils.sendMessage(channel, "Could not play: " + exception.getMessage());
    }

}
