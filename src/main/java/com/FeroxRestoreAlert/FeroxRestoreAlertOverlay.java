package com.FeroxRestoreAlert;

import javax.inject.Inject;
import java.awt.*;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class FeroxRestoreAlertOverlay extends Overlay
{
    @Inject
    private FeroxRestoreAlertConfig config;
    private final Client client;
    private final PanelComponent panelComponent = new PanelComponent();
    private final OverlayManager overlayManager;
    private boolean isVisible = false;

    @Inject
    private FeroxRestoreAlertOverlay(FeroxRestoreAlertPlugin plugin,
                                     Client client,
                                     FeroxRestoreAlertConfig config,
                                     OverlayManager overlayManager) {
        super(plugin);
        setPriority(OverlayPriority.LOW);
        this.client = client;
        this.config = config;
        this.overlayManager = overlayManager;
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Ferox Restore Alert overlay");
    }

    public void showOverlay() {
        if (!isVisible)
        {
            isVisible = true;
            overlayManager.add(this);
        }
    }

    public void hideOverlay() {
        if (isVisible)
        {
            isVisible = false;
            overlayManager.remove(this);
        }
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if(!isVisible) {
            return null;
        }

        String titleText = "Restore your stats before leaving!";

        panelComponent.getChildren().clear();
        panelComponent.getChildren().add(TitleComponent.builder()
                .text(titleText)
                .color(config.getTextColor())
                .build());

        panelComponent.setPreferredSize(new Dimension(
                graphics.getFontMetrics().stringWidth(titleText) + 50,
                20
        ));

        panelComponent.setBackgroundColor(config.getBackgroundColor());

        switch (config.getAlertPosition()) {
            case BOTTOM_LEFT:
                setPosition(OverlayPosition.BOTTOM_LEFT);
                break;
            case TOP_LEFT:
                setPosition(OverlayPosition.TOP_LEFT);
                break;
            case TOP_CENTER:
                setPosition(OverlayPosition.TOP_CENTER);
                break;
            case TOP_RIGHT:
                setPosition(OverlayPosition.TOP_RIGHT);
                break;
        }

        return panelComponent.render(graphics);
    }
}
