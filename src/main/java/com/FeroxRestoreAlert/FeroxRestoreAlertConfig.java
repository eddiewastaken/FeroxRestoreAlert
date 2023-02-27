package com.FeroxRestoreAlert;

import java.awt.Color;

import net.runelite.client.config.*;

@ConfigGroup("FeroxRestoreAlert")
public interface FeroxRestoreAlertConfig extends Config {
	@Alpha
	@ConfigItem(
			keyName = "textColor",
			name = "Text Color",
			description = "Color of text in alert overlay",
			position = 0
	)
	default Color getTextColor() {
		return Color.WHITE;
	}

	@Alpha
	@ConfigItem(
			keyName = "backgroundColor",
			name = "Background Color",
			description = "Color of background in alert overlay",
			position = 1
	)
	default Color getBackgroundColor() {
		return new Color(0, 142, 0);
	}

	@ConfigItem(
			keyName = "alertPosition",
			name = "Alert Position",
			description = "Changes where the alert is displayed",
			position = 2
	)
	default AlertPosition getAlertPosition() {
		return AlertPosition.TOP_LEFT;
	}

	@Range(
			min = 1,
			max = 100
	)
	@ConfigItem(
			keyName = "customEnergyValue",
			name = "Energy Threshold",
			description = "% for Energy to be considered restored",
			position = 5
	)
	default int getCustomEnergyRestoreValue() {
		return 100;
	}
}
