package modules.azurlane.entity;

public enum Rarity {
    NORMAL ("Normal", 16777215),
    RARE ("Rare", 11591910),
    ELITE ("Elite", 14524637),
    SUPER_RARE ("Super Rare", 15657130),
    UNKNOWN ("Unknown", 16777215);

    private String name;
    private int color;

    Rarity(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}