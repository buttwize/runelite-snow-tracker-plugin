package sh.bitwise.runelite;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@PluginDescriptor(
        name = "Snow Tracker",
        description = "Tracks snow on the ground",
        tags = {"snow", "track", "tracker", "time", "timer"}
)
public class SnowTrackerPlugin extends Plugin {
    private static final String SNOW_MESSAGE = "You made some snow fall!";

    @Getter
    private final List<Snow> snowList = new LinkedList<>();

    @Inject
    private Client client;

    @Inject
    private SnowOverlay overlay;

    @Inject
    private OverlayManager overlayManager;

    private WorldPoint playerLocation;

    @Provides
    SnowTrackerConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(SnowTrackerConfig.class);
    }

    @Override
    protected void startUp() {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() {
        overlayManager.remove(overlay);
        this.snowList.clear();
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {
        if (Objects.equals(event.getMessage(), SNOW_MESSAGE)) {
            log.debug("Snow created by current player at {}", this.playerLocation);
            this.snowList.add(new Snow(this.playerLocation));
        }
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        this.snowList.forEach(Snow::decrement);
        this.snowList.removeIf(snow -> snow.getTicksLeft() < 0);

        final Player player = client.getLocalPlayer();
        if (null != player) {
            player.getLocalLocation();
            this.playerLocation = player.getWorldLocation();
        }
    }
}
