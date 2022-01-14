package sh.bitwise.runelite;

import net.runelite.client.config.*;

import java.awt.*;


@ConfigGroup("snowtracker")
public interface SnowTrackerConfig extends Config {
    @Alpha
    @ConfigItem(
            keyName = "textColor",
            name = "Text Color",
            description = "Color of normal timer text",
            position = 0
    )
    default Color getTextColor() {
        return Constants.COLOR_TEXT;
    }

    @Alpha
    @ConfigItem(
            keyName = "warningColor",
            name = "Warning Color",
            description = "Color of warning timer text",
            position = 1
    )
    default Color getWarningColor() {
        return Constants.COLOR_WARNING;
    }

    @Range(
            max = 24
    )
    @ConfigItem(
            keyName = "warningThreshold",
            name = "Warning Threshold",
            description = "When the ticks drop BELOW this number, the warning color is applied.\nNOTE: 0 effectively disables the warning color.",
            position = 2
    )
    default int getWarningThreshold() {
        return Constants.THRESHOLD_WARNING;
    }

    @Alpha
    @ConfigItem(
            keyName = "errorColor",
            name = "Error Color",
            description = "Color of error timer text",
            position = 3
    )
    default Color getErrorColor() {
        return Constants.COLOR_ERROR;
    }

    @Range(
            max = 24
    )
    @ConfigItem(
            keyName = "errorThreshold",
            name = "Error Threshold",
            description = "When the ticks drop BELOW this number, the error color is applied.\nNOTE: 0 effectively disables the error color.",
            position = 4
    )
    default int getErrorThreshold() {
        return Constants.THRESHOLD_ERROR;
    }

    @ConfigItem(
            keyName = "enableTextOutline",
            name = "Outline Text",
            description = "If the timer text should have an outline",
            position = 5
    )
    default boolean getEnableTextOutline() {
        return false;
    }

    @ConfigSection(
            name = "Background Settings",
            description = "All settings relating to the timer background",
            position = 100
    )
    String backgroundSettings = "backgroundSettings";

    @ConfigItem(
            keyName = "enableBackground",
            name = "Enable Background",
            description = "If the timer background should be enabled",
            position = 0,
            section = backgroundSettings
    )
    default boolean getEnableBackground() {
        return true;
    }

    @Range(
            min = 16,
            max = 30
    )
    @ConfigItem(
            keyName = "backgroundSize",
            name = "Background Size",
            description = "How large the timer background should be",
            position = 1,
            section = backgroundSettings
    )
    default int getBackgroundSize() {
        return Constants.BACKGROUND_SIZE;
    }

    @Alpha
    @ConfigItem(
            keyName = "backgroundColor",
            name = "Background Color",
            description = "Color of background box surrounding the timer",
            position = 2,
            section = backgroundSettings
    )
    default Color getBackgroundColor() {
        return Constants.COLOR_BACKGROUND;
    }


}