package modules.music;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import core.BotUtils;
import modules.AbstractModule;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

public class MusicModule extends AbstractModule {

    private AudioPlayerManager playerManager;
    private Map<String, AudioHandler> audioPlayers;

    public MusicModule() {
	super();
	playerManager = new DefaultAudioPlayerManager();
	audioPlayers = new HashMap<>();
	AudioSourceManagers.registerRemoteSources(playerManager);

    }

    @Override
    public void populate() {
	commands.put("join", (event, args) -> {
	    IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild())
		    .getChannel();
	    // Check if bot is not currently in use
	    if (botVoiceChannel != null) {
		BotUtils.sendMessage(event.getChannel(), "I'm already in a voice channel. Stealing is bad!");
		return;
	    }
	    // Creating AudioPlayer
//	    AudioPlayer player = playerManager.createPlayer();
//	    TrackScheduler trackScheduler = new TrackScheduler(player);
//	    player.addListener(trackScheduler);
	    audioPlayers.put(event.getChannel().getStringID(), null);

	    IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();
	    if (userVoiceChannel == null)
		return;
	    userVoiceChannel.join();
	});

	commands.put("leave", (event, args) -> {
	    IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild())
		    .getChannel();
	    if (botVoiceChannel == null) {
		System.out.println("NULL");
		return;
	    }
	    
	    audioPlayers.remove(event.getGuild().getStringID());
	    botVoiceChannel.leave();

	});

	commands.put("play", (event, args) -> {
	    // No identifier song found
	    if (args.size() < 2)
		return;

	    IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild())
		    .getChannel();

	    if (botVoiceChannel == null) {
		BotUtils.sendMessage(event.getChannel(), "I'm not in a voice channel");
		return;
	    }


	    // Get the AudioPlayer object for the guild
	    AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());

	    // Find a song given the search term
	    File song = new File(args.get(1));

	    // Stop the playing track
	    audioP.clear();

	    // Play the found song
	    try {
		audioP.queue(song);
	    } catch (IOException | UnsupportedAudioFileException e) {
		BotUtils.sendMessage(event.getChannel(), "There was an issue playing that song.");
		e.printStackTrace();
	    }

	    BotUtils.sendMessage(event.getChannel(), "Now playing: " + song.getName());

	});

    }

}
