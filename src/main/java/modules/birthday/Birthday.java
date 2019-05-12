package modules.birthday;

import db.model.Member;
import db.model.Settings;
import modules.AbstractModule;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.BotUtils;
import utils.DBUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Birthday extends AbstractModule {
    @Override
    public void populate() {
        commands.put("set", (event, args) -> {
            if (args.size() < 2) {
                BotUtils.sendMessage(event.getChannel(), "Please give your birthday following this format: MM/dd");
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
            try {
                Date date = formatter.parse(args.get(1));
                Member member = new Member(event.getAuthor().getIdLong(),
                        event.getGuild().getIdLong(), date);
                ArrayList res = DBUtils.query("select id from member where userid = "
                        + event.getAuthor().getId() + " and guildid = " + event.getGuild().getId());
                if (res.size() > 0) {
                    member.setId((Integer) res.get(0));
                }
                DBUtils.saveOrUpdate(member);
                BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Your birthday is set to " + new SimpleDateFormat("MMM dd",
                        Locale.ENGLISH).format(date));
            } catch (ParseException e) {
                BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Cannot get your birthday, please give your birthday " +
                        "following this format: MM/dd");
            }
        });

        commands.put("remove", (event, args) -> {
            Member member = new Member(event.getAuthor().getIdLong(), event.getGuild().getIdLong(), null);
            ArrayList res = DBUtils.query("select id from member where userid = "
                    + event.getAuthor().getId() + " and guildid = " + event.getGuild().getId());
            if (res.size() > 0) {
                member.setId((Integer) res.get(0));
            }
            DBUtils.saveOrUpdate(member);
            BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Your birthday has been removed");
        });

        commands.put("get", (event, args) -> {
            // get own birthday
            if (args.size() < 2) {
                ArrayList res = DBUtils.query("select birthday from member where userid = "
                        + event.getAuthor().getId() + " and guildid = " + event.getGuild().getId());
                if (res.size() > 0 && res.get(0) != null) {
                    BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Your birthday is on " +
                            new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(res.get(0)));
                } else {
                    BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "You didn't set your birthday");
                }
            } else {
                args.remove(0);
                String username = String.join(" ", args);
                List<net.dv8tion.jda.core.entities.Member> l = new ArrayList<>();
                l.addAll(event.getGuild().getMembersByName(username, true));
                l.addAll(event.getGuild().getMembersByEffectiveName(username, true));
                if (l.size() >  0) {
                    ArrayList res = DBUtils.query("select birthday from member where userid = "
                            + l.get(0).getUser().getId() + " and guildid = " + event.getGuild().getId());
                    if (res.size() > 0 && res.get(0) != null) {
                        BotUtils.sendMessage(event.getChannel(), l.get(0).getEffectiveName() + " birthday is on " +
                                new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(res.get(0)));
                    } else {
                        BotUtils.sendMessage(event.getChannel(), l.get(0).getEffectiveName() + " didn't set a birthday");
                    }
                } else {
                    BotUtils.sendMessage(event.getChannel(), "Cannot find the user in this server");
                }
            }
        });

        commands.put("setchannel", (event, args) -> {
            if (!event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "You have no permission to set a birthday channel");
                return;
            }
            Settings settings = new Settings(event.getGuild().getIdLong(), event.getChannel().getIdLong());
            ArrayList res = DBUtils.query("select id from settings where guildid = "
                    + event.getGuild().getId());
            if (res.size() > 0) {
                settings.setId((Integer) res.get(0));
            }
            DBUtils.saveOrUpdate(settings);
            BotUtils.sendMessage(event.getChannel(), "Birthdays will be announced in " + BotUtils.linkTextChannel(event.getChannel()) + " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)");
        });

        commands.put("getchannel", (event, args) -> {
            ArrayList res = DBUtils.query("select birthdaychannelid from settings where guildid = "
                    + event.getGuild().getId());
            TextChannel channel = event.getGuild().getTextChannelById(res.get(0).toString());
            if (res.size() == 0 || channel == null) {
                BotUtils.sendMessage(event.getChannel(), "Birthday channel is not set");
                return;
            }
            BotUtils.sendMessage(event.getChannel(), "Birthdays are announced in " + BotUtils.linkTextChannel(channel));
        });
    }

    @Override
    public String getName() {
        return "birthday";
    }
}
