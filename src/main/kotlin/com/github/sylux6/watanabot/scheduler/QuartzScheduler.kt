package com.github.sylux6.watanabot.scheduler

import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.DateBuilder
import org.quartz.JobBuilder.newJob
import org.quartz.Scheduler
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory

val scheduler: Scheduler = StdSchedulerFactory().scheduler

object QuartzScheduler {
    fun run() {
        val startTime = DateBuilder.nextGivenSecondDate(null, 15)

        val youBirthday = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 17 4 ? *")).build()
        val maiaBirthday = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 14 3 ? *")).build()
        val dailyTrigger = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 * * ?")).build()

        scheduler.scheduleJob(newJob(WatanabeYou::class.java).build(), youBirthday)
        scheduler.scheduleJob(newJob(Maia::class.java).build(), maiaBirthday)
        scheduler.scheduleJob(newJob(DailyTask::class.java).build(), dailyTrigger)
        scheduler.start()
    }
}
