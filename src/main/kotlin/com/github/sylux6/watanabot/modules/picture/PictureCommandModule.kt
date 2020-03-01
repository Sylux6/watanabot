package com.github.sylux6.watanabot.modules.picture

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.picture.commands.PictureNsfwCommand
import com.github.sylux6.watanabot.modules.picture.commands.PictureSafeCommand

object PictureCommandModule : AbstractCommandModule(
    "Picture",
    "p",
    setOf(
        PictureNsfwCommand,
        PictureSafeCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands to search for pictures from Danbooru."
}
