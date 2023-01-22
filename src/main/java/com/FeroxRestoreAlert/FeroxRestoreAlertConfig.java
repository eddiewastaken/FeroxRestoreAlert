package com.FeroxRestoreAlert;

import java.awt.Color;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("FeroxRestoreAlert")
public interface FeroxRestoreAlertConfig extends Config
{
	@Alpha
	@ConfigItem(
			keyName = "textColor",
			name = "Text Color",
			description = "Color of text in alert overlay",
			position = 0
	)
	default Color getTextColor()
	{
		return Color.WHITE;
	}

	@Alpha
	@ConfigItem(
			keyName = "backgroundColor",
			name = "Background Color",
			description = "Color of background in alert overlay",
			position = 1
	)
	default Color getBackgroundColor()
	{
		return new Color(0, 142, 0);
	}

	@ConfigItem(
			keyName = "alertPosition",
			name = "Alert Position",
			description = "Changes where the alert is displayed",
			position = 2
	)
	default AlertPosition getAlertPosition()
	{
		return AlertPosition.TOP_LEFT;
	}
}
