package modules.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.MessageChannel;
import utils.MessageUtils;

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
	MessageUtils.sendMessage(channel, "Adding to queue " + track.getInfo().title);
	musicManager.scheduler.queue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
	AudioTrack track = playlist.getSelectedTrack();

	if (track != null) {
	    // Users picked a track from a playlist: only load this one
	    track = playlist.getTracks().get(0);
	    musicManager.scheduler.queue(track);
	    MessageUtils.sendMessage(channel, "Adding to queue " + track.getInfo().title + " (first track of playlist "
		    + playlist.getName() + ")");
	} else {
	    // Load all the tracks from the playlist
	    for (AudioTrack t : playlist.getTracks())
		musicManager.scheduler.queue(t);

	    MessageUtils.sendMessage(channel, "Adding to queue all tracks from playlist " + playlist.getName());
	}
    }

    @Override
    public void noMatches() {
	MessageUtils.sendMessage(channel, "Nothing found by " + track);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
	MessageUtils.sendMessage(channel, "Could not play: " + exception.getMessage());
    }

}
