package reminder;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {
    
    public void run() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

		CronTrigger youBirthday = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 17 4 ? *")).build();
		CronTrigger dailyTrigger = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 * * ?")).build();

		sched.scheduleJob(newJob(WatanabeYou.class).build(), youBirthday);
		sched.scheduleJob(newJob(Batch.class).build(), dailyTrigger);
		sched.start();
    }
}
