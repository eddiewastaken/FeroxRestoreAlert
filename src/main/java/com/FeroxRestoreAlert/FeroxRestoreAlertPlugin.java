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
    private int customEnergyRestoreValue = 0;
    private int currentEnergyPercentage = 0;
    private final WorldArea feroxArea = new WorldArea(3123, 3617, 31, 21, 0);
    int tickCounter = 0;

    @Subscribe
    public void onGameTick(GameTick event) {
        updateMaxStatValues();
        updateCurrentStatValues();
        customEnergyRestoreValue = config.getCustomEnergyRestoreValue();

        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

        if (feroxArea.contains(playerLocation) && !checkStatsAreRestored() && tickCounter % 2 == 0) {
            feroxRestoreAlertOverlay.showOverlay();
            tickCounter++;
        } else {
            feroxRestoreAlertOverlay.hideOverlay();
            tickCounter = 0;
        }

        if (!feroxArea.contains(playerLocation) || checkStatsAreRestored()) {
            feroxRestoreAlertOverlay.hideOverlay();
        }
    }

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
        currentEnergyPercentage = Math.floorDiv(client.getEnergy(), 100);
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
        return currentPrayerValue == maxPrayerValue &&
               currentHitpointsValue == maxHitpointsValue &&
               currentEnergyPercentage >= customEnergyRestoreValue;
    }

    @Provides
    FeroxRestoreAlertConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(FeroxRestoreAlertConfig.class);
    }
}