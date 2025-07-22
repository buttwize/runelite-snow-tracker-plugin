package sh.bitwise.runelite;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SnowOverlay extends Overlay {
    private final SnowTrackerPlugin plugin;
    private final SnowTrackerConfig config;
    private final Client client;

    @Inject
    SnowOverlay(SnowTrackerPlugin plugin, SnowTrackerConfig config, Client client) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.plugin = plugin;
        this.config = config;
        this.client = client;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        final Color colorText = this.config.getTextColor();
        final boolean shouldOutline = this.config.getEnableTextOutline();

        final Color colorWarning = this.config.getWarningColor();
        final int thresholdWarning = this.config.getWarningThreshold();

        final Color colorError = this.config.getErrorColor();
        final int thresholdError = this.config.getErrorThreshold();

        final Color colorBackground = this.config.getBackgroundColor();
        final int backgroundSize = this.config.getBackgroundSize();

        final boolean backgroundEnabled = this.config.getEnableBackground();

        this.plugin.getSnowList().forEach(snow -> {
            final String text = snow.getTicksLeftDisplay();
            final LocalPoint localPoint = LocalPoint.fromWorld(this.client.getTopLevelWorldView(), snow.getLocation());
            if (localPoint != null) {
                final Color color;
                final int counter = snow.getTicksLeft();
                if (counter < thresholdError) {
                    color = colorError;
                } else if (counter < thresholdWarning) {
                    color = colorWarning;
                } else {
                    color = colorText;
                }

                final Point point = Perspective.getCanvasTextLocation(this.client, graphics, localPoint, text, 0);
                Rectangle2D textBounds = graphics.getFontMetrics().getStringBounds(text, graphics);

                if (backgroundEnabled) {
                    this.drawTextBackground(graphics, point, colorBackground, textBounds, backgroundSize);
                }

                this.drawText(graphics, point, color, shouldOutline, text);
            }
        });

        return null;
    }

    private void drawTextBackground(Graphics2D graphics, Point point, Color color, Rectangle2D textBounds, int size) {
        graphics.setColor(color);

        final int x = (int) (point.getX() - ((double) size / 2) + (textBounds.getWidth() / 2));
        final int y = (int) (point.getY() - ((double) size / 2) - (textBounds.getHeight() / 2));

        graphics.fillRect(x, y, size, size);
    }

    private void drawText(Graphics2D graphics, Point point, Color color, boolean shouldOutline, String text) {
        final int x = point.getX();
        final int y = point.getY();

        if (shouldOutline) {
            graphics.setColor(Color.BLACK);
            graphics.drawString(text, x + 1, y + 1);
        }

        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }
}
