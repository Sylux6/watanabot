package modules.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class AudioHandler implements AudioLoadResultHandler {

    private TrackScheduler trackScheduler;

    public AudioHandler(TrackScheduler trackScheduler) {
	this.trackScheduler = trackScheduler;
    }

    public TrackScheduler getTrackScheduler() {
	return trackScheduler;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
	trackScheduler.queue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
	for (AudioTrack track : playlist.getTracks()) {
	    trackScheduler.queue(track);
	}
    }

    @Override
    public void noMatches() {
	// TODO Auto-generated method stub

    }

    @Override
    public void loadFailed(FriendlyException exception) {
	// TODO Auto-generated method stub

    }

}
