package commands.azur_lane.entities

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.json.JSONObject
import utils.HttpRequest
import utils.JsonUtils
import java.io.IOException
import java.util.*

class Ship(
        private val id: String,
        private val url: String,
        private val nameEN: String,
        private val nameJP: String,
        private val nameCN: String,
        private val constructionTime: String,
        private val rarity: Rarity,
        private val shipClass: String,
        private val nationality: String,
        private val type: String,
        private val img: String,
        private val imgIcon: String,
        private val imgChibi: String,
        private val skins: LinkedHashMap<String, String>
) {

    fun toEmbed(): MessageEmbed {
        return EmbedBuilder()
                .setTitle(this.nameEN + " (" + this.nameJP + ")", this.url)
                .setColor(this.rarity.colorCode)
                .setThumbnail(this.imgIcon)
                .setImage(this.img)
                .setDescription("ID No. " + this.id)
                .addField("Class", this.shipClass, true)
                .addField("Nationality", this.nationality, true)
                .addBlankField(true)
                .addField("Type", this.type, true)
                .addField("Construction time", this.constructionTime, true)
                .addBlankField(true)
                .build()
    }

    fun skinEmbed(index: Int): MessageEmbed {
        if (index == 0 || index > this.skins.size || index < 0) {
            return EmbedBuilder().setDescription("No skin found").build()
        } else {
            var count = index
            var name = ""
            var skin = ""
            for (key in skins.keys) {
                name = key
                skin = this.skins[key]!!
                if (--count == 0) break
            }
            return EmbedBuilder()
                    .setTitle(this.nameEN + " (" + this.nameJP + ")", this.url)
                    .setColor(this.rarity.colorCode)
                    .setDescription(name)
                    .setImage(skin)
                    .build()
        }
    }

    fun skinListEmbed(): MessageEmbed {
        val embedShip = EmbedBuilder()
                .setTitle(this.nameEN + " (" + this.nameJP + ")", this.url)
                .setColor(this.rarity.colorCode)
                .setThumbnail(this.imgIcon)
        if (skins.size > 0) {
            var i = 1
            val list = StringBuilder()
            for (key in skins.keys) {
                list.append("$i. $key\n")
                i++
            }
            embedShip.addField("Skin(s)", list.toString(), true)
        } else {
            embedShip.setDescription("No skin")
        }
        return embedShip.build()
    }

    fun chibiEmbed(): MessageEmbed {
        return EmbedBuilder()
                .setTitle(this.nameEN + " (" + this.nameJP + ")", this.url)
                .setColor(this.rarity.colorCode)
                .setImage(this.imgChibi)
                .build()

    }

    companion object {

        fun getRarityByName(rarity: String): Rarity {
            when (rarity) {
                "Super Rare" -> return Rarity.SUPER_RARE
                "Elite" -> return Rarity.ELITE
                "Rare" -> return Rarity.RARE
                "Normal" -> return Rarity.NORMAL
                else -> return Rarity.UNKNOWN
            }
        }

        @Throws(IOException::class)
        fun getShipByName(name: String): Ship? {
            val jsonObject = JsonUtils.stringToJsonObject(HttpRequest.getRequest(
                    "https://api.kurozeropb.info/v1/azurlane/ship", "name=$name"
            ))
            if (jsonObject.getInt("statusCode") != 200) {
                return null
            }
            val ship = jsonObject.getJSONObject("ship")
            val skins = ship.getJSONArray("skins")
            val skinMap = LinkedHashMap<String, String>()
            for (skin in skins) {
                skinMap[(skin as JSONObject).getString("title")] = skin.getString("image")
            }
            val id = ship.getString("id")
            val url = ship.getString("wikiUrl")
            val nameEN = JsonUtils.getStringOrNull(ship.getJSONObject("names"), "en")
            val nameJP = JsonUtils.getStringOrNull(ship.getJSONObject("names"), "jp")
            val nameCN = JsonUtils.getStringOrNull(ship.getJSONObject("names"), "cn")
            val constructionTime = JsonUtils.getStringOrNull(ship, "buildTime")
            val rarity = getRarityByName(JsonUtils.getStringOrNull(ship, "rarity"))
            val shipClass = JsonUtils.getStringOrNull(ship, "class")
            val nationality = JsonUtils.getStringOrNull(ship, "nationality")
            val type = JsonUtils.getStringOrNull(ship, "hullType")
            val img = skinMap["Default"]
            val imgIcon = JsonUtils.getStringOrNull(ship, "thumbnail")
            val imgChibi = JsonUtils.getStringOrNull(ship, "chibi")
            skinMap.remove("Default")
            return Ship(id, url, nameEN, nameJP, nameCN, constructionTime, rarity, shipClass, nationality, type, img
                    ?: "",
                    imgIcon, imgChibi, skinMap)
        }
    }
}
