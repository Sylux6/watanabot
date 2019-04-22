package reminder;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.BotUtils;
import utils.DBUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Batch implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalDate today = LocalDate.now();
        BotUtils.sendLog(new Date().toString() + " - Running batch");
        ArrayList l = DBUtils.query("select userid from member where guildid = "
                + BotUtils.SRID + " and extract(month from birthday) = " + today.getMonthValue()
                + " and extract(day from birthday) = " + today.getDayOfMonth());
        StringBuilder wish = new StringBuilder(BotUtils.getYousoro(BotUtils.bot.getGuildById(BotUtils.SRID)) + " Happy Birthday to:\n");
        for (Long id : (ArrayList<Long>)l) {
            wish.append(BotUtils.mentionAt(BotUtils.bot.getUserById(id)) + "\n");
        }
        BotUtils.sendMessage(BotUtils.bot.getGuildById(BotUtils.SRID).getTextChannelsByName("general", true).get(0), wish.toString());
    }
}
