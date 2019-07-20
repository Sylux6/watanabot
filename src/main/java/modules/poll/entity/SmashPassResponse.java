package modules.poll.entity;

public enum SmashPassResponse {
    SMASH("\u2705"),
    PASS("\u274c"),
    HUG("\uD83D\uDC96");

    private final String emote;

    SmashPassResponse(String s) {
        this.emote = s;
    }

    public String getEmote() {
        return this.emote;
    }
}
