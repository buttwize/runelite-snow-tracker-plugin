package sh.bitwise.runelite;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.ObjectID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name = "Snow Tracker",
        description = "Tracks snow on the ground",
        tags = {"snow", "track", "tracker", "time", "timer"}
)
public class SnowTrackerPlugin extends Plugin {
    @Getter
    private final List<Snow> snow = new ArrayList<>();

    @Inject
    private SnowOverlay overlay;

    @Inject
    private OverlayManager overlayManager;

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
        this.snow.clear();
    }

    @Subscribe
    public void onGameObjectSpawned(final GameObjectSpawned event) {
        final GameObject gameObject = event.getGameObject();
        switch (gameObject.getId()) {
            case ObjectID.GUBLINCH_SNOW_SMALL_1:
            case ObjectID.GUBLINCH_SNOW_SMALL_2:
            case ObjectID.GUBLINCH_SNOW_SMALL_3:
            case ObjectID.GUBLINCH_SNOW_SMALL_4:
            case ObjectID.GUBLINCH_SNOW_SMALL_5:
                log.debug("Snow tracked at {}", gameObject.getLocalLocation());
                this.snow.add(new Snow(gameObject));
                break;
        }
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        this.snow.removeIf(Snow::isExpired);
        this.snow.forEach(Snow::tick);
    }
}
