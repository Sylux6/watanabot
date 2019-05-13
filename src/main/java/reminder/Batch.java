package reminder;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.BotUtils;
import utils.DBUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Batch implements Job {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context) {
        LocalDate today = LocalDate.now();
        BotUtils.sendLog(new Date().toString() + " - Running batch");

        // Birthday
        ArrayList l = DBUtils.query("select guildid, userid from member where extract(month from birthday) = " + today.getMonthValue()
                + " and extract(day from birthday) = " + today.getDayOfMonth());
        if (l.size() > 0) {
            HashMap<BigInteger, ArrayList<BigInteger>> res = new HashMap<>();
            for (Object[] o: (ArrayList<Object[]>) l) {
                ArrayList<BigInteger> members = res.get(o[0]);
                if (members == null) {
                    members = new ArrayList<>();
                    res.put((BigInteger) o[0], members);
                }
                members.add((BigInteger) o[1]);
            }
            for (Map.Entry<BigInteger, ArrayList<BigInteger>> entry: res.entrySet()) {
                ArrayList settings = DBUtils.query("select birthdaychannelid from settings where guildid = "
                        + entry.getKey());
                TextChannel channel = BotUtils.bot.getGuildById(entry.getKey().toString()).getTextChannelById(settings.get(0).toString());
                if (res.size() == 0 || channel == null) {
                    continue;
                }
                boolean found = false;
                StringBuilder wish = new StringBuilder("Happy Birthday " + BotUtils.getYousoro(BotUtils.bot.getGuildById(entry.getKey().toString())) + "\n");
                for (BigInteger id : entry.getValue()) {
                    // FIXME: dirty
                    User user = BotUtils.bot.getUserById(String.valueOf(id));
                    if (user != null) {
                        found = true;
                        wish.append(BotUtils.mentionAt(user));
                        wish.append("\n");
                    }
                }
                if (found) {
                    BotUtils.sendMessage(channel, wish.toString());
                }
            }
        }
    }
}
