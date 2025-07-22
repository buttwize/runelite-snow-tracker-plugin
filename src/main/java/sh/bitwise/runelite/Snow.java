package sh.bitwise.runelite;

import lombok.Getter;
import net.runelite.api.GameObject;
import net.runelite.api.coords.LocalPoint;

/**
 * Wrapper class for a GameObject that represents Snow.
 */
public class Snow {
    private static final int DEFAULT_DURATION = 24;

    @Getter
    private final LocalPoint location;

    @Getter
    private int remaining;

    Snow(GameObject object) {
        this.remaining = DEFAULT_DURATION;
        this.location = object.getLocalLocation();
    }

    public void tick() {
        --this.remaining;
    }

    public boolean isExpired() {
        return remaining < 1;
    }
}
