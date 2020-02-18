package com.github.sylux6.watanabot.modules.azur_lane

import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneChibiCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneInfoCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneMarryCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLanePopularityCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneSkinCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneSkinsCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneWaifuCommand
import com.github.sylux6.watanabot.modules.azur_lane.entities.Rarity
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

object AzurLaneCommandModule : AbstractCommandModule(
    "Azur Lane",
    "al",
    setOf(
        AzurLaneInfoCommand,
        AzurLaneSkinsCommand,
        AzurLaneSkinCommand,
        AzurLaneChibiCommand,
        AzurLaneMarryCommand,
        AzurLaneWaifuCommand,
        AzurLanePopularityCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands related to Azur Lane."

    private fun getRarityByName(rarity: String): Rarity {
        return when (rarity) {
            "Super Rare" -> Rarity.SUPER_RARE
            "Elite" -> Rarity.ELITE
            "Rare" -> Rarity.RARE
            "Normal" -> Rarity.NORMAL
            else -> Rarity.UNKNOWN
        }
    }

    fun shipToEmbed(ship: Ship): MessageEmbed {
        return EmbedBuilder()
            .setTitle(ship.names.en + " (" + ship.names.jp + ")", ship.wikiUrl)
            .setColor(getRarityByName(ship.rarity).colorCode)
            .setThumbnail(ship.thumbnail)
            .setImage(if (ship.skins.isNotEmpty()) ship.skins.first().image else null)
            .setDescription("ID No. ${ship.id}")
            .addField("Class", ship.shipClass, true)
            .addField("Nationality", ship.nationality, true)
            .addBlankField(true)
            .addField("Type", ship.hullType, true)
            .addField("Construction time", ship.construction?.constructionTime ?: "N/A", true)
            .addBlankField(true)
            .build()
    }

    fun skinEmbed(ship: Ship, index: Int): MessageEmbed {
        return if (index < 1 || index > ship.skins.size) {
            EmbedBuilder().setDescription("No skin found").build()
        } else {
            EmbedBuilder()
                .setTitle(ship.names.en + " (" + ship.names.jp + ")", ship.wikiUrl)
                .setColor(getRarityByName(ship.rarity).colorCode)
                .setDescription(ship.skins[index].name)
                .setImage(ship.skins[index].image)
                .setThumbnail(ship.skins[index].chibi)
                .build()
        }
    }

    fun skinListEmbed(ship: Ship): MessageEmbed {
        val embedShip = EmbedBuilder()
            .setTitle(ship.names.en + " (" + ship.names.jp + ")", ship.wikiUrl)
            .setColor(getRarityByName(ship.rarity).colorCode)
            .setThumbnail(ship.thumbnail)
        if (ship.skins.size > 1) {
            val list = StringBuilder()
            for ((index, skin) in ship.skins.drop(1).withIndex()) {
                list.append("${index + 1}. ${skin.name}\n")
            }
            embedShip.addField("Skin(s)", list.toString(), true)
        } else {
            embedShip.setDescription("No skin")
        }
        return embedShip.build()
    }

    fun chibiEmbed(ship: Ship): MessageEmbed {
        return EmbedBuilder()
            .setTitle(ship.names.en + " (" + ship.names.jp + ")", ship.wikiUrl)
            .setColor(getRarityByName(ship.rarity).colorCode)
            .setImage(ship.skins.first().chibi)
            .build()
    }
}
