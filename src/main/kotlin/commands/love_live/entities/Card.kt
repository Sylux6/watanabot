package commands.love_live.entities

import java.awt.Color
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.internal.utils.IOUtil
import org.json.JSONObject

class Card(json: JSONObject, user: User) {

    val id: Int = json.getInt("id")
    val name: String = json.getJSONObject("idol").getString("name")
    private val rarity: Rarity = Rarity.valueOf(json.getString("rarity"))
    private val attribute: Attribute = Attribute.valueOf(json.getString("attribute").toUpperCase())
    val url: String = json.getString("website_url")
    var img: String? = null
        private set
    private val idolizedImage: String
    var roundImage: String? = null
        private set
    private val roundIdolizedImage: String
    private val user: String = user.name

    val fileImg: ByteArray
        @Throws(MalformedURLException::class, IOException::class)
        get() = IOUtil.readFully(URL(this.img!!).openStream())

    val fileIdolizedImg: ByteArray
        @Throws(MalformedURLException::class, IOException::class)
        get() = IOUtil.readFully(URL(this.idolizedImage).openStream())

    val fileRoundImg: ByteArray
        @Throws(MalformedURLException::class, IOException::class)
        get() = IOUtil.readFully(URL(this.roundImage!!).openStream())

    val fileRoundIdolizedImg: ByteArray
        @Throws(MalformedURLException::class, IOException::class)
        get() = IOUtil.readFully(URL(this.roundIdolizedImage).openStream())

    init {

        if (json.get("card_image") == null)
            this.img = "https:" + json.getString("card_idolized_image")
        else
            this.img = "https:" + json.getString("card_image")
        this.idolizedImage = "https:" + json.getString("card_idolized_image")

        if (json.get("round_card_image") == null)
            this.roundImage = "https:" + json.getString("round_card_idolized_image")
        else
            this.roundImage = "https:" + json.getString("round_card_image")
        this.roundIdolizedImage = "https:" + json.getString("round_card_idolized_image")

    }

    fun toEmbedMessage(): Message {

        val embed = EmbedBuilder()
                .setTitle(name)
                .setImage("attachment://idol.png")
                .addField("ID", "$id", true)
                .addField("Rarity", rarity.toString(), true)
                .addBlankField(true)
                .addField("Attribute", attribute.toString(), true)
                .addField("Owner", user, true)
                .addBlankField(true)
                .setColor(if (this.attribute === Attribute.COOL) Color(0, 187, 255) else if (this.attribute === Attribute.PURE) Color(0, 187, 68) else Color(238, 27, 143))
                .build()
        return MessageBuilder(embed).build()
    }
}
