package modules.poll.entity;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import utils.BotUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmashPass {

    private String attachment;
    private boolean hasAttachment;
    private String messageId;
    private Member owner;
    private String topic;
    private HashMap<Member, SmashPassResponse> responses;

    public SmashPass(String messageId, Member owner, String topic, boolean hasAttachment) {
        this.messageId = messageId;
        this.owner = owner;
        this.topic = topic;
        this.responses = new HashMap<>();
        this.hasAttachment = hasAttachment;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public boolean hasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public String getMessageId() {
        return messageId;
    }

    public Member getOwner() {
        return owner;
    }

    public String getTopic() {
        return topic;
    }


    public HashMap<Member, SmashPassResponse> getResponses() {
        return responses;
    }

    public MessageEmbed toEmbed() {
        BotUtils.smashPassInstances.remove(messageId);
        EmbedBuilder embed = new EmbedBuilder();
        if (!topic.isEmpty()) {
            embed.setTitle(topic);
        }
        embed.setThumbnail(owner.getUser().getAvatarUrl());
        embed.setDescription("created by " + owner.getEffectiveName());

        HashMap<SmashPassResponse, ArrayList<Member>> results = new HashMap<>();
        results.put(SmashPassResponse.SMASH, new ArrayList<>());
        results.put(SmashPassResponse.PASS, new ArrayList<>());
        results.put(SmashPassResponse.HUG, new ArrayList<>());
        for (Map.Entry<Member, SmashPassResponse> entry : responses.entrySet()) {
            results.get(entry.getValue()).add(entry.getKey());
        }
        for (SmashPassResponse val : SmashPassResponse.values()) {
            StringBuilder s  = new StringBuilder();
            for (Member m : results.get(val)) {
                s.append("- ").append(m.getEffectiveName()).append("\n");
            }
            embed.addField(val.getEmote() + " **" + val + "** (**"
                    + results.getOrDefault(val, new ArrayList<>()).size() + "**):", s.toString(), false);
        }
        if (hasAttachment()) {
            embed.setImage(getAttachment());
        }
        return embed.build();
    }
}
