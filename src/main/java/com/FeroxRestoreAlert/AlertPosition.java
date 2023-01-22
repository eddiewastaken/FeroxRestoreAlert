package com.FeroxRestoreAlert;

import lombok.Getter;

@Getter
public enum AlertPosition {
    TOP_LEFT("Top Left"),
    TOP_RIGHT("Top Right"),
    TOP_CENTER("Top Center"),
    BOTTOM_LEFT("Bottom Left");

    private final String friendlyName;

    AlertPosition(String name) {
        this.friendlyName = name;
    }
}
