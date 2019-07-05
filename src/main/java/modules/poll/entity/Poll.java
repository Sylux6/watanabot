package modules.poll.entity;

import modules.poll.PollModule;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import utils.BotUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Poll {

    private Message message;
    private LocalDateTime createdDatetime;
    private Member owner;
    private int size;
    private int total;
    private boolean multiple;
    private String topic;
    private HashMap<Integer, String> choices;
    private HashMap<String, Set<Integer>> userResponses;

    public Poll(Member owner, int size, boolean multiple, String topic) {
        this.owner = owner;
        this.size = size;
        this.multiple = multiple;
        this.topic = topic;
        this.createdDatetime = LocalDateTime.now();
        this.choices = new HashMap<>();
        this.userResponses = new HashMap<>();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message messageId) {
        this.message = messageId;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public int getSize() {
        return size;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public String getTopic() {
        return topic;
    }

    public HashMap<Integer, String> getChoices() {
        return choices;
    }

    public HashMap<String, Set<Integer>> getUserResponses() {
        return userResponses;
    }

    public MessageEmbed toEmbed() {
        EmbedBuilder message = new EmbedBuilder();
        message.setTitle(topic + (!multiple ? "" : " (multiple choices allowed)"));
        message.setDescription("created by " + owner.getEffectiveName());
        message.setThumbnail(owner.getUser().getAvatarUrl());
        for (int i = 1; i < size+1; i++) {
            int count = 0;
            for (Set set : userResponses.values()) {
                if (set.contains(i)) {
                    count++;
                }
            }
            StringBuilder display = new StringBuilder();
            if (total != 0) {
                for (int j = 0; j < Math.round((float) count / total * 10); j++) {
                    display.append("#");
                }
            }
            message.addField(
                    i + ". " + choices.get(i),
                    total == 0 ? "(**0**)" : display.toString() + " (**" + count + "**)",
                    false);
        }
        return message.build();
    }

    public void reset() {
        total = 0;
        userResponses = new HashMap<>();
        message.clearReactions().queue();
        message = message.editMessage(toEmbed()).complete();
        for (int i = 1; i < size + 1; i++) {
            message.addReaction(PollModule.choiceToEmote.get(i)).queue();
        }
        message.addReaction("↩").queue();
    }

    synchronized public void addVote(MessageReactionAddEvent event) {
        if (event.getReactionEmote().getName().equals("↩")) {
            reset();
        }
        int userChoice = PollModule.emoteToChoice.getOrDefault(event.getReactionEmote().getName(), 0);
        if (userChoice == 0) return;
        if (!multiple) {
            total = Math.max(0, --total);
            userResponses.remove(event.getUser().getId());
        }
        userResponses.putIfAbsent(event.getUser().getId(), new HashSet<>());
        userResponses.get(event.getUser().getId()).add(userChoice);
        total++;
        BotUtils.editMessagePoll(message, toEmbed(), this);
    }

    synchronized public void removeVote(MessageReactionRemoveEvent event) {
        int userChoice = PollModule.emoteToChoice.getOrDefault(event.getReactionEmote().getName(), 0);
        if (userChoice == 0) return;
        if (userResponses.get(event.getUser().getId()).contains(userChoice)) {
            total = Math.max(0, --total);
        }
        userResponses.get(event.getUser().getId()).remove(userChoice);
        BotUtils.editMessagePoll(message, toEmbed(), this);
    }
}
