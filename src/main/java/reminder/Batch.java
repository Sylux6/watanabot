package reminder;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.BotUtils;
import utils.DBUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Batch implements Job {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context) {
        LocalDate today = LocalDate.now();
        BotUtils.sendLog(new Date().toString() + " - Running batch");
        ArrayList l = DBUtils.query("select userid from member where guildid = "
                + BotUtils.SRID + " and extract(month from birthday) = " + today.getMonthValue()
                + " and extract(day from birthday) = " + today.getDayOfMonth());
        if (l.size() > 0) {
            StringBuilder wish = new StringBuilder("Happy Birthday " + BotUtils.getYousoro(BotUtils.bot.getGuildById(BotUtils.SRID)) + "\n");
            for (BigInteger id : (ArrayList<BigInteger>) l) {
                wish.append(BotUtils.mentionAt(BotUtils.bot.getUserById(String.valueOf(id))));
                wish.append("\n");
            }
            BotUtils.sendMessage(BotUtils.bot.getGuildById(BotUtils.SRID).getTextChannelsByName("general", true).get(0), wish.toString());
        }
    }
}
