package core.listeners;

import modules.poll.SmashPassModule;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BotUtils;


public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser() == BotUtils.bot.getSelfUser()) {
            return;
        }
        if (BotUtils.smashPassInstances.containsKey(event.getMessageId())) {
            SmashPassModule.addReaction(event);
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
    }
}
