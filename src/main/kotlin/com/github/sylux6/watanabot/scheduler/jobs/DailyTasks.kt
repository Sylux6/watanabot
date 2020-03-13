package com.github.sylux6.watanabot.scheduler.jobs

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.scheduler.utils.birthdayDailyChecker
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.jdaInstance
import com.github.sylux6.watanabot.utils.log
import com.github.sylux6.watanabot.utils.sendLog
import java.time.LocalDate
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import net.dv8tion.jda.api.EmbedBuilder
import org.quartz.Job
import org.quartz.JobExecutionContext

private val logger = KotlinLogging.logger {}

class DailyTasks : Job {

    private val logMutex = Mutex()

    override fun execute(context: JobExecutionContext) {
        val today = LocalDate.now()
        val logBuilder = StringBuilder("List of tasks:")
        val embedLogBuilder = EmbedBuilder()
            .setAuthor(jdaInstance.selfUser.name, null, jdaInstance.selfUser.effectiveAvatarUrl)
            .setColor(BOT_PRIMARY_COLOR)
            .setTitle("Daily task ($today)")

        // BE CAREFUL WHEN ADDING A NEW TASK TO CONCURRENCY ACCESS AND MUTABLE STATE
        runBlocking {
            withContext(Dispatchers.Default) {
                // Update Azur Lane database
                launch { addJob(embedLogBuilder, logBuilder, "Update Azur Lane database") { Atago.reloadDatabase() } }
                // Birthday
                launch { addJob(embedLogBuilder, logBuilder, "Check members' birthday") { birthdayDailyChecker() } }
            }
        }

        // Send log
        logger.log(logBuilder.toString())
        sendLog(embedLogBuilder.build())
    }

    private suspend fun addJob(
        embedLogBuilder: EmbedBuilder,
        logBuilder: StringBuilder,
        jobTitle: String,
        job: () -> Unit
    ): EmbedBuilder {
        try {
            try {
                val timeExecution = measureTimeMillis(job)
                logMutex.withLock {
                    embedLogBuilder.addField(jobTitle, "${timeExecution}ms", false)
                    logBuilder.append("\n* $jobTitle executed in ${timeExecution}ms")
                }
            } catch (e: Exception) {
                logMutex.withLock {
                    embedLogBuilder.addField(jobTitle, "failed because: ${e.message}", false)
                    logBuilder.append("\n* $jobTitle failed because $e")
                }
            }
        } catch (e: Throwable) {
            logger.log(e)
        }
        return embedLogBuilder
    }
}
