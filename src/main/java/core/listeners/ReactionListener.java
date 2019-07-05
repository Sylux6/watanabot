package core.listeners;

import modules.poll.SmashPassModule;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.BotUtils;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser() == BotUtils.bot.getSelfUser()) {
            return;
        }
        if (BotUtils.smashPassInstances.containsKey(event.getMessageId())) {
            SmashPassModule.addReaction(event);
        } else if (BotUtils.pollInstances.containsKey(event.getMessageId())) {
            BotUtils.pollInstances.get(event.getMessageId()).addVote(event);
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getUser() == BotUtils.bot.getSelfUser()) {
            return;
        }
        if (BotUtils.smashPassInstances.containsKey(event.getMessageId())) {
            SmashPassModule.removeReaction(event);
        }
        if (BotUtils.pollInstances.containsKey(event.getMessageId())) {
            BotUtils.pollInstances.get(event.getMessageId()).removeVote(event);
        }
    }
}
