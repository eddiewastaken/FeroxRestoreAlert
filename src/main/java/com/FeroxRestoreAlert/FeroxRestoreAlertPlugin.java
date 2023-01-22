package com.FeroxRestoreAlert;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "Ferox Restore Alert"
)
public class FeroxRestoreAlertPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private FeroxRestoreAlertConfig config;

    @Inject
    private ClientThread clientThread;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private FeroxRestoreAlertOverlay feroxRestoreAlertOverlay;

    @Override
    protected void startUp() {
        log.info("Ferox Restore Alert started!");
        overlayManager.add(feroxRestoreAlertOverlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(feroxRestoreAlertOverlay);
        log.info("Ferox Restore Alert stopped!");
    }

    private int maxPrayerValue = 0;
    private int currentPrayerValue = 0;
    private int maxHitpointsValue = 0;
    private int currentHitpointsValue = 0;
    private final int maxEnergyValue = 100;
    private int currentEnergyPercentage = 0;
    private final WorldArea feroxArea = new WorldArea(3123, 3617, 31, 21, 0);

    @Subscribe
    public void onGameTick(GameTick event) {
        updateMaxStatValues();
        updateCurrentStatValues();

        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

        if (feroxArea.contains(playerLocation)) {
            if(!checkStatsAreRestored())
            {
                if(tickCounter % 2 == 0) {
                    feroxRestoreAlertOverlay.showOverlay();
                }
                else {
                    feroxRestoreAlertOverlay.hideOverlay();
                }
                tickCounter++;
            } else {
                feroxRestoreAlertOverlay.hideOverlay();
                tickCounter = 0;
            }
        }
    }
    int tickCounter = 0;

    public void updateMaxPrayerValue() {
        maxPrayerValue = client.getRealSkillLevel(Skill.PRAYER);
    }

    public void updateCurrentPrayerValue() {
        currentPrayerValue = client.getBoostedSkillLevel(Skill.PRAYER);
    }

    public void updateMaxHitpointsValue() {
        maxHitpointsValue = client.getRealSkillLevel(Skill.HITPOINTS);
    }

    public void updateCurrentHitpointsValue() {
        currentHitpointsValue = client.getBoostedSkillLevel(Skill.HITPOINTS);
    }

    public void updateCurrentEnergyPercentage() {
        currentEnergyPercentage = Math.floorDiv(client.getEnergy(), maxEnergyValue);
    }

    public void updateMaxStatValues() {
        updateMaxPrayerValue();
        updateMaxHitpointsValue();
    }

    public void updateCurrentStatValues() {
        updateCurrentPrayerValue();
        updateCurrentHitpointsValue();
        updateCurrentEnergyPercentage();
    }

    public boolean checkStatsAreRestored() {
        return currentPrayerValue == maxPrayerValue && currentHitpointsValue == maxHitpointsValue && currentEnergyPercentage == maxEnergyValue;
    }

    @Provides
    FeroxRestoreAlertConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(FeroxRestoreAlertConfig.class);
    }
}