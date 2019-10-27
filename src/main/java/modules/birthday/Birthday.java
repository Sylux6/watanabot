package modules.birthday;

import db.model.Member;
import db.model.Settings;
import modules.AbstractModule;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import utils.BotUtils;
import utils.DBUtils;
import utils.MessageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Birthday extends AbstractModule {
    @Override
    public void populate() {
        commands.put("set", (event, args) -> {
            if (args.size() < 2) {
                MessageUtils.sendMessage(event.getChannel(),
                        "Please give your birthday following this format: MM/dd");
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
                MessageUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Your birthday is set to "
                        + new SimpleDateFormat("MMM dd",
                        Locale.ENGLISH).format(date));
            } catch (ParseException e) {
                MessageUtils.sendMessageAt(event.getChannel(), event.getAuthor(),
                        "Cannot get your birthday, please give your birthday "
                                + "following this format: MM/dd");
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
            MessageUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Your birthday has been removed");
        });

        commands.put("get", (event, args) -> {
            // get own birthday
            if (args.size() < 2) {
                ArrayList res = DBUtils.query("select birthday from member where userid = "
                        + event.getAuthor().getId() + " and guildid = " + event.getGuild().getId());
                if (res.size() > 0 && res.get(0) != null) {
                    MessageUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "Your birthday is on " +
                            new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(res.get(0)));
                } else {
                    MessageUtils.sendMessageAt(event.getChannel(), event.getAuthor(),
                            "You didn't set your birthday");
                }
            } else {
                args.remove(0);
                String username = String.join(" ", args);
                net.dv8tion.jda.api.entities.Member member = BotUtils.findMember(event.getGuild(), username);
                if (member != null) {
                    ArrayList res = DBUtils.query("select birthday from member where userid = "
                            + member.getUser().getId() + " and guildid = " + event.getGuild().getId());
                    if (res.size() > 0 && res.get(0) != null) {
                        MessageUtils.sendMessage(event.getChannel(), member.getEffectiveName()
                                + " birthday is on "
                                + new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(res.get(0)));
                    } else {
                        MessageUtils.sendMessage(event.getChannel(), member.getEffectiveName()
                                + " didn't set a birthday");
                    }
                } else {
                    MessageUtils.sendMessage(event.getChannel(), "Cannot find the user in this server");
                }
            }
        });

        commands.put("setchannel", (event, args) -> {
            if (!event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                MessageUtils.sendMessageAt(event.getChannel(), event.getAuthor(),
                        "You have no permission to set a birthday channel");
                return;
            }
            Settings settings = new Settings(event.getGuild().getIdLong(), event.getChannel().getIdLong());
            ArrayList res = DBUtils.query("select id from settings where guildid = "
                    + event.getGuild().getId());
            if (res.size() > 0) {
                settings.setId((Integer) res.get(0));
            }
            DBUtils.saveOrUpdate(settings);
            MessageUtils.sendMessage(event.getChannel(), "Birthdays will be announced in "
                    + MessageUtils.linkTextChannel(event.getChannel())
                    + " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)");
        });

        commands.put("getchannel", (event, args) -> {
            ArrayList res = DBUtils.query("select birthdaychannelid from settings where guildid = "
                    + event.getGuild().getId());
            TextChannel channel = event.getGuild().getTextChannelById(res.get(0).toString());
            if (res.size() == 0 || channel == null) {
                MessageUtils.sendMessage(event.getChannel(), "Birthday channel is not set");
                return;
            }
            MessageUtils.sendMessage(event.getChannel(), "Birthdays are announced in "
                    + MessageUtils.linkTextChannel(channel));
        });

        commands.put("coming", (event, args) -> {
            LocalDate today = LocalDate.now();
            ArrayList l = DBUtils.query("select userid, birthday from member where extract(month from birthday) = "
                    + today.getMonthValue()
                    + " and guildid = " + event.getGuild().getId()
                    + " and extract(day from birthday) >= " + today.getDayOfMonth() + " order by birthday");
            if (l.size() > 0) {
                ArrayList<net.dv8tion.jda.api.entities.Member> members = new ArrayList<>();
                ArrayList<Object> birthdays = new ArrayList<>();
                for (Object[] o: (ArrayList<Object[]>) l) {
                    net.dv8tion.jda.api.entities.Member member = event.getGuild().getMemberById(o[0].toString());
                    if (member != null) {
                        members.add(member);
                        birthdays.add(o[1]);
                    }
                }

                if (members.size() > 0) {
                    StringBuilder message = new StringBuilder("Coming birthdays this month:\n");
                    for (int i = 0; i < members.size(); i++) {
                        message.append("- ")
                                .append(members.get(i).getEffectiveName())
                                .append(" (").append(new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(birthdays.get(i)))
                                .append(")\n");
                    }
                    MessageUtils.sendMessage(event.getChannel(), message.toString());
                    return;
                }
            }
            MessageUtils.sendMessage(event.getChannel(), "No coming birthdays this month");
        });

        commands.put("month", (event, args) -> {
            if (args.size() < 2) {
                MessageUtils.sendMessage(event.getChannel(), "Please give a month");
                return;
            }

            try {
                int month = Integer.parseInt(args.get(1));

                if (month < 1 || month > 12) {
                    throw new NumberFormatException();
                }

                ArrayList l = DBUtils.query("select userid, birthday from member where extract(month from birthday) = "
                        + month + " and guildid = " + event.getGuild().getId() + " order by birthday");

                if (l.size() > 0) {
                    ArrayList<net.dv8tion.jda.api.entities.Member> members = new ArrayList<>();
                    ArrayList<Object> birthdays = new ArrayList<>();
                    for (Object[] o : (ArrayList<Object[]>) l) {
                        net.dv8tion.jda.api.entities.Member member = event.getGuild().getMemberById(o[0].toString());
                        if (member != null) {
                            members.add(member);
                            birthdays.add(o[1]);
                        }
                    }

                    if (members.size() > 0) {
                        StringBuilder message = new StringBuilder("Birthdays on "
                                + Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ":\n");
                        for (int i = 0; i < members.size(); i++) {
                            message.append("- ")
                                    .append(members.get(i).getEffectiveName())
                                    .append(" (")
                                    .append(new SimpleDateFormat("MMM dd",
                                            Locale.ENGLISH).format(birthdays.get(i)))
                                    .append(")\n");
                        }
                        MessageUtils.sendMessage(event.getChannel(), message.toString());
                        return;
                    }
                }
                MessageUtils.sendMessage(event.getChannel(), "No birthdays this month");
            } catch (NumberFormatException e) {
                MessageUtils.sendMessage(event.getChannel(), "Invalid month");
            }
        });
    }

    @Override
    public String getName() {
        return "birthday";
    }
}
