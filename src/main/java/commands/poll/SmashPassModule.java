//package modules.poll;
//
//import modules.AbstractModule;
//import modules.poll.entity.SmashPass;
//import modules.poll.entity.SmashPassResponse;
//import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
//import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
//import utils.BotUtils;
//import utils.MessageUtils;
//
//public class SmashPassModule extends AbstractModule {
//
//    @Override
//    public void populate() {
//        getMapCommands().put("new", (event, args) -> {
//            args.remove(0);
//            SmashPass instance = new SmashPass(
//                    event.getMessage().getId(), event.getMember(), String.join(" ", args),
//                    event.getMessage().getAttachments().size() > 0
//            );
//            // smash
//            event.getMessage().addReaction("\u2705").queue();
//            // pass
//            event.getMessage().addReaction("\u274c").queue();
//            // hug
//            event.getMessage().addReaction("\ud83d\udc96").queue();
//            // stop
//            event.getMessage().addReaction("\ud83c\udd97").queue();
//            if (instance.hasAttachment()) {
//                instance.setAttachment(event.getMessage().getAttachments().get(0).getUrl());
//            }
//            BotUtils.INSTANCE.getSmashPassInstances().put(event.getMessageId(), instance);
//        });
//    }
//
//    @Override
//    public String getName() {
//        return "smash-pass";
//    }
//
//    static public void addReaction(MessageReactionAddEvent event) {
//        SmashPass instance = BotUtils.INSTANCE.getSmashPassInstances().get(event.getMessageId());
//        // Check if user has already voted
//        if (instance.getResponses().containsKey(event.getUser().getId())
//                && !event.getReactionEmote().getName().equals("\ud83c\udd97")) {
//            event.getReaction().removeReaction(event.getUser()).queue();
//            return;
//        } else {
//            switch (event.getReactionEmote().getName()) {
//                case "\u2705":
//                    // smash
//                    instance.getResponses().put(event.getMember(), SmashPassResponse.SMASH);
//                    break;
//                case "\u274c":
//                    // pass
//                    instance.getResponses().put(event.getMember(), SmashPassResponse.PASS);
//                    break;
//                case "\uD83D\uDC96":
//                    // hug
//                    instance.getResponses().put(event.getMember(), SmashPassResponse.HUG);
//                    break;
//                case "\ud83c\udd97":
//                    // stop
//                    if (event.getMember().equals(instance.getOwner())) {
//                        MessageUtils.INSTANCE.sendMessage(event.getChannel(), instance.toEmbed());
//                    }
//            }
//        }
//    }
//
//    static public void removeReaction(MessageReactionRemoveEvent event) {
//        SmashPass instance = BotUtils.INSTANCE.getSmashPassInstances().get(event.getMessageId());
//        instance.getResponses().remove(event.getUser().getId());
//    }
//}
