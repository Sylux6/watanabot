package modules.blindtest;

import java.util.HashMap;
import java.util.Map;

import core.CommandHandler;
import modules.AbstractModule;
import modules.music.GuildMusicManager;
import modules.music.MusicModule;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import utils.MessageUtils;

public class BlindtestModule extends AbstractModule {

    private final Map<Long, BlindtestInstance> instances;

    public BlindtestModule() {
	super();
	instances = new HashMap<>();
    }

    @Override
    public void populate() {
	commands.put("abort", (event, args) -> {
	    BlindtestInstance instance = getBlindtestInstance(event.getGuild());
	    MusicModule musicModule = (MusicModule) CommandHandler.moduleMap.get("music");
	    GuildMusicManager musicManager = musicModule.getGuildAudioPlayer(event.getGuild());

	    Member owner = event.getGuild().getMember(instance.getOwner());

	    if (owner != null && instance.getOwner().getIdLong() != event.getAuthor().getIdLong()
		    && owner.getOnlineStatus() != OnlineStatus.OFFLINE) {
		MessageUtils.sendMessage(event.getChannel(), "You're not the owner of the game");
		return;
	    }
	    musicManager.player.destroy();
	    MessageUtils.editMessage(instance.getmainMessage(), instance.abortEmbed().build());
	    instances.remove(Long.parseLong(event.getGuild().getId()));
	    event.getGuild().getAudioManager().closeAudioConnection();
	    MessageUtils.sendMessage(event.getChannel(), "Game aborted");
	});

	commands.put("prepare", (event, args) -> {
	    BlindtestInstance instance = getBlindtestInstance(event.getGuild());

	    MusicModule musicModule = (MusicModule) CommandHandler.moduleMap.get("music");
	    GuildMusicManager musicManager = musicModule.getGuildAudioPlayer(event.getGuild());
	    AudioManager audioManager = event.getGuild().getAudioManager();
	    
	    // Check if a blindtest is already running
	    if (instance != null) {
		MessageUtils.sendMessage(event.getChannel(), "A blindtest game is already running");
		return;
	    }

	    // Check if bot is not currently in use
	    if (audioManager.isConnected() || audioManager.isAttemptingToConnect()) {
		MessageUtils.sendMessage(event.getChannel(), "I am already in a voice channel. Stealing is bad!");
		return;
	    }


	    // Bot is playing music
	    if (musicManager.player.getPlayingTrack() != null) {
		MessageUtils.sendMessage(event.getChannel(), "I'm currently playing music");
		return;
	    }
	    
	    // Users VoiceChannel
	    if (!event.getMember().getVoiceState().inVoiceChannel()) {
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " you are not in a voice channel");
		return;
	    }

	    if (args.size() < 2) {
		MessageUtils.sendMessage(event.getChannel(), "Score limit is missing : prepare <limit>");
		return;
	    }
	    
	    instance = new BlindtestInstance(event.getAuthor(), Integer.valueOf(args.get(1)), event.getChannel());
	    instances.put(Long.parseLong(event.getGuild().getId()), instance);
	    instance.setState(BlindtestState.PREPARING);
	    audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
	});

	commands.put("join", (event, args) -> {
	    BlindtestInstance instance = getBlindtestInstance(event.getGuild());
	    
	    if (instance == null) {
		MessageUtils.sendMessage(event.getChannel(), "No game found");
		return;
	    }
	   
	    if (!MusicModule.isInVoiceChannel(event.getMember(), event.getGuild().getAudioManager())) {
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " you are not in the voice channel");
		return;
	    }
	    if (instance.getState() != BlindtestState.PREPARING && instance.getState() != BlindtestState.SCORING) {
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " you can not signup");
		return;
	    }
	    if (instance.addPlayer(event.getAuthor())) {
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " you're in (> ᴗ •)ゞ");
		MessageUtils.editMessage(instance.getmainMessage(), instance.updateEmbedPreparation().build());
	    }
	    else
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " you're already in");
	});

	commands.put("leave", (event, args) -> {
	    BlindtestInstance instance = getBlindtestInstance(event.getGuild());
	    
	    if (instance == null) {
		MessageUtils.sendMessage(event.getChannel(), "No blindtest game found");
		return;
	    }
	    if (event.getAuthor().equals(instance.getOwner())) {
		instance.removePlayer(event.getAuthor());
		MessageUtils.editMessage(instance.getmainMessage(), instance.updateEmbedPreparation().build());
		if (instance.getOwner() == null) {
		    MusicModule musicModule = (MusicModule) CommandHandler.moduleMap.get("music");
		    GuildMusicManager musicManager = musicModule.getGuildAudioPlayer(event.getGuild());
		    // abort behaviour
		    musicManager.player.destroy();
		    instances.remove(Long.parseLong(event.getGuild().getId()));
		    event.getGuild().getAudioManager().closeAudioConnection();
		    MessageUtils.sendMessage(event.getChannel(), "No players left : game aborted");
		}
		else {
		    MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor())
			    + " is out, the new owner is " + MessageUtils.mentionAt(instance.getOwner()));
		}
	    }
	    else if (instance.removePlayer(event.getAuthor()))
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " is out");
	    else
		MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " already out");
	    
	});
	
	commands.put("start", (event, args) -> {
	    BlindtestInstance instance = getBlindtestInstance(event.getGuild());
	    
	    if (instance == null) {
		MessageUtils.sendMessage(event.getChannel(), "No blindtest game found");
		return;
	    }
	    
	    if (instance.getState() != BlindtestState.PREPARING) {
		MessageUtils.sendMessage(event.getChannel(), "Blindtest is already running");
	    }
	    
	    instance.setState(BlindtestState.PLAYING);
	    
	    
	});
    }

    public synchronized BlindtestInstance getBlindtestInstance(Guild guild) {
	long guildId = Long.parseLong(guild.getId());
	BlindtestInstance blindtestInstance = instances.get(guildId);
	return blindtestInstance;
    }

    @Override
    public String getName() {
	return "(b)lindtest";
    }

}
