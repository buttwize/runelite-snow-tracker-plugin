package sh.bitwise.runelite;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SnowTrackerPluginTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(SnowTrackerPlugin.class);
        RuneLite.main(args);
    }
}