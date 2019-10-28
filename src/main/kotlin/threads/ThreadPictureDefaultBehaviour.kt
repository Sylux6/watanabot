package threads

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class ThreadPictureDefaultBehaviour(internal var event: MessageReceivedEvent, internal var args: List<String>) : Runnable {

    override fun run() {
//        PictureModule.getByDefault(event, args)
    }

}
