package modules.birthday;

import db.model.Member;
import modules.AbstractModule;
import utils.BotUtils;
import utils.DBUtils;

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
    }

    @Override
    public String getName() {
        return "birthday";
    }
}
