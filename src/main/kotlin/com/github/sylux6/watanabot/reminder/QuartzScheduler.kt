package com.github.sylux6.watanabot.reminder

import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.DateBuilder
import org.quartz.JobBuilder.newJob
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory

object QuartzScheduler {

    fun run() {
        val sf = StdSchedulerFactory()
        val sched = sf.scheduler

        val startTime = DateBuilder.nextGivenSecondDate(null, 15)

        val youBirthday = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 17 4 ? *")).build()
        val maiaBirthday = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 14 3 ? *")).build()
        val dailyTrigger = newTrigger().startAt(startTime).withSchedule(cronSchedule("0 0 0 * * ?")).build()

        sched.scheduleJob(newJob(WatanabeYou::class.java).build(), youBirthday)
        sched.scheduleJob(newJob(Maia::class.java).build(), maiaBirthday)
        sched.scheduleJob(newJob(Batch::class.java).build(), dailyTrigger)
        sched.start()
    }
}
