package modules.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import modules.AbstractModule;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import utils.MessageUtils;

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
			MessageUtils.sendMessage(event.getChannel(), "I am already in a voice channel. Stealing is bad!");
			return;
	    }
	    audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
	    MessageUtils.sendMessage(event.getChannel(), "Ohayousoro!~ (> ᴗ •)ゞ");
	    
	});

	commands.put("leave", (event, args) -> {
	    event.getGuild().getAudioManager().closeAudioConnection();
	    getGuildAudioPlayer(event.getGuild()).player.destroy();
	    MessageUtils.sendMessage(event.getChannel(), "Bye bye!~ (> ᴗ •)ゞ");
	});

	commands.put("pause", (event, args) -> {
	    if (!isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
			MessageUtils.sendMessage(event.getChannel(), "You must be in the voice channel");
			return;
	    }
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.player.setPaused(true);
	    MessageUtils.sendMessage(event.getChannel(), "Pause...");
	});

	commands.put("resume", (event, args) -> {
	    if (!isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
			MessageUtils.sendMessage(event.getChannel(), "You must be in the voice channel");
		return;
	    }
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.player.setPaused(false);
	    MessageUtils.sendMessage(event.getChannel(), "... Resume");
	});

	commands.put("volume", (event, args) -> {
	    if (!isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
			MessageUtils.sendMessage(event.getChannel(), "You must be in the voice channel");
		return;
	    }
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    if (args.size() < 2) {
			// Setting the volume to 50 by default
			musicManager.player.setVolume(50);
			MessageUtils.sendMessage(event.getChannel(), "Volume set to 50%");
			return;
	    }
	    try {
			int volume = Integer.valueOf(args.get(1));
			musicManager.player.setVolume(volume);
			MessageUtils.sendMessage(event.getChannel(), "Volume set to " + volume + "%");
	    } catch (NumberFormatException e) {
			musicManager.player.setVolume(50);
			MessageUtils.sendMessage(event.getChannel(), "Unknown value: volume set to 50%");
	    }
	});

	commands.put("clear", (event, args) -> {
	    if (!isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
			MessageUtils.sendMessage(event.getChannel(), "You must be in the voice channel");
			return;
	    }
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.scheduler.purgeQueue();
	    MessageUtils.sendMessage(event.getChannel(), "Tracklist has been cleared");
	});

	commands.put("queue", (event, args) -> {
	    if (!isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
			MessageUtils.sendMessage(event.getChannel(), "You must be in the voice channel");
			return;
	    }
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    AudioManager audioManager = event.getGuild().getAudioManager();

	    // Check if bot is in voiceChannel
	    if (!audioManager.isConnected()) {
			MessageUtils.sendMessage(event.getChannel(), "I am not in a voice channel. Please make me join you.");
			return;
	    }

	    if (args.size() < 2) {
			MessageUtils.sendMessage(event.getChannel(), "No music found");
			return;
	    }
	    
	    playerManager.loadItemOrdered(musicManager, args.get(1),
		    new AudioHandler(musicManager, event.getChannel(), args.get(1)));
	});
	
	commands.put("play", (event, args) -> {
	    if (!isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
			MessageUtils.sendMessage(event.getChannel(), "You must be in the voice channel");
			return;
	    }
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    AudioManager audioManager = event.getGuild().getAudioManager();

	    // Check if bot is in voiceChannel
	    if (!audioManager.isConnected()) {
			MessageUtils.sendMessage(event.getChannel(), "I am not in a voice channel. Please make me join you.");
			return;
	    }

	    if (args.size() == 1 && musicManager.scheduler.getTracklist().size() > 1) {
			musicManager.player.startTrack(musicManager.scheduler.getTracklist().get(0), true);
			return;
	    }
	    else if (args.size() < 2) {
			MessageUtils.sendMessage(event.getChannel(), "No music found");
			return;
	    }
	    
	    musicManager.player.stopTrack();
	    musicManager.scheduler.purgeQueue();
	    playerManager.loadItemOrdered(musicManager, args.get(1),
		    new AudioHandler(musicManager, event.getChannel(), args.get(1)));
	});

	commands.put("stop", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.player.stopTrack();
	    MessageUtils.sendMessage(event.getChannel(), "Stop playing");
	});

	commands.put("next", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.scheduler.nextTrack();
	    MessageUtils.sendMessage(event.getChannel(),
		    "Playing next track: " + musicManager.player.getPlayingTrack().getInfo().title);
	});

	commands.put("list", (event, args) -> {
	    StringBuilder message = new StringBuilder("**Tracklist:**\n");
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    List<AudioTrack> tracklist = musicManager.scheduler.getTracklist();
	    int i = 0;
	    for (AudioTrack t : tracklist) {
			if (i == 5) {
				message.append("and **" + (tracklist.size() - i) + "** more...");
				break;
			}
			message.append("**" + (i + 1) + ".** " + t.getInfo().title + "\n");
			i++;
	    }
	    MessageUtils.sendMessage(event.getChannel(), message.toString());
	});

	commands.put("shuffle", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    musicManager.scheduler.shuffle();
	    MessageUtils.sendMessage(event.getChannel(), "Tracklist has been shuffled");
	});
	
	commands.put("now", (event, args) -> {
	    GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
	    AudioTrack now = musicManager.player.getPlayingTrack();
	    MessageUtils.sendMessage(event.getChannel(), "You are listening to **"+now.getInfo().title+"**");
	});

    }
    
    static public boolean isInVoiceChannel(Member author, AudioManager audioManager) {
		if (author.getVoiceState().getChannel() == null || audioManager.getConnectedChannel() == null)
			return false;
		return author.getVoiceState().getChannel().equals(audioManager.getConnectedChannel());
    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManager.player.setVolume(50);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
    }

    @Override
    public String getName() {
	return "(m)usic";
    }

}
