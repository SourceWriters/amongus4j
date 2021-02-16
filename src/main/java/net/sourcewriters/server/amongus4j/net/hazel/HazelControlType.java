package net.sourcewriters.server.amongus4j.net.hazel;

public enum HazelControlType {

    HELLO(false, false),
    DISCONNECT(true, false),
    ACKNOWLEDGED(false, true);

    private final boolean reliable, fragmented;

    private HazelControlType(boolean reliable, boolean fragmented) {
        this.reliable = reliable;
        this.fragmented = fragmented;
    }

    public boolean isReliable() {
        return reliable;
    }

    public boolean isFragmented() {
        return fragmented;
    }

    public static HazelControlType of(boolean reliable, boolean fragmented) {
        for (HazelControlType type : values()) {
            if (type.reliable == reliable && type.fragmented == fragmented) {
                return type;
            }
        }
        return null;
    }

}
