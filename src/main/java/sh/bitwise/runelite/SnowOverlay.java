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
        this.plugin.getSnow().forEach(snow -> {
            final int remaining = snow.getRemaining();
            final String text = String.valueOf(remaining);

            final LocalPoint localPoint = snow.getLocation();
            final Point point = Perspective.getCanvasTextLocation(this.client, graphics, localPoint, text, 0);
            if (point == null) {
                return;
            }

            final Color color = color(remaining);
            Rectangle2D textBounds = graphics.getFontMetrics().getStringBounds(text, graphics);

            if (this.config.getEnableBackground()) {
                this.drawTextBackground(graphics, point, this.config.getBackgroundColor(), textBounds, this.config.getBackgroundSize());
            }

            this.drawText(graphics, point, color, this.config.getEnableTextOutline(), text);
        });

        return null;
    }

    private Color color(int counter) {
        if (counter < this.config.getErrorThreshold()) {
            return this.config.getErrorColor();
        } else if (counter < this.config.getWarningThreshold()) {
            return this.config.getWarningColor();
        } else {
            return this.config.getTextColor();
        }
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
