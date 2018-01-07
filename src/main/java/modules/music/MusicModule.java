package modules.music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import core.BotUtils;
import modules.AbstractModule;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.AudioManager;

public class MusicModule extends AbstractModule {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public MusicModule() {
	super();
	musicManagers = new HashMap<>();
	playerManager = new DefaultAudioPlayerManager();
	AudioSourceManagers.registerRemoteSources(playerManager);
	AudioSourceManagers.registerLocalSource(playerManager);
    }

    @Override
    public void populate() {
	commands.put("join", (event, args) -> {
	    AudioManager audioManager = event.getGuild().getAudioManager();

	    // Check if bot is not currently in use
	    if (audioManager.isConnected() || audioManager.isAttemptingToConnect()) {
		BotUtils.sendMessage(event.getChannel(), "I am already in a voice channel. Stealing is bad!");
		return;
	    }
	    audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
	    BotUtils.sendMessage(event.getChannel(), "Ohayousoro!~ (> ᴗ •)ゞ");
	});

	commands.put("leave", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    AudioManager audioManager = event.getGuild().getAudioManager();
	    musicManager.player.destroy();
	    audioManager.closeAudioConnection();
	    BotUtils.sendMessage(event.getChannel(), "Bye bye!~ (> ᴗ •)ゞ");
	});

	commands.put("clear", (event, args) -> {
	    // TODO
	});

	commands.put("play", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    AudioManager audioManager = event.getGuild().getAudioManager();

	    // Check if url is here
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "I do not know what to play for you");
		return;
	    }

	    // Check if bot is in voiceChannel
	    if (!audioManager.isConnected()) {
		BotUtils.sendMessage(event.getChannel(), "I am not in a voice channel. Please make me join you.");
		return;
	    }

	    playerManager.loadItemOrdered(musicManager, args.get(1),
		    new AudioHandler(musicManager, event.getChannel(), args.get(1)));
	});

	commands.put("stop", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.player.stopTrack();
	    BotUtils.sendMessage(event.getChannel(), "Stop playing");
	});

	commands.put("next", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.scheduler.nextTrack();
	    BotUtils.sendMessage(event.getChannel(),
		    "Playing next track: " + musicManager.player.getPlayingTrack().getInfo().title);
	});

    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
	long guildId = Long.parseLong(guild.getId());
	GuildMusicManager musicManager = musicManagers.get(guildId);

	if (musicManager == null) {
	    musicManager = new GuildMusicManager(playerManager);
	    musicManagers.put(guildId, musicManager);
	}

	guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

	return musicManager;
    }

}
