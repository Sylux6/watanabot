package com.github.sylux6.watanabot.modules.music.entities

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import java.nio.ByteBuffer
import net.dv8tion.jda.api.audio.AudioSendHandler

/**
 * This is a wrapper around AudioPlayer which makes it behave as an
 * AudioSendHandler for JDA. As JDA calls canProvide before every call to
 * provide20MsAudio(), we pull the frame in canProvide() and use the frame we
 * already pulled in provide20MsAudio().
 */
class AudioPlayerSendHandler
/**
 * @param audioPlayer
 * Audio player to wrap.
 */
    (private val audioPlayer: AudioPlayer) : AudioSendHandler {
    private var lastFrame: AudioFrame? = null

    override fun canProvide(): Boolean {
        if (lastFrame == null) {
            lastFrame = audioPlayer.provide()
        }

        return lastFrame != null
    }

    override fun provide20MsAudio(): ByteBuffer? {
        if (lastFrame == null) {
            lastFrame = audioPlayer.provide()
        }

        val data = if (lastFrame != null) ByteBuffer.wrap(lastFrame!!.data) else null
        lastFrame = null

        return data
    }

    override fun isOpus(): Boolean {
        return true
    }
}
