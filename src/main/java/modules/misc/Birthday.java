package modules.misc;

import modules.AbstractModule;
import utils.BotUtils;
import utils.DBUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Birthday extends AbstractModule {
    @Override
    public void populate() {
        commands.put("set", (event, args) -> {
            if (args.size() < 2) {
                BotUtils.sendMessage(event.getChannel(), "Please give your birthday following this format: MM/dd");
            }
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
            try {
                Date date = formatter.parse(args.get(1));
                db.model.Member member = new db.model.Member(event.getAuthor().getIdLong(),
                        event.getGuild().getIdLong(), date);
                ArrayList l = DBUtils.query("select id from member where userid = "
                        + event.getAuthor().getId() + " and guildid = " + event.getGuild().getId());
                if (l.size() > 0) {
                    member.setId((Integer) l.get(0));
                }
                DBUtils.saveOrUpdate(member);
                BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " your birthday is set to " + new SimpleDateFormat("MMM dd",
                        Locale.ENGLISH).format(date));
            } catch (ParseException e) {
                BotUtils.sendMessage(event.getChannel(), "Cannot get your birthday, please give your birthday " +
                        "following this format: MM/dd");
            }
        });

        commands.put("get", (event, args) -> {

        });
    }

    @Override
    public String getName() {
        return "birthday";
    }
}
