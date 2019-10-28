package commands.azur_lane.entities

enum class Rarity constructor(val colorName: String, val colorCode: Int) {
    NORMAL("Normal", 16777215),
    RARE("Rare", 11591910),
    ELITE("Elite", 14524637),
    SUPER_RARE("Super Rare", 15657130),
    UNKNOWN("Unknown", 16777215)
}