package sh.bitwise.runelite;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

/**
 * Wrapper class for a GameObject that represents Snow.
 */
public class Snow {
    private static final int DEFAULT_DURATION = 24;

    @Getter
    private final WorldPoint location;

    @Getter
    private String ticksLeftDisplay;

    @Getter
    private int ticksLeft;

    Snow(WorldPoint location) {
        this.ticksLeft = DEFAULT_DURATION;
        this.ticksLeftDisplay = "" + this.ticksLeft;
        this.location = location;
    }

    public void decrement() {
        this.ticksLeftDisplay = "" + --this.ticksLeft;
    }
}
