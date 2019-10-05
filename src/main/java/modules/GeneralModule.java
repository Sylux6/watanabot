package modules;

import core.CommandHandler;
import modules.picture.PictureModule;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.kodehawa.lib.imageboards.entities.Rating;
import utils.BotUtils;
import utils.EmbedUtils;
import utils.MessageUtils;

import java.util.ArrayList;


public class GeneralModule extends AbstractModule {

    @Override
    public void populate() {
        commands.put("test", (event, args) -> {
            MessageUtils.sendMessage(event.getChannel(), "You ran the test command with args: " + args);
        });

        commands.put("help", (event, args) -> {
            if (args.size() < 2) {
                MessageUtils.sendDM(event.getAuthor(), getHelp());
            } else {
                AbstractModule module = CommandHandler.moduleMap.get(args.get(1));
                if (module == null)
                    MessageUtils.sendMessage(event.getChannel(), args.get(1) + " module not found");
                else {
                    MessageUtils.sendDM(event.getAuthor(), module.getHelp());
                }
            }
        });

        commands.put("module", (event, args) -> {
            StringBuilder help = new StringBuilder("**Module list:**\n");
            for (String s : CommandHandler.moduleMap.keySet()) {
            help.append("- `" + s + "`\n");
            }
            MessageUtils.sendMessage(event.getChannel(), help.toString());
        });

        commands.put("roll", (event, args) -> {
            if (BotUtils.yousoroEmojiExists(event.getGuild()))
            MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " " + roll() + " "
                + BotUtils.getEmojiMessage(event.getGuild(), "yousoro"));
            else
            MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " " + roll() + "");
        });

        commands.put("lewd", (event, args) -> {
            MessageUtils.sendMessage(event.getChannel(),
                    EmbedUtils.buildEmbedImageOnly("https://puu.sh/Cszmq.jpg"));
        });

        commands.put("hentai", (event, args) -> {
            MessageUtils.sendMessage(event.getChannel(),
                EmbedUtils.buildEmbedImageOnly("https://puu.sh/CtsdM.png"));
        });

        commands.put("nya", (event, args) -> {
            PictureModule.getImage(event, "nekomimi", event.getTextChannel().isNSFW() ? Rating.EXPLICIT : Rating.SAFE);
        });

        commands.put("yousoro", (event, args) -> {
            PictureModule.getImage(event, "watanabe_you",
                event.getTextChannel().isNSFW() ? Rating.EXPLICIT : Rating.SAFE);
        });

        commands.put("choose", (event, args) -> {
            String p[] = {"Obviously", "I take", "The best would be"};
            int r1 = BotUtils.random.nextInt(p.length), r2 = BotUtils.random.nextInt(args.size()-1)+1;
            MessageUtils.sendMessage(event.getChannel(), p[r1]+" **"+args.get(r2)+"**");
        });

        commands.put("say", (event, args) -> {
            args.remove(0);
            MessageUtils.sendMessage(event.getChannel(), String.join(" ", args));
        });

        commands.put("getrole", (event, args) -> {
            args.remove(0);
            StringBuilder m = new StringBuilder();
            ArrayList<Role> role = (ArrayList<Role>) event.getGuild().getRolesByName(String.join(" ", args), true);
            if (role.isEmpty()) {
                m.append("**"+String.join(" ", args)+"** not found");
                MessageUtils.sendMessage(event.getChannel(), m.toString());
                return;
            }

            ArrayList<Member> l = (ArrayList<Member>) event.getGuild().getMembersWithRoles(role.get(0));
            m.append("List of **"+String.join(" ", args)+"** (**"+l.size()+"**):\n");

            for (Member u: l)
                m.append("- "+u.getUser().getName()+"#"+u.getUser().getDiscriminator()+"\n");

            if (l.size() > 10) {
                MessageUtils.sendMessage(event.getChannel(), "List of **"+String.join(" ", args)+"** (**"+l.size()+"**) sent in DM");
                MessageUtils.sendDM(event.getAuthor(), m.toString());
            }
            else {
                MessageUtils.sendMessage(event.getChannel(), m.toString());
            }
        });

        commands.put("screenshare", (event, args) -> {
            if (!event.getMember().getVoiceState().inVoiceChannel()) {
                MessageUtils.sendMessage(event.getChannel(), "Please join a voice channel");
                return;
            }
            MessageUtils.sendMessage(event.getChannel(),
                    "There you go for screensharing:\n" +
                            "<https://www.discordapp.com/channels/" + event.getGuild().getId() + "/"
                            + event.getMember().getVoiceState().getChannel().getId() + ">");
        });

        //////////////////////////////////////////////////

        commands.put("yousolewd", (event, args) -> {
            if (event.getGuild().getIdLong() != BotUtils.SRID) return;
            Role eventRole = event.getGuild().getRolesByName("Lewd", false).get(0);
            if (!event.getMember().getRoles().contains(eventRole)) {
            event.getGuild().addRoleToMember(event.getMember(), eventRole).queue();
            MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor())+" you're lewd >///< (**Lewd** role added)");
            }
            else {
            event.getGuild().removeRoleFromMember(event.getMember(), eventRole).queue();
            MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor())+" you're pure (**Lewd** role removed)");
            }
        });

        commands.put("joinevent", (event, args) -> {
            if (event.getGuild().getIdLong() != BotUtils.SRID) return;
            Role eventRole = event.getGuild().getRolesByName("Event", false).get(0);
            if (!event.getMember().getRoles().contains(eventRole)) {
            event.getGuild().addRoleToMember(event.getMember(), eventRole).queue();
            MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor())+" "+eventRole.getName()+" role added");
            }
        });

        commands.put("leaveevent", (event, args) -> {
            if (event.getGuild().getIdLong() != BotUtils.SRID) return;
            Role eventRole = event.getGuild().getRolesByName("Event", false).get(0);
            if (event.getMember().getRoles().contains(eventRole)) {
            event.getGuild().removeRoleFromMember(event.getMember(), eventRole).queue();
            MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor())+" "+eventRole.getName()+" role removed");
            }
        });

    }

    // Auxiliar functions:

    private int roll() {
		BotUtils.random.setSeed(System.currentTimeMillis());
		return Math.abs((BotUtils.random.nextInt() % 100)) + 1;
    }

    @Override
    public String getName() {
    	return "general";
    }

}
